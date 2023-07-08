#!/usr/bin/env python3

import argparse

import asana
import utils
from constants import (
    ASANA_PAT,
    ASANA_PROJECT,
    ASANA_WORKSPACE,
    PULL_REQUESTS_SECTION,
    USER_MAPPING,
)
from event import Event


events: dict[str, Event] = {}


def github_event(event: type[Event]) -> type[Event]:
    instance = event()
    events[instance.name()] = instance
    return event


@github_event
class PullRequestOpened(Event):
    def name(self):
        return "pullrequest-opened"

    def description(self) -> str:
        return "Creates a new task in the PRs section of the Asana project."

    def subparser(self, parser: argparse.ArgumentParser):
        parser.add_argument("--assignee-id", required=True)
        parser.add_argument("--pr-url", required=True, help="URL to the PR")
        parser.add_argument("--pr-title", required=True, help="Title of the PR")
        parser.add_argument("--pr-number", required=True, help="Number of the PR")

    def run(self, args: argparse.Namespace, client: asana.Client):
        print(f"Creating task for PR {args.pr_number}")
        task = client.tasks.create_in_workspace(
            ASANA_WORKSPACE,
            {
                "name": f"[#{args.pr_number}] {args.pr_title}",
                "projects": [ASANA_PROJECT],
                "notes": f"PR: {args.pr_url}",
                "assignee": USER_MAPPING.get(args.assignee_id),
            },
        )
        client.sections.add_task(PULL_REQUESTS_SECTION, {"task": task["gid"]})


@github_event
class PullRequestAssigned(Event):
    def name(self) -> str:
        return "pullrequest-assigned"

    def description(self) -> str:
        return "Assigns a task to a user."

    def subparser(self, parser: argparse.ArgumentParser):
        parser.add_argument("--assignee-id", required=True)
        parser.add_argument("--pr-number", required=True, help="Number of the PR")

    def run(self, args: argparse.Namespace, client: asana.Client):
        tasks = utils.search_incomplete_tasks_by_name(client, f"[#{args.pr_number}]")
        if not tasks:
            print(f"No tasks found for PR {args.pr_number}")
            return
        task = tasks[0]
        asana_user = USER_MAPPING.get(args.assignee_id)
        if asana_user is None:
            print(f"Removing assignee from task {task['gid']}")
        else:
            print(f"Assigning task {task['gid']} to {args.assignee_id}")

        client.tasks.update(
            task["gid"], {"assignee": USER_MAPPING.get(args.assignee_id)}
        )


def main():
    """Sets up args and processes the event."""

    # parser setup
    parser = argparse.ArgumentParser()
    subparsers = parser.add_subparsers(dest="event", required=True)
    for event in events.values():
        subparser = subparsers.add_parser(event.name(), help=event.description())
        event.subparser(subparser)

    # asana client setup
    client = asana.Client.access_token(ASANA_PAT)
    client.headers = {
        "Asana-Enable": "new_user_task_lists,new_goal_memberships"  # this shuts up warnigns
    }

    # process event
    args = parser.parse_args()
    event = events.get(args.event)
    print(f"Processing event {event.name()}")
    event.run(args, client)


if __name__ == "__main__":
    main()

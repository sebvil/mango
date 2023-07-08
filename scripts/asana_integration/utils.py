import asana
from constants import ASANA_WORKSPACE, ASANA_PROJECT, PULL_REQUESTS_SECTION


def search_incomplete_tasks_by_name(client: asana.Client, query: str) -> list[dict]:
    print(f"Searching for tasks with query: {query}")
    return list(
        client.tasks.search_tasks_for_workspace(
            ASANA_WORKSPACE,
            {
                "completed": False,
                "text": query,
                "projects_any": ASANA_PROJECT,
                "sections_any": PULL_REQUESTS_SECTION,  # this will hopefully reduce incorrect matches
            },
        )
    )

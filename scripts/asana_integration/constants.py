import pathlib
from os import environ

"""
This file contains constants used for the integration and a function to load
environment variables from a .env file.
"""


def load_env():
    """Load environment variables from .env file if it exists."""
    env_path = pathlib.Path(__file__).parent / ".env"
    if env_path.exists():
        print("Loading environment variables from .env file")
        environ.update(
            {
                line.split("=")[0]: line.split("=")[1]
                for line in env_path.read_text().split("\n")
                if line
            }
        )


load_env()

ASANA_PAT = environ["ASANA_PAT"]
ASANA_WORKSPACE = environ["ASANA_WORKSPACE"]
ASANA_PROJECT = environ["ASANA_PROJECT"]
PULL_REQUESTS_SECTION = environ["PULL_REQUESTS_SECTION"]
# github id -> asana id
USER_MAPPING = {
    "31978033": "782074107025910",  # andrew
    "8863909": "1200045256749024",  # sebastian
}

import argparse
from abc import ABC, abstractmethod

import asana

"""
This is the format for an event that represents a GitHub action.

Implementations are found in main.py
"""


class Event(ABC):
    @property
    @abstractmethod
    def name(self) -> str:
        pass

    @property
    @abstractmethod
    def description(self) -> str:
        pass

    @abstractmethod
    def subparser(self, parser: argparse.ArgumentParser):
        pass

    @abstractmethod
    def run(self, args: argparse.Namespace, client: asana.Client):
        pass

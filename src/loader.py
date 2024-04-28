import json
from abc import ABC, abstractmethod
from os import PathLike
import sqlite3


class AbstractLoader(ABC):
    """
    Abstract data loader
    """

    def __init__(self, file_path: str | PathLike):
        self._file_path = file_path

    def load(self):
        """
        Load data for given resource
        """


class JsonToSqliteLoader(AbstractLoader):
    """
    This loader creates sqlite3 database for aggregating large number of sells
    You can easily override `load` method to import data into database.
    """

    def __init__(
        self,
        file_path: str | PathLike,
        table_name: str,
        connection: sqlite3.Connection,
        datatype_mapping=None,
        mapping: dict = None,
    ):
        """

        :param file_path: Path to Json file
        :param table_name: Table to insert with
        :param connection: Sqlite3 database connection
        :param datatype_mapping: Mapping for wrapping str items into brackets
        :param mapping: object and database mapping. For ex: {"json_field": "database_field"}
        """
        super().__init__(file_path)
        self._connection = connection
        self._mapping = mapping
        self._table_name = table_name

    def load(self):
        """
        Load file to Sqlite database
        """
        cursor = self._connection.cursor()
        for obj in self.get_data():
            self.insert_row(cursor=cursor, obj=obj)

    def get_data(self) -> list[dict]:
        """
        Just open json file with built-in json module
        It can be generator if Json file is huge.
        :return: Return list of dicts due to given example
        """
        with open(self._file_path) as file:
            return json.load(file)

    def insert_row(self, cursor, obj):
        """
        Insert row to database;
        It should be overrided to work with chunks
        :return:
        """
        data = self.map_to_columns(obj)

        # We can make like this because python saves
        # in which order elements was inserted \ created
        columns = ", ".join(data.keys())
        values = ", ".join(data.values())

        cursor.execute(
            f"INSERT INTO {self._table_name} ({columns})" f"VALUES ({values});"
        )

        self._connection.commit()

    def map_to_columns(self, obj: dict):
        """
        Returns mapped fields data from obj to database
        :param obj: importing obj
        :return: dictitonary with {field_database: obj_value}
        """
        if not self._mapping:
            return self.wrap_datatypes(obj)

        mapped_obj = {
            field_database: obj.get(field_obj)
            for field_obj, field_database in self._mapping.values()
        }

        return self.wrap_datatypes(mapped_obj)

    def wrap_datatypes(self, obj: dict):
        """
        Wraps str datatypes into brackets
        Now it's workaround, wraps str datatypes into bracket
        :param obj: obj
        :return:
        """
        for key, value in obj.items():
            if isinstance(value, str):
                obj[key] = f"'{value}'"
        return obj

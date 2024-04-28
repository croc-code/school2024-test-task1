import json
import sqlite3
from datetime import datetime
import calendar


datatype_mapping = {int: "INTEGER", str: "TEXT", datetime: "TEXT", float: "NUMERIC"}

CREATE_QUERY_TEMPLATE = """
CREATE TABLE {table_name} ( {fields} );
"""


def create_database(connection: sqlite3.Connection, table_name: str, rows: dict):
    """
    Help-function for generating table in database
    :param connection: connection to sqlite3 database
    :param rows: Fields of database, mapping like {"field_name": datatype}
    :param table_name: table name for creating
    :return: None :)
    """

    fields = ", ".join(
        f"{field} {datatype_mapping.get(datatype)}" for field, datatype in rows.items()
    )

    cursor = connection.cursor()
    create_query = CREATE_QUERY_TEMPLATE.format(table_name=table_name, fields=fields)

    cursor.execute(create_query)
    connection.commit()


def format_months(months: list[str]) -> list[str]:
    """
    Format month numbers into labels
    :param months:
    :return:
    """
    formatted = []
    for month in months:
        formatted.append(
            # lower due to given example in README.MD
            # This is inefficient but, imho more clear
            calendar.month_name[int(month)].lower()
        )

    return formatted


def format_result(months: list[str]) -> str:
    """
    Format result with given example in README.MD
    :param months:
    :return: json-like string
    """
    formatted_months = format_months(months)
    result_obj = {"months": formatted_months}
    return json.dumps(result_obj)

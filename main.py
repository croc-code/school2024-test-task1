from src.loader import JsonToSqliteLoader
from src.utils import create_database, format_result
from src.strategies import SqliteFindMaxSpendingMonthStrategy
import sqlite3
from tempfile import TemporaryDirectory
from datetime import datetime


def main():
    # Sets fields as it was in example
    datatypes_mapping = {
        "user_id": str,
        "ordered_at": datetime,
        "status": str,
        "total": float,
    }
    temp_db_name = "temp.db"
    table_name = "sells"

    # Create temporary directory for only importing file
    with TemporaryDirectory(suffix="database", dir="./") as temp_dir:

        # Create database in temp dir
        connection = sqlite3.connect(f"{temp_dir}/{temp_db_name}")
        create_database(
            connection=connection, table_name=table_name, rows=datatypes_mapping
        ) 
        # Load data from json to sqlite3
        loader = JsonToSqliteLoader(file_path="format.json",
                                    table_name=table_name,
                                    connection=connection)
        loader.load()

        # Finding and formatting result
        strategy = SqliteFindMaxSpendingMonthStrategy(connection, table_name)
        result = format_result(strategy.find())
        print(result)








if __name__ == "__main__":
    main()

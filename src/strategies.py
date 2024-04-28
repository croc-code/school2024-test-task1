import sqlite3
from abc import ABC, abstractmethod


class AbstractStrategy(ABC):

    @abstractmethod
    def find(self):
        pass


class SqliteFindMaxSpendingMonthStrategy(AbstractStrategy):
    """Strategy to find max spending month with given sqlite3 database connection"""

    _find_max_total_query = """
    SELECT SUM(total) as total_sum
    FROM {table_name}
    WHERE status = 'COMPLETED'
    GROUP BY strftime('%m',date(ordered_at))
    ORDER BY -total_sum
    LIMIT -1
    """

    _find_month_query = """
    SELECT strftime('%m',date(ordered_at)) as month
    FROM {table_name}
    WHERE status = 'COMPLETED'
    GROUP BY month
    HAVING SUM(total) = {max_sum}
    ORDER BY month
    """

    def __init__(self, connection: sqlite3.Connection, table_name: str):
        self._connection = connection
        self._table_name = table_name

    def find(self) -> list[str]:
        cursor = self._connection.cursor()
        max_sum = self._get_max_sum(cursor)
        months = self._get_months(
            cursor,
            max_sum,
        )
        return [month[0] for month in months]

    def _get_max_sum(self, cursor) -> float:
        """Execute query for finding max spending summ"""
        # Just unpack fetched
        return cursor.execute(
            self._find_max_total_query.format(table_name=self._table_name)
        ).fetchone()[0]

    def _get_months(self, cursor, max_sum) -> list[tuple[str]]:
        """Execute query for finding months number by given summ"""
        return cursor.execute(
            self._find_month_query.format(table_name=self._table_name, max_sum=max_sum)
        ).fetchall()

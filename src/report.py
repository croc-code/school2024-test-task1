import json
from datetime import datetime


class Report:
    month_names = ["january", "february", "march", "april", "may", "june", "july", "august", "september",
                   "october", "november", "december"]

    def __init__(self, input_file: str):
        """
        Args:
            input_file: str - path to input file
        """
        self.input_file = input_file

    @staticmethod
    def _find_out_month(timestamp: str) -> int:
        """Find out month of timestamp

        Args:
            timestamp: str - datetime in string format

        Returns:
            month: int - month in timestamp
        """
        format_string = "%Y-%m-%dT%H:%M:%S.%f"
        date = datetime.strptime(timestamp, format_string)
        return date.month

    def __parse(self) -> dict:
        """Parse JSON file, count expenses and store it in a dictionary divided by months"""
        months = {}
        with open(self.input_file, "r") as f:
            data = json.load(f)
            for element in data:
                # count only if order status is COMPLETED
                if element['status'] == 'COMPLETED':
                    cur_month = self._find_out_month(element['ordered_at'])
                    # save user expense in dictionary by its month
                    months[cur_month] = months.get(cur_month, 0) + float(element['total'])
        return months

    def find_max_month(self) -> dict:
        """Find months with max user expenses

        Returns:
            Dictionary in format below
            {'months': ['march', 'december']}
        """
        months = self.__parse()
        # get max value of user expenses
        max_value = max(months.values())
        # find month numbers where value equals max_value
        max_months_numbers = sorted([month for month, value in months.items() if value == max_value])
        # convert numbers into month names
        max_months_str = [self.month_names[index - 1] for index in max_months_numbers]
        return {"months": max_months_str}

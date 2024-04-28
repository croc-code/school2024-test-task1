import json
from datetime import datetime


def make_report(input_file):
    with open(input_file, 'r') as file:
        data = json.load(file)

    month_earnings = {}

    for order in data:
        if order['status'] == 'COMPLETED' or order['status'] == 'DELIVERY':
            order_date = datetime.strptime(order['ordered_at'], "%Y-%m-%dT%H:%M:%S.%f")
            month_name = order_date.strftime('%B').lower()

            if month_name not in month_earnings:
                month_earnings[month_name] = 0.0

            month_earnings[month_name] += float(order['total'])

    if len(month_earnings.values()) == 0:
        max_month_names = []
    else:
        max_earning = max(month_earnings.values())
        max_month_names = [month for month, spending in month_earnings.items() if spending == max_earning]

    result = {'months': max_month_names}
    return json.dumps(result)


if __name__ == '__main__':
    input_file = 'input.json'
    report = make_report(input_file)
    print(report)

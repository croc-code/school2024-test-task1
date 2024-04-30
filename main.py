import json
from dateutil.parser import parse
import calendar


def solve():
    with open('format.json', 'r') as f:
        data = json.loads(f.read())
    total_expenses = [0] * 12
    for expense in data:
        if expense['status'] == 'COMPLETED':
            date_ = parse(expense['ordered_at'])
            total_expenses[date_.month - 1] += float(expense['total'])

    max_expense = max(total_expenses)
    report = {'months': []}
    for i in range(12):
        if total_expenses[i] == max_expense:
            report['months'].append(calendar.month_name[i + 1].lower())

    return json.dumps(report)


print(solve())

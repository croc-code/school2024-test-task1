import json
from collections import defaultdict
from datetime import datetime
def calculate(file):
    with open(file, 'r') as f:
        data = json.load(f)
        user_monthly_spending = defaultdict(lambda: defaultdict(float))
        for entry in data:
                ordered = datetime.fromisoformat(entry['ordered_at'])
                month = ordered.strftime('%B').lower()
                if entry['status'] in ['COMPLETED']:
                        user_monthly_spending[entry['user_id']][month] += float(entry['total'])
    max_spending_months = set()
    max_spending = 0
    for user, monthly_spending in user_monthly_spending.items():
        for month, spending in monthly_spending.items():
            if spending > max_spending:
                max_spending_months = {month}
                max_spending = spending
            elif spending == max_spending:
                max_spending_months.add(month)
    return {'months': list(max_spending_months)}

print(calculate('format.json'))
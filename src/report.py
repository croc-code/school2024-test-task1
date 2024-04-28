import json
from collections import defaultdict
from datetime import datetime

# Функция для формирования отчета о месяцах с наибольшими тратами
def generate_report(data):
    # Словарь для хранения общей суммы трат по месяцам
    monthly_expenses = defaultdict(float)

    # Обработка данных
    for record in data:
        if record["status"] == "COMPLETED":
            order_date = datetime.fromisoformat(record["ordered_at"])
            month = order_date.strftime("%B").lower()
            monthly_expenses[month] += float(record["total"]) 

    # Определение максимальной суммы трат
    max_expense = max(monthly_expenses.values())

    # Определение месяцев с максимальными тратами
    max_months = [month for month, expense in monthly_expenses.items() if expense == max_expense]

    return json.dumps({"months": sorted(max_months)})

def run_report():
    with open("input.json", "r") as f:
        data = json.load(f)

    report = generate_report(data)

    print("Наибольший период трат:", report)

if __name__ == "__main__":
    run_report()

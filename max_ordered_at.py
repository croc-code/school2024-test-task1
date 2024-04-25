#испорт бибилиотек для реализации
import json
from collections import defaultdict
from datetime import datetime

#созданием функции для формирования отчета по месяцам
def create_report(input_data):
    order_status = [order for order in input_data if order['status'] == 'COMPLETED']
    months = defaultdict(float)
    for order in order_status:
        date = datetime.strptime(order['ordered_at'], "%Y-%m-%dT%H:%M:%S.%f")
        month = date.strftime("%B").lower()
        months[month] += float(order['total'])
    max_expense = max(months.values())
    max_months = [month for month, expense in months.items() if expense == max_expense]
    result = {'months': max_months}
    return result


#чтение файла и преобразование его в объект Python
with open('input.json', 'r') as file:
    data = json.load(file)


#вызов функции для формирования отчета
print(create_report(data))

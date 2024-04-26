import json
from datetime import datetime


# функция для создания словаря, содержащего суммарные траты заказов со статусом COMPLETED по месяцам
def get_data(data):
    months_total = {}
    for item in data:
        if item['status'] == 'COMPLETED':
            formatted_date = datetime.strptime(item['ordered_at'], "%Y-%m-%dT%H:%M:%S.%f")
            month = formatted_date.strftime("%B").lower()
            months_total[month] = months_total.get(month, 0) + float(item['total'])
    return months_total


# функция для создания отчета
def create_report(months_total):
    max_spending = max(months_total.values())
    report = {"months": [month for month, total in months_total.items() if total == max_spending]}
    return report


if __name__ == "__main__":

    try:
        # открываем файл format.json при помощи контекстного менеджера
        with open('format.json', 'r') as f:
            data = json.load(f)
        
        # формируем словарь, содержащий суммарные траты по месяцам
        m_total = get_data(data)
        
        # генерируем на основе словаря отчет и выводим его в стандартный вывод
        print(create_report(m_total))

    except FileNotFoundError:
        print("Error: File not found")
    except json.JSONDecodeError:
        print("Error parsing JSON")

# Импортируем необходимые модули для работы скрипта
from collections import defaultdict
from datetime import datetime
import json


def generate_report(data):
    """Функция для создания отчета о расходах за месяц.

    Args:
        data (list): список словарей, содержащих записи о расходах.

    Returns:
        str: строка в формате JSON, представляющая месяц(-ы) с самыми высокими расходами.
    """

    # Создаем словарь, где ключами будут названия месяцев, а значениями - суммарные расходы за каждый месяц
    monthly_expenses = defaultdict(float)

    # Проходимся по каждой записи данных
    for record in data:
        # Проверяем, является ли заказ завершенным
        if record["status"] == "COMPLETED":
            # Получаем дату заказа из записи и преобразуем ее в объект datetime
            order_date = datetime.fromisoformat(record["ordered_at"])
            # Получаем название месяца из даты заказа и приводим его к нижнему регистру
            month = order_date.strftime("%B").lower()
            # Увеличиваем суммарные расходы за соответствующий месяц
            monthly_expenses[month] += float(record["total"])

    # Находим максимальный расход из всех суммарных расходов
    max_expense = max(monthly_expenses.values())

    # Получаем список месяцев, в которых был максимальный расход
    max_months = [
        month for month, expense in monthly_expenses.items() if expense == max_expense
    ]

    # Возвращаем JSON-строку с месяцем(-ами) с максимальным расходом, отсортированных по порядку следования в году
    return json.dumps({"months": sorted(max_months, key=lambda x: max_months.index(x))})


def perform_report():
    """Функция для выполнения процесса формирования отчета.

    Считывает данные из входного файла, формирует отчет и выводит результат.
    """

    # Открываем файл в формате JSON с данными
    with open("./src/input.json", "r") as f:
        data = json.load(f)

    # Генерируем отчет на основе данных
    report = generate_report(data)

    # Выводим результат отчета
    print(report)


if __name__ == "__main__":
    # Выполняем функцию выполнения процесса формирования отчета.
    perform_report()
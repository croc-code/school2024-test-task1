import json
from datetime import datetime


def find_max_month(month):
    month_list = []  # Иницализация списка, в котором будут хранится месяцы с максимальной суммой затрат
    max_value = 0

    month = dict(sorted(month.items(),
                        key=lambda item: item[1], reverse=True))  # Сортировка словаря по значениям в порядке увеличения

    for i in month:
        if max_value <= month[i]:  # Находим все месяцы с максимальной суммой затрат, добавляем их в список
            max_value = month[i]
            month_list.append(i)
        else:
            break

    month_list = [datetime.strptime(x, "%m").strftime("%B").lower() for x in
                  sorted(month_list)]  # Сортировка месяцев в порядке их следования, перевод месяцев в нужный формат

    return month_list


def report_generation():
    with open("format.json", "r") as json_file:
        format_dict = json.load(json_file)

    month = {}  # Инициализация словаря, данные в котором будут {месяц: сумма пользовательских затрат}

    for i in format_dict:
        if i['status'] == 'COMPLETED':  # Проверка условия завершенности заказа
            # Из длинного формата даты оставляем только месяц в формате: 01, 02. Инициализируем переменную
            ordered_at = datetime.strptime(i["ordered_at"], "%Y-%m-%dT%H:%M:%S.%f").strftime("%m")

            if ordered_at in month:
                month[ordered_at] += float(i['total'])
            else:
                month[ordered_at] = float(i['total'])

    # Создание словаря в который будет записан ответ. Значение получаем из функции, которая возварщает список месяцев
    answer = {"months": find_max_month(month)}
    json_string = json.dumps(answer)  # Преобразование ответа в json строку

    return json_string


report_generation()

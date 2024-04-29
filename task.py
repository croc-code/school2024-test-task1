import json

# Словарь <String:float> используемый для подсчёта суммарных трат за каждый месяц 
total_month = {
"january": 0.0,
"february": 0.0,
"march": 0.0,
"april": 0.0,
"may": 0.0,
"june": 0.0,
"july": 0.0,
"august": 0.0,
"september": 0.0,
"october": 0.0,
"november": 0.0,
"december": 0.0,
}

# Список месяцов в порядке следования
list_month = ["january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"]

# Создает строку в формате JSON из списка названий месяцев.
# принимает: months - Список месяцов
# Возвращает json_string - строку в формате json
def create_json_from_list(months):
    
    # Создаём словарь требуемого формата
    json_dict = {"months": months}

    # Создаём строку формата json из словаря
    json_string = json.dumps(json_dict)
    return json_string


# Находит ключи со значением, которое является максимальным в словаре.
# Принимает: data - Словарь со значениями типа float.
# Возвращает keys - список ключей с максимальным значением.
def find_max_keys(data):

  # Получаем список из всех значений словаря
  values = list(data.values())

  # Находим максимальное значение
  max_value = max(values)

  # Получаем ключи с максимальным значением
  keys = [key for key, value in data.items() if value == max_value]

  return keys


def main():
    try:
        # Открывам исходный файл и записываем его содержимое в переменную data
        with open('input.json') as f:
            data = json.load(f)
    except:
        # В случае отсутсвия входного файла сделаем данные пустыми
        data = []

    # Пробегаемся по всем объектам из массива содержащегося в файле
    for order in data:
        # Если статус заказа COMPLETED, работаем с ним
        if order['status'] == "COMPLETED":

            # Вычленяем таймстепк из объекта
            timestamp = order["ordered_at"]

            # Разделяем таймстемп по символу "T"
            parts = timestamp.split("T")

            # Получаем дату в формате "ГГГГ-ММ-ДД"
            date_part = parts[0]

            # Разделяем дату по символу "-"
            date_parts = date_part.split("-")

            # Получаем месяц
            month = date_parts[1]

            # Преобразуем месяц в число (0-11)
            month_number = int(month) - 1
            
            # Вычленяем сумму по данному заказу
            total = float(order["total"])

            # Получаем название месяца
            str_month = list_month[month_number]

            # Увеличиваем траты за этот месяц
            total_month[str_month] += total

    # Получаем список месяцов с тратами равной наибольшей
    ans = find_max_keys(total_month)

    # Получаем строку формата json требуемого формата 
    js = create_json_from_list(ans)
    
    # Вывод полученной строки
    print(js)

if __name__ == "__main__":
	main()
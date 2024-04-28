# импорт нужных библиотек
import json
from collections import defaultdict

month_ab = {
        '01':'january', '02':'february', '03':'march', '04':'april', '05':'may', '06':'june',
        '07':'july', '08':'august', '09':'september', '10':'october','11':'november','12':'december'
         }

# главная ф-ция, в которую передаются инпут
def report(input_file: json) -> json:
    # открываем файл и загружаем данные в переменную data
    with open(input_file, 'r') as file:
        data = json.load(file)
    
    # создаем словарь, где будут храниться общие затраты пользователей по месяцам
    user_expenses = defaultdict(int)
    
    # цикл по записям и суммирование трат по месяцем, при условии что status == 'COMPLETED'
    for record in data:
        if record['status'] == 'COMPLETED':
            month = month_ab.get(record['ordered_at'].split('-')[1].lower())
            user_expenses[month] += float(record['total'])
    
    # находим максимальное значение суммы трат среди всех месяцев
    max_expenses = max(user_expenses.values())
    result = {"months": [month for month, expenses in user_expenses.items() if expenses == max_expenses]}
    return json.dumps(result)

# чтение файла .json
if __name__ == '__main__':
    result = report('format.json')
    print(result)
    
# Я не совсем понял условие задания 
# "Если максимальная сумма пользовательских трат была в более, чем одном месяце, отчет должен показывать все такие месяцы."
# Написал вам письмо, а вы молчите (((
    
 
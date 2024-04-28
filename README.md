## Автор решения
Клочков Никита Сергеевич
## Описание реализации
Импортирование модулей `json` и `datetime`;

Функция `make_report` принимает json-файл в качестве переменной:
- Функция открывает `input.json`, записывает информацию из него в переменную `data`;
- Следующий цикл парсит дату заказов со статусом `COMPLETED` и `DELIVERY`, конвертирует её в объект `datetime`, извлекает имя месяца в нижнем регистре, а после записывает прибыль каждого месяца в `month_earnings`;
- Если данные по заказам имеются (`month_earnings.values()` != 0), функция находит максимальную выручку `max_earning` среди всех месяцев, записывает в `max_month_names` названия месяцев с максимальной выручкой;
- Функция формирует отчет `result` в нужном формате, конвертирует его в json;


- Если в файле `input.json` отсутствуют данные (пустой список), функция выведет пустой список (`max_month_names` = [ ]);

После вызова функции результат выводится в командную строку
## Инструкция по сборке и запуску решения
Запуск происходит следюущим образом:
- Разместить `main.py` и `input.json` в выбранной директории
- Используя командную строку, перейти в эту директорию и ввести команду `python3 main.py`
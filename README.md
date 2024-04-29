# Тестовое задание для отбора на Летнюю ИТ-школу КРОК по разработке

## Условие задания

Один развивающийся и перспективный маркетплейс активно растет в настоящее время. Текущая команда разработки вовсю занята тем, что развивает ядро системы. Помимо этого, перед CTO маркетплейса стоит задача — разработать подсистему аналитики, которая на основе накопленных данных формировала бы разнообразные отчеты и статистику.

Вы — компания подрядчик, с которой маркетплейс заключил рамочный договор на выполнение работ по разработке этой подсистемы. В рамках первого этапа вы условились провести работы по прототипированию и определению целевого технологического стека и общих подходов к разработке.

На одном из совещаний с Заказчиком вы определили задачу, на которой будете выполнять работы по прототипированию. В качестве такой задачи была выбрана разработка отчета о периодах наибольших трат со стороны пользователей.

Аналитики со стороны маркетплейса предоставили небольшой срез массива данных (файл format.json) о покупках пользователей, на примере которого вы смогли бы ознакомиться с форматом входных данных. Каждая запись данного среза содержит следующую информацию:

- Идентификатор пользователя;
- Дата и время оформления заказа;
- Статус заказа;
- Сумма заказа.

В пояснительной записке к массиву данных была уточняющая информация относительно статусов заказов:

- COMPLETED (Завершенный заказ);
- CANCELED (Отмененный заказ);
- CREATED (Созданный заказ, еще не оплаченный);
- DELIVERY (Созданный и оплаченный заказ, который доставляется).

Необходимо разработать отчет, вычисляющий по полученному массиву данных месяц, когда пользователи тратили больше всего. Если максимальная сумма пользовательских трат была в более, чем одном месяце, отчет должен показывать все такие месяцы. В отчете должны учитываться только завершенные заказы.

Требования к реализации:

1. Реализация должна содержать, как минимум, одну процедуру (функцию/метод), отвечающую за формирование отчета, и должна быть описана в readme.md в соответствии с чек-листом;
2. В качестве входных данных программа использует json-файл (input.json), соответствующий структуре, описанной в условиях задания;
3. Процедура (функция/метод) формирования отчета должна возвращать строку в формате json следующего формата:
   - {«months»: [«march»]}
   - {«months»: [«march», «december»]}
4. Найденный в соответствии с условием задачи месяц должен выводиться на английском языке в нижнем регистре. Если месяцев несколько, то на вывод они все подаются на английском языке в нижнем регистре в порядке их следования в течение года.

## Автор решения

Ерофеев Никита Алексеевич

## Описание реализации

Модули:

1. json используется для загрузки данных из файла формата JSON.

Для быстрого получения названяи месяца я создал список list_month содержащий все месяца в нужном порядке, и теперь по номеру
от 0 до 11 могу быстро получить название месяца.

Для подсчёта общих трат за каждый месяц был создан словарь total_month со структурой <String: float> - по ключу - название месяца хранится сумма трат за этот месяц.

Программа начинается с функции main
Решение пытается открытб json файл с названеим input, лежащий в одной папке с исполняемым файлом, и записать данные из него в переменую data, если же такого файла нет - data будет пустым списком.

Затем для каждого объекта из data со статусом заказа COMPLETED я увеличиваю сумму трат за месяц из заказа.

После обработки всех заказов, с помощью функции find_max_keys я нахожу все месяца, сумма трат закоторые равна максимальной.

По полученным месяцам с помощью функции create_json_from_list создаю json строку требуемого формата

## Инструкция по сборке и запуску решения

Если на компьютере установлен python и заданы переменные среды, достаточно запустить командруню строку из папки, содержащей решение и выполнить команду "python task.py"

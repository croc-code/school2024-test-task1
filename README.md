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
Каширина Мария Олеговна
## Описание реализации
1. Для реализации необходимо импортировать модуль json для работы с файлом input.json, defaultdict для создания словаря по умолчанию и datetime для преобразования строки в формат даты
2. Функция create_report принимает одну переменную с файлом json.
   - В переменную order_status записывается информация только о тех заказах, статус которых является COMPLETED
   - Переменная months создает класс defaultdict() модуля collections и возвращает новый словарь, который подобен объекту для последующего сохранения данных
   - Далее используется цикл для подсчета суммы потраченных денег за каждый месяц. Переменная date преобразует строку из списка заказов, которые были завершены, в формат даты. Затем из даты извлекается месяц в нижнем регистре. И в соответствии с месяцем в словарь months записывается сумма за каждый месяц
   - После формирования итогового словаря, необходимо найти максимальное значение суммы, с помощью встроенной функции max и извлечения значений из словаря с помощью values()
   - Теперь переменной max_expense присвоено значение максимальной суммы. С помощью тернарного оператора в переменную  max_months записываются месяц(а), в котором(ых) была достигнута максимальная сумма заказов пользователей. В переменную max_months1 записывается отсортированный список месяцов в порядке их следования в течение года
   - Переменная result содержит итог, который возращает строку в формате json
3. Чтобы прочесть файл используется оператор with, который позволяет создавать конструкцию6 котоая выполняется в любом случае. Для чтения файла используется флаг "r". В переменную data записывается файл json в объект Python для последующей работы
4. Вызов функции create_report, в аргументе которой находится переменная data, в которую ранее был записан исходный файл 

## Инструкция по сборке и запуску решения
1. При реализации используются встроенные бибилиотеки, поэтому их лишь нужно импортировать
2. Чтобы запустить проект необходимо в терминале: python3 max_ordered_at.py
3. В командной строке появится необходимый результат

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
Мельникова Марина Андреевна
## Описание реализации
Решение делится на два проекта: Prototyping и AnalyticsLargestExpenses

1. Prototyping
Данный проект служит для тестирования, в нем определены необходимые конфигурации, есть разделение окружения на разработку и продакшен.
В файле Startup идет настройка логгера, DI и определение названия файла в зависимости от типа окружения. В Program происходит запуск проекта.

3. AnalyticsLargestExpenses
Это библиотека классов. В ней реализованы три основных класса с бизнес логикой и три класса с моделями.

• JsonConverter отвечает за сериализацию и десериализацию из формата Json, есть возможность работать с данными напрямую или через файлы. Добавлена проверка на отсутствие данных в файле.

• ReportHandler обрабатывает список моделей PurchaseDto, которые соответствуют формату из примера (format.json) и возвращает модель ReportResponse, содержащую единственное свойство - список месяцев. Необходимо настроить CultureInfo для корректного преобразования поля total и перевода месяца в полную форму. Изначально происходит сортировка записей по месяцам и фильтрация по значению статуса. Далее цикл по покупкам, происходит запись в словарь, где ключом является название месяца. Если сумма совпадает с максимумом, то месяц добавляется в модель ReportResponse

• ReportGenerator объединяет взаимодействие предыдущих классов и через метод GetReportInJson() возвращет отчет в виде строки формата json.
Если при десериализации были ошибки, то будет произведена запись в логгер и вернется null

Краткое описание методов и моделей представлено в самом решении в summary. Описание классов с бизнес логикой расположено в интерфейсах, которые они реализуют

## Инструкция по сборке и запуску решения
В решении работает два окружения. Development и Production. При выборе первого данные будут считываться из файла format.json, при втором input.json
Важно, чтобы файлы находились в одной папке с решением, то есть в папке System analytics, поскольку считывание и поиск файла происходит автоматически.

При успешном выполнении программы итоговый отчет будет предоставлен в консоли и в файле months.json, который также расположен в папке решения 

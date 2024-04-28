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

***Дубровин Иван Александрович***

## Описание реализации

1. В папке models проекта `ru.croc.javaschool2024.marketplace` располагаются следующие модели данных,
   которые представляют собой основные структуры для работы с информацией в приложении:
   - MonthReport – это модель данных, используемая для сериализации информации о месяцах в формат JSON.
   - Order – это модель для представления заказов.
   - OrderStatus – это перечисление, описывающее возможные статусы заказа
2. В пакете `ru.croc.javaschool2024.marketplace.utils` содержатся утилитарные классы,
   которые обеспечивают функциональность для обработки и анализа данных
   - JsonParser - предназначен для десериализации JSON файлов в список объектов Order
     - readJsonFile: Метод, который принимает путь к файлу JSON и возвращает список объектов Order.
   - JsonReport - содержит методы для генерации отчетов в формате JSON
     - generateReport: Принимает строку JSON и возвращает ее в виде массива байтов.
   - JsonUtils - предлагает методы для анализа списка заказов и определения месяцев с максимальным объемом продаж
     - findMonthWithMaxWastes: Анализирует список заказов и возвращает JSON строку, 
       содержащую месяцы с наибольшим объемом продаж.
     - getTotalAmountByMonth: Собирает данные о сумме покупок по месяцам.
     - getMaxMonthTotal: Определяет максимальную сумму трат среди всех месяцев.
3. Application в `ru.croc.javaschool2024.marketplace` служит точкой входа в приложение.

***P.S. Для более детальной информации обращайтесь к Javadoc.***

## Инструкция по сборке и запуску решения

Запуск из корня проекта с путем к папке с JSON файлом, пример для Windows:

```bash
C:\Users\IdeaProjects\school2024-test> gradle run --args="src\main\java\ru\croc\javaschool2024\resources\format.json"                                                                                                 
```
   

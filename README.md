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
Калинич Анна Борисовна

## Описание реализации
Данная программа ищет внутри папки проекта файл "input.json" и выводит месяц(ы) с максимальной выручкой в json-формате в консоль и в "output.json".
Код состоит из 3-х классов:
- Order.cs
- OrderProcessor.cs
- Program.cs

### Order.cs 
   Класс для того, чтобы парсить данные из .json 
```C#
   public class Order
       {

           [JsonProperty("user_id")]
           public required string UserId { get; set; }

           [JsonProperty("ordered_at")]
           public DateTime OrderedAt { get; set; }

           [JsonProperty("status")]
           public required string Status { get; set; }

           [JsonProperty("total")]
           public double Total { get; set; }
       }
```
### OrderProcessor.cs 
   Класс, содержащий функции:
   - для парсинга json-строки в List<Order>
   - вытягивание названия месяца в нужном формате из поля "ordered_at"
   - поиска месяца(ев) с максимальной выручкой и выведения результата в формате json-строки
```C#
public static class OrderProcessor
    {
        /// <summary>
        /// Парсирует дату и возвращает строку с названием месяца в нижнем регистре
        /// </summary>
        /// <param name="dateTime">Дата для парсинга</param>
        /// <returns>Строка с названием месяца в нижнем регистре</returns>
        private static string ParseMonth(DateTime dateTime)
        {
            CultureInfo culture = new CultureInfo("en-US");
            return dateTime.ToString("MMMM", culture).ToLower();
        }

        /// <summary>
        /// Поиск месяцев с максимальной выручкой
        /// </summary>
        /// <param name="_orders">Список заказов</param>
        /// <returns>JSON-строка с месяцами, в которых достигнута максимальная выручка</returns>
        public static string GetMaxTotalPerMonth(List<Order> orders)
        {
            var totalPerMonth = new Dictionary<string, double>();

            foreach (var order in orders)
            {
                if (order.Status != "COMPLETED")
                {
                    continue;
                }
                var month = ParseMonth(order.OrderedAt);

                if (totalPerMonth.ContainsKey(month))
                {
                    totalPerMonth[month] += order.Total;
                }
                else
                {
                    totalPerMonth[month] = order.Total;
                }
            }

            double maximum = totalPerMonth.Values.Max();
            var maxMonths = totalPerMonth.Where(month => month.Value == maximum).Select(month => month.Key).ToList();
            var result = new { months = maxMonths };
            var json = JsonConvert.SerializeObject(result);

            return json;
        }

        /// <summary>
        /// Создает новый файл формата JSON и записывает в него переданную строку.
        /// </summary>
        /// <param name="json">Строка JSON, которую необходимо записать в файл.</param>
        /// <param name="path">Путь для создания файла.</param>
        public static void MakeJsonFile(string json, string path)
        {
            using (StreamWriter writer = new StreamWriter(Path.Combine(path, "output.json")))
            {
                writer.WriteLine(json);
            }
        }
    }
```
### Program.cs
   Испольняемый файл
``` C#
internal class Program
{
    private static void Main(string[] args)
    {
        bool makeJsonFile = true; // Флаг, указывающий, нужно ли создавать JSON файл

        string fileName = "input.json"; // Имя файла для чтения данных

        string projectPath = Directory.GetParent(Environment.CurrentDirectory).Parent.Parent.Parent.FullName;
        string path = fileName;

        // Определение пути к файлу с учётом структуры проекта
        try{
            path = Path.Combine(projectPath, fileName);// Проверяем существование пути до файла
            if(!File.Exists(path)){
                projectPath = Directory.GetParent(Environment.CurrentDirectory).Parent.Parent.FullName;
                path = Path.Combine(projectPath, fileName);
            }
        }
        catch (FileNotFoundException ex)
        { 
            Console.WriteLine(ex.Message);
            Console.Write("Введите имя json-файла: ");
            path = Path.Combine(projectPath, Console.ReadLine() + ".json");
        }
        finally{
            while (!File.Exists(path))
            {
                Console.Write("Введите путь к json-файлу: ");
                projectPath = Console.ReadLine();
                path = Path.Combine(projectPath, fileName);
                if(!File.Exists(path)){
                    Console.Write("Введите имя json-файла: ");
                    fileName = Console.ReadLine() + ".json";
                }
            }
            path = Path.Combine(projectPath, fileName);
    }

        string json = File.ReadAllText(path); // Чтение JSON из  файла

        // Десериализация JSON в список заказов
        List<Order> orders = JsonConvert.DeserializeObject<List<Order>>(json);

        // Получение максимальной суммы заказов за месяц и соответствующих месяцев
        if (orders is not null)
        {
            var maxTotal = OrderProcessor.GetMaxTotalPerMonth(orders);

            Console.WriteLine(maxTotal); // Вывод результата на консоль

            if (makeJsonFile)
            {
                OrderProcessor.MakeJsonFile(maxTotal, projectPath); // Создание JSON файла с результатом
            }
        }
        
    }
}
```
## Инструкция по сборке и запуску решения
1. Откройте терминал или командную строку.
2. Перейдите в директорию, в которой вы хотите клонировать репозиторий.
3. Введите следующую команду:
   git clone https://github.com/Altermilk/school2024-test-task1.git

Примечания для использования:
- Запускаемый файл - "Program.cs". 
- Для того, чтобы программа работала, необходимо разместить файл "input.json" в той же директории, в которой находится приложение.
- Если требуется найти другой файл, можно изменить имя файла в переменной string fileName внутри Program.cs или задать его с клавиатуры при запуске программы. 
- Если требуется вывод результата только в консоль, измените значение флага "makeJsonFile" внутри Program.cs на "false".
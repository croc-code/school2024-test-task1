using Newtonsoft.Json;
using testTask;

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
            if(!File.Exists(path))
            {
                throw new FileNotFoundException();
            }
        }
        catch (FileNotFoundException)
        { 
            while(!File.Exists(path))
            {
                Console.WriteLine($"FILE \"{path}\" NOT FOUND");
                Console.Write("> Введите полный путь к json-файлу: ");
                path = Console.ReadLine();
                projectPath = Path.GetDirectoryName(path);
            }
            Console.WriteLine();
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
                Console.WriteLine($"\n--> Результат записан в \"{Path.Combine(projectPath, "output.json")}\" ");
            }
        }
    }
}
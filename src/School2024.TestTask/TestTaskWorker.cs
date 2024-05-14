namespace School2024.TestTask;

using System.Text.Json;
using System.Text;
using System.Globalization;

using School2024.Domain;
using School2024.Application;
using School2024.ServicesForTestTask;
using School2024.ServicesForTestTask.Models;

// <summary>
// Работник тестового задания, доставляющий аналитику разными способами
// - GetAnalyticsToString возвращает аналитику в виде строки по структуре JSON
// - GetAnalyticsToJsonFile создает .json файл по переданному в метод пути и записывает в него аналитику
// </summary>

public class TestTaskWorker
{
    private readonly Dictionary<string, List<string>> _result;

    public TestTaskWorker(string inputingPath)
    {
        _result = TestTaskInitializer
                    .CreateBasicReportCreator(inputingPath)
                    .Create();
    }

    public string GetAnalyticsToString()
    {
        return JsonSerializer.Serialize <Dictionary<string, List<string>>> (_result);
    }

    public void GetAnalyticsToJsonFile(string outputingPath)
    {
        if (outputingPath == null || outputingPath.Trim().Length == 0){
            throw new ArgumentNullException("The path to the input data file is not specified");
        }

        string json = JsonSerializer.Serialize <Dictionary<string, List<string>>> (_result);

        using (FileStream fileStream = new FileStream ( outputingPath, FileMode.OpenOrCreate, FileAccess.Write))
        {
            byte[] jsonInBytes = Encoding.ASCII.GetBytes(json);

            fileStream.SetLength(0);

            fileStream.Write(jsonInBytes);
        }       
    }
}

// <summary>
// Класс предоставляющий методы инициализации сущности Создателя Отчетов, то есть предоставляющий для этой сущности сервисы 
// </summary>

public static class TestTaskInitializer 
{
	public static BasicReportCreator CreateBasicReportCreator(string inputingPath)
	{
        CultureInfo nonInvariantCulture = new CultureInfo("en-US");
        Thread.CurrentThread.CurrentCulture = nonInvariantCulture;

		InputingFileFeatures inputingFile = new InputingFileFeatures(inputingPath);

        JsonSerializerOptions jsonOptions = new JsonSerializerOptions(){
            PropertyNamingPolicy = JsonNamingPolicy.SnakeCaseLower,
            WriteIndented = true
        };

        WorkerDTOs workerDTOs = new WorkerDTOs();
        workerDTOs.AddConverter(nameof(Order), () => new DTOConverterOrder());	
        
        return new BasicReportCreator(new OrderAnalyzer(), inputingFile, jsonOptions, workerDTOs);	
	}
}

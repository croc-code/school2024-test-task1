namespace School2024.Presentation;

using Microsoft.Extensions.Logging;
using System.IO;
using System.Text.Json;
using System.Globalization;
using System.Text;

using School2024.Application;
using School2024.ServicesForTestTask;
using School2024.ServicesForTestTask.Models;
using School2024.Domain;

public class Program
{
    static void Main(string[] args)
    {
        IOrderAnalyzer analyzer = new OrderAnalyzer();
    
        InputingFileFeatures inputingFile;
    
        if (args.Length > 0){
            if (!File.Exists(args[0])){
                inputingFile = new InputingFileFeatures("input.json");
            }
            else {
                inputingFile = new InputingFileFeatures(args[0]);
            }
        }
        else {
            inputingFile = new InputingFileFeatures("input.json");
        }
        
        JsonSerializerOptions jsonOptions = new JsonSerializerOptions(){
            PropertyNamingPolicy = JsonNamingPolicy.SnakeCaseLower,
            WriteIndented = true
        };

        WorkerDTOs worker = new WorkerDTOs();
        worker.AddConverter(nameof(Order), () => new DTOConverterOrder());

        IReportCreator reportGenerator = new BasicReportCreator(analyzer, inputingFile, jsonOptions, worker);

        CultureInfo nonInvariantCulture = new CultureInfo("en-US");
        Thread.CurrentThread.CurrentCulture = nonInvariantCulture;

        Dictionary<string, List<string>> result = reportGenerator.Create();

        using (FileStream fileStream = new FileStream (
                Path.Combine(
                    Directory.GetCurrentDirectory(), "result.json"
                ),
                FileMode.OpenOrCreate,
                FileAccess.Write
                ) 
            )
        {
            string json = JsonSerializer.Serialize <Dictionary<string, List<string>>> (result);

            Console.WriteLine(json);

            byte[] jsonInBytes = Encoding.ASCII.GetBytes(json);

            fileStream.SetLength(0);

            fileStream.Write(jsonInBytes);
        }
    }
}
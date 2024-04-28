namespace School2024.Launch;

using Microsoft.Extensions.Logging;
using System.IO;
using System.Text.Json;
using System.Globalization;
using School2024.Application;
using School2024.ServicesForTestTask;
using School2024.ServicesForTestTask.Models;
using School2024.Domain;

public class Program
{
    static void Main(string[] args)
    {
        ILoggerFactory loggerFactory = LoggerFactory
                                    .Create(builder => builder.AddConsole());
    
        ILogger logger = loggerFactory.CreateLogger("School2024");
    
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

        IReportCreator reportGenerator = new ReportInJsonCreator(logger, analyzer, inputingFile, jsonOptions, worker);

        CultureInfo nonInvariantCulture = new CultureInfo("en-US");
        Thread.CurrentThread.CurrentCulture = nonInvariantCulture;

        reportGenerator.Create();
    }
}
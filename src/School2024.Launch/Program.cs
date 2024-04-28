using Microsoft.Extensions.Logging;
using School2024.Application;
using School2024.ServicesForTestTask;
using School2024.ServicesForTestTask.Models;

ILoggerFactory loggerFactory = LoggerFactory
                                .Create(builder => builder.AddConsole());

ILogger logger = loggerFactory.CreateLogger("School2024");

IOrderAnalyzer analyzer = new OrderAnalyzer();

InputingFileFeatures inputinFile;

# if DEBUG 
    inputinFile = new InputingFileFeatures("../../../input.json");
# else 
    inputinFile = new InputingFileFeatures("input.json");
# endif

System.Console.WriteLine(inputingFile.);

namespace School2024.ServicesForTestTask;

using School2024.Domain;
using School2024.Application;
using School2024.ServicesForTestTask.Models;
using Microsoft.Extensions.Logging;
using System.IO;
using System.Text.Json;
using System.Text;

public class ReportInJsonCreator : IReportCreator
{
    private readonly ILogger _logger;

    private readonly IOrderAnalyzer _analyzer;

    private List<Order> _orders;

    private readonly JsonSerializerOptions _jsonOptions;

    public List<Order> Orders {get { return _orders; } }

    private readonly InputingFileFeatures _inputingFile;

    private readonly WorkerDTOs _workerDTOs;

    public ReportInJsonCreator (
        ILogger logger,
        IOrderAnalyzer analyzer, 
        InputingFileFeatures inputingFile, 
        JsonSerializerOptions jsonOptions,
        WorkerDTOs workerDTOs
    )
    {
        _logger = logger;
        _analyzer = analyzer;
        _inputingFile = inputingFile;
        _jsonOptions = jsonOptions;
        _workerDTOs = workerDTOs;
    }

    public void SetOrders()
    {
        
    }

    public void Create()
    {
        FileInfo fileWithInputing = new FileInfo(_inputingFile.FullName);

        Console.WriteLine(fileWithInputing.FullName);

        if (!File.Exists(_inputingFile.FullName)){
            _logger.LogInformation($"{_inputingFile.FullName}\tTФайл не найден");
        }

        List<OrderDTO> dto;

        using (FileStream fileStream = new FileStream (_inputingFile.FullName, FileMode.Open, FileAccess.Read))
        {
            dto = JsonSerializer.Deserialize <List<OrderDTO>> (fileStream, _jsonOptions);

            foreach (OrderDTO order in dto)
            {
                Console.WriteLine($"{order.UserId}\t{order.OrderedAt.ToString()}\t{order.Total}\t{order.Status}");
            }
        }

        // Dictionary<string, List<string>> output = 
        //         new Directory<string, List<string>>(1)
        //                 .Add("months", new List<string>());

        // foreach (string month in _analyzer.GetMostProfitableMonths(_orders))
        // {
        //     output.Value.Add(month.ToLower());
        // }

        // using (FileStream fileStream = new FileStream (
        //         Path.Combine(
        //             Directory.GetCurrentDirectory(), "result.json"
        //         ),
        //         FileMode.OpenOrCreate,
        //         FileAccess.Write
        //     ))
        // {
        //     string json = JsonSerializer.Serialize <KeyValuePair<string, List<string>>> (output);
        //     _logger.LogInformation(json);

        //     byte[] jsonInBytes = Encoding.ASCII.GetBytes(json);

        //     fileStream.SetLength(0);

        //     fileStream.Write(jsonInBytes);
        // }
    }
}
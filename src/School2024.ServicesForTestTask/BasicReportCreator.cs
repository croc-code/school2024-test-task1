namespace School2024.ServicesForTestTask;

using School2024.Domain;
using School2024.Application;
using School2024.ServicesForTestTask.Models;
using Microsoft.Extensions.Logging;
using System.IO;
using System.Text.Json;
using System.Text;

public class BasicReportCreator : IReportCreator
{
    private readonly IOrderAnalyzer _analyzer;

    private List<Order> _orders;

    private readonly JsonSerializerOptions _jsonOptions;

    private readonly InputingFileFeatures? _inputingFile;

    private readonly WorkerDTOs _workerDTOs;

    public BasicReportCreator (
        IOrderAnalyzer analyzer, 
        InputingFileFeatures inputingFile, 
        JsonSerializerOptions jsonOptions,
        WorkerDTOs workerDTOs
    )
    {
        if (inputingFile == null || inputingFile.FullName == null || inputingFile.FullName.Trim().Length == 0){
            throw new ArgumentNullException("The path to the input data file is not specified");
        }
        if (analyzer == null){
            throw new ArgumentNullException("The Order Analizer is empty");
        }
        if (workerDTOs == null){
            throw new ArgumentNullException("The worker DTOs is empty");
        }
        _inputingFile = inputingFile;
        _analyzer = analyzer;
        _jsonOptions = jsonOptions;
        _workerDTOs = workerDTOs;
        _orders = new List<Order>();

    }

    public void SetOrders()
    {
        
    }

    public Dictionary<string, List<string>> Create()
    {
        FileInfo fileWithInputing = new FileInfo(_inputingFile.FullName);

        if (!fileWithInputing.Exists){
            throw new FileNotFoundException("The file with the input data was not found");
        }

        List<OrderDTO> dto;

        using (FileStream fileStream = new FileStream (fileWithInputing.FullName, FileMode.Open, FileAccess.Read))
        {
            try {
                dto = JsonSerializer.Deserialize <List<OrderDTO>> (fileStream, _jsonOptions);
            }
            catch {
                dto = JsonSerializer.Deserialize <List<OrderDTO>> (fileStream);
            }
        }

        foreach (OrderDTO orderDTO in dto)
        {
            _orders.Add( 
                _workerDTOs
                .GetConverter(nameof(Order))
                .Convert(orderDTO) as Order 
            );
        }

        Dictionary<string, List<string>> output = 
                new Dictionary<string, List<string>>(1);
                        
        output.Add("months", new List<string>());

        foreach (string month in _analyzer.GetMostProfitableMonths(_orders))
        {
            output["months"].Add(month.ToLower());
        }

        return output;
    }
}
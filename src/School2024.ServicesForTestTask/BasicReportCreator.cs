namespace School2024.ServicesForTestTask;

using School2024.Domain;
using School2024.Application;
using School2024.ServicesForTestTask.Models;
using Microsoft.Extensions.Logging;
using System.IO;
using System.Text.Json;
using System.Text;

// <summary>
// Сущность для создания базового отчета
// </summary>

public class BasicReportCreator : IReportCreator
{
    private readonly IOrderAnalyzer _analyzer;

    private List<Order> _orders;

    private readonly JsonSerializerOptions _jsonOptions;

    private readonly InputingFileFeatures? _inputingFile;

    private readonly WorkerDTOs _workerDTOs;

    // <summary>
    // Получение нужных для базового создателя отчетов сервисов:
    // - IOrderAnalyzer для разных реализаций аналитики
    // - InputingFileFeatures свойства файла вводных данных
    // - JsonSerializerOptions опции для сериализации из файла 
    // - WorkerDTOs для получения нужного конвертатора для DTO сущностей
    // </summary>

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

    public Dictionary<string, List<string>> Create()
    {
        // Проверка корректности переданного пути
        FileInfo fileWithInputing = new FileInfo(_inputingFile.FullName);

        if (!fileWithInputing.Exists){
            throw new FileNotFoundException("The file with the input data was not found");
        }

        List<OrderDTO> dto;

        // Десериализация из файла с данными в DTO сущность чтобы исключить ошибки  
        using (FileStream fileStream = new FileStream (fileWithInputing.FullName, FileMode.Open, FileAccess.Read))
        {
            try {
                dto = JsonSerializer.Deserialize <List<OrderDTO>> (fileStream, _jsonOptions);
            }
            catch {
                dto = JsonSerializer.Deserialize <List<OrderDTO>> (fileStream);
            }
        }

        // Конвертация DTO в базовую сущность
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

        // Проведение аналитики
        foreach (string month in _analyzer.GetMostProfitableMonths(_orders))
        {
            output["months"].Add(month.ToLower());
        }

        return output;
    }
}
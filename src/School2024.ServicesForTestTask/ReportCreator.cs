namespace School2024.ServicesForTestTask;

using School2024.Domain
using School2024.Application;
using Microsoft.Extensions.Logging;

public class ReportCreator : IReportCreator
{
    private readonly ILogger _logger;

    private readonly IOrderAnalyzer _analyzer;

    private List<Orders> _orders;

    public List<Orders> Orders {get { return _orders; } };

    private readonly string _fullNameInputingFile;

    public ReportCreator (ILogger logger, IOrderAnalyzer analyzer, string fullNameInputingFile)
    {
        _logger = logger;
        _analyzer = analyzer;
        _fullNameInputingFile = fullNameInputingFile;
    }

    public void SetOrders()
    {
        
    }

    public void Create()
    {

    }
}
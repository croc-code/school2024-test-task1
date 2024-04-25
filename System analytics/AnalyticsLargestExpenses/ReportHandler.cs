using AnalyticsLargestExpenses.Interfaces;
using AnalyticsLargestExpenses.Models;
using Serilog;

namespace AnalyticsLargestExpenses;

public class ReportHandler : IReportHandler
{
    private readonly ILogger _logger;

    public ReportHandler(ILogger logger)
    {
        _logger = logger;
    }

    public ReportResponse GetReport(List<PurchaseDto> purchases)
    {
        throw new NotImplementedException();
    }
}

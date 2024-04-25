using AnalyticsLargestExpenses.Interfaces;
using AnalyticsLargestExpenses.Models;
using Microsoft.Extensions.Logging;

namespace AnalyticsLargestExpenses;

public class ReportHandler : IReportHandler
{
    private const string CORRECT_STATUS = "COMPLETED";

    private readonly ILogger<ReportHandler> _logger;

    public ReportHandler(ILogger<ReportHandler> logger)
    {
        _logger = logger;
    }

    public ReportResponse GetReport(List<PurchaseDto> purchases)
    {
        var purchasesSorted = purchases
            .OrderBy(p => p.OrderedAt);

        Dictionary<string, int> totalByMonth = [];

        foreach (var purchase in purchasesSorted)
        {
            totalByMonth[purchase.OrderedAt.ToString("MMMM")] += purchase.Total;
        }

        var maxTotal = totalByMonth.Values.Max();

        var report = new ReportResponse()
        {
            Months = []
        };

        foreach(var month in totalByMonth.Keys)
        {
            if (totalByMonth[month] == maxTotal)
            {
                report.Months.Add(month);
            }
        }

        _logger.LogInformation($"The number of months recorded in the report = {report.Months.Count}");

        return report;
    }
}

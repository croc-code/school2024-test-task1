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
        Thread.CurrentThread.CurrentCulture = new System.Globalization.CultureInfo("en-US");

        var purchasesSorted = purchases
            .OrderBy(p => p.OrderedAt.Month);

        Dictionary<string, double> totalByMonth = [];

        foreach (var purchase in purchasesSorted)
        {
            if (purchase.Status != CORRECT_STATUS)
            {
                continue;
            }

            var month = purchase.OrderedAt
                .ToString("MMMM")
                .ToLower();

            if (!totalByMonth.ContainsKey(month))
            {
                totalByMonth[month] = 0;
            }

            totalByMonth[month] += double.Parse(purchase.Total);
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

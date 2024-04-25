using AnalyticsLargestExpenses.Interfaces;
using AnalyticsLargestExpenses.Models;
using Microsoft.Extensions.Logging;
using System.Globalization;

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
            .Where(p => p.Status  == CORRECT_STATUS)
            .OrderBy(p => p.OrderedAt.Month);

        Dictionary<string, double> totalByMonth = [];

        var culture = new CultureInfo("en-US");

        foreach (var purchase in purchasesSorted)
        {
            var month = purchase.OrderedAt
                .ToString("MMMM", culture);

            totalByMonth.TryAdd(month, 0);

            totalByMonth[month] += double.Parse(purchase.Total, culture);
        }

        var maxTotal = totalByMonth.Values.Max();

        var report = new ReportResponse()
        {
            Months = []
        };

        report.Months = totalByMonth.Keys
            .Where(k => totalByMonth[k] == maxTotal)
            .Select(k => k.ToLower())
            .ToList();

        _logger.LogInformation($"The number of months recorded in the report = {report.Months.Count}");

        return report;
    }
}
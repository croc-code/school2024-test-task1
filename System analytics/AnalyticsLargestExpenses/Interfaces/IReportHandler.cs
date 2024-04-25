using AnalyticsLargestExpenses.Models;

namespace AnalyticsLargestExpenses.Interfaces;

/// <summary>
/// Data handler for the report
/// </summary>
public interface IReportHandler
{
    /// <summary>
    /// Calculates the most profitable months
    /// </summary>
    /// <param name="purchases">List of purchases</param>
    /// <returns>Response from report</returns>
    ReportResponse GetReport(List<PurchaseDto> purchases);
}

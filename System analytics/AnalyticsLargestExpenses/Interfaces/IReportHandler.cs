using AnalyticsLargestExpenses.Models;

namespace AnalyticsLargestExpenses.Interfaces;

/// <summary>
/// Data handler for the report
/// </summary>
public interface IReportHandler
{
    /// <summary>
    ///
    /// </summary>
    /// <param name="purchases"></param>
    /// <returns></returns>
    ReportResponse GetReport(List<PurchaseDto> purchases);
}

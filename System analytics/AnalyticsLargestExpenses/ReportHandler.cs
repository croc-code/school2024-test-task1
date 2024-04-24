using AnalyticsLargestExpenses.Interfaces;

namespace AnalyticsLargestExpenses;

public class ReportHandler : IReportHandler
{
    private readonly IJsonConverter _jsonConverter;

    public ReportHandler(IJsonConverter jsonConverter)
    {
        _jsonConverter = jsonConverter;
    }

    public string GetrReport()
    {
        throw new NotImplementedException();
    }
}

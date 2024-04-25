using System.Text.Json.Serialization;

namespace AnalyticsLargestExpenses.Models;

/// <summary>
/// The class for generating a report on the most profitable months
/// </summary>
public class ReportResponse
{
    [JsonPropertyName("months")]
    public List<string>? Months { get; set; }
}

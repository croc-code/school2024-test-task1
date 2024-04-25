using System.Text.Json.Serialization;

namespace AnalyticsLargestExpenses.Models;

/// <summary>
/// The class for getting data about user purchases
/// </summary>
public class PurchaseDto
{
    [JsonPropertyName("user_id")]
    public Guid UserId { get; set; }

    [JsonPropertyName("ordered_at")]
    public DateTime OrderedAt { get; set; }

    [JsonPropertyName("status")]
    [JsonConverter(typeof(JsonStringEnumConverter))]
    public required StatusType Status { get; set; }

    [JsonPropertyName("total")]
    public required string Total { get; set; }
}

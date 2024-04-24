using System.Text.Json.Serialization;

namespace AnalyticsLargestExpenses;

public class PurchaseDto
{
    [JsonPropertyName("user_id")]
    public Guid UserId { get; set; }

    [JsonPropertyName("ordered_at")]
    public DateTime OrderedAt { get; set; }

    [JsonPropertyName("status")]
    public required string Status { get; set; }

    [JsonPropertyName("total")]
    public int Total { get; set; }
}

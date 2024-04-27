using System.Text.Json.Serialization;

namespace Croc.Infrastructure.Contracts;

public sealed class JsonOrder
{
    [JsonPropertyName("user_id")]
    public required string UserId { get; set; }
    
    [JsonPropertyName("ordered_at")]
    public DateTime OrderedAt { get; set; }
    
    [JsonPropertyName("status")]
    public Statuses Status { get; set; }
    
    [JsonPropertyName("total")]
    public required string Total { get; set; }
    
    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum Statuses
    {
        Completed,
        Canceled,
        Created,
        Delivery
    }
}
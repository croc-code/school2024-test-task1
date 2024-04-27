using System.Text.Json.Serialization;

namespace Croc.Domain;

public sealed class Order
{
    public required string UserId { get; set; }
    public DateTime OrderedAt { get; set; }
    public Status Status { get; set; }
    public decimal Total { get; set; }
}
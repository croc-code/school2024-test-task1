using System.Text.Json.Serialization;

namespace Croc.Domain;

public enum Status
{
    Completed,
    Canceled,
    Created,
    Delivery
}
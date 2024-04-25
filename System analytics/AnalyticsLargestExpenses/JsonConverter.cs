using AnalyticsLargestExpenses.Interfaces;
using System.Text.Json;

namespace AnalyticsLargestExpenses;

public class JsonConverter<T> : IJsonConverter<T>
    where T : new()
{
    public string SerializeJson(T value)
    {
        return JsonSerializer.Serialize(value);
    }

    public T DeserializeJson(string jsonString)
    {
        return JsonSerializer
                .Deserialize<T>(jsonString)
            ?? new();
    }

    public T DeserializeJsonFromFile(string path)
    {
        if (!File.Exists(""))
        {
            return new();
        }

        var jsonString = File.ReadAllText("");

        var values = JsonSerializer
            .Deserialize<T>(jsonString);

        return values ?? new();
    }

    public string SerializeJsonToFile(string path, T value)
    {
        var jsonString = JsonSerializer.Serialize(value);

        File.WriteAllText(path, jsonString);

        return jsonString;
    }
}

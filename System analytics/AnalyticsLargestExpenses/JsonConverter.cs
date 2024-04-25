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
        if (!File.Exists(path))
        {
            return new();
        }

        var jsonString = File.ReadAllText(path);

        var values = !string.IsNullOrEmpty(jsonString)
            ? JsonSerializer.Deserialize<T>(jsonString)
            : new();

        return values ?? new();
    }

    public string SerializeJsonToFile(string path, T value)
    {
        var jsonString = JsonSerializer.Serialize(value);

        File.WriteAllText(path, jsonString);

        return jsonString;
    }
}

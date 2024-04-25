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

    public async Task<T> DeserializeJsonFromFile(string path)
    {
        if (!File.Exists(path))
        {
            return new();
        }

        var jsonString = await File.ReadAllTextAsync(path);

        var values = !string.IsNullOrEmpty(jsonString)
            ? JsonSerializer.Deserialize<T>(jsonString)
            : new();

        return values ?? new();
    }

    public async Task<string> SerializeJsonToFile(string path, T value)
    {
        var jsonString = JsonSerializer.Serialize(value);

        await File.WriteAllTextAsync(path, jsonString);

        return jsonString;
    }
}

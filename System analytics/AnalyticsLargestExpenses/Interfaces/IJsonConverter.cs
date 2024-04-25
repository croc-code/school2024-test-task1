namespace AnalyticsLargestExpenses.Interfaces;

/// <summary>
/// Converter for json type
/// </summary>
public interface IJsonConverter<T>
{
    /// <summary>
    /// Serializes data in json
    /// </summary>
    /// <param name="value">Value to serialize</param>
    /// <returns>Json string</returns>
    string SerializeJson(T value);

    /// <summary>
    /// Deserializes data from a json string
    /// </summary>
    /// <param name="jsonString">String in json format</param>
    /// <returns>Deserialized value</returns>
    T DeserializeJson(string jsonString);

    /// <summary>
    /// Deserializes data from a json file
    /// </summary>
    /// <param name="path">File path</param>
    /// <returns>Deserialized value</returns>
    Task<T> DeserializeJsonFromFile(string path);

    /// <summary>
    /// Serializes data in json to file
    /// </summary>
    /// <param name="path">File path</param>
    /// <param name="value">Value to serialize</param>
    /// <returns>Json string</returns>
    Task<string> SerializeJsonToFile(string path, T value);
}

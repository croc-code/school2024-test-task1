using System;

namespace AnalyticsLargestExpenses.Interfaces;

/// <summary>
/// Converter for json type
/// </summary>
public interface IJsonConverter
{
    /// <summary>
    ///
    /// </summary>
    void ConvertToJson();

    /// <summary>
    ///
    /// </summary>
    void ConvertFromJson();
}

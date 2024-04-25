﻿namespace AnalyticsLargestExpenses.Interfaces;

/// <summary>
/// Generates a report and saves it
/// </summary>
public interface IReportGenerator
{
    /// <summary>
    /// Calls the report handler and generates it in json
    /// </summary>
    /// <returns>Report in json format or null if something was incorrect</returns>
    Task<string?> GetReportInJson();
}

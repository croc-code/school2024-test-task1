using AnalyticsLargestExpenses.Interfaces;
using AnalyticsLargestExpenses.Models;
using Microsoft.Extensions.DependencyInjection;
using Prototyping;

var builder = new ServiceCollection();

Startup.Configure(builder);

var provider = builder.BuildServiceProvider();

var reportGenerator = provider
    .GetRequiredService<IReportGenerator>();

reportGenerator.GetReportInJson();
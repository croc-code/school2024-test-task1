using AnalyticsLargestExpenses.Interfaces;
using Microsoft.Extensions.DependencyInjection;
using Prototyping;

var builder = new ServiceCollection();

Startup.Configure(builder);

var provider = builder.BuildServiceProvider();

var reportHandler = provider
    .GetRequiredService<IReportHandler>();
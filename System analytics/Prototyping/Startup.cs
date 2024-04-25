using AnalyticsLargestExpenses;
using AnalyticsLargestExpenses.Interfaces;
using AnalyticsLargestExpenses.Models;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Serilog;

namespace Prototyping;

internal class Startup
{
    public static void Configure(IServiceCollection services)
    {
        ConfigureFilePath(services);

        ConfigureLog();

        ConfigureServices(services);
    }

    private static void ConfigureServices(IServiceCollection services)
    {
        services.AddLogging(builder =>
        {
            builder.AddSerilog(Log.Logger, true);
        });

        services.AddTransient<IJsonConverter<List<PurchaseDto>>, JsonConverter<List<PurchaseDto>>>();
        services.AddTransient<IJsonConverter<ReportResponse>, JsonConverter<ReportResponse>>();

        services.AddScoped<IReportGenerator, ReportGenerator>();
        services.AddScoped<IReportHandler, ReportHandler>();
    }

    private static void ConfigureLog()
    {
        Log.Logger = new LoggerConfiguration()
          .WriteTo.Console()
          .CreateLogger();
    }

    private static void ConfigureFilePath(IServiceCollection services)
    {
        var configBuilder = new ConfigurationBuilder()
            .AddEnvironmentVariables();

        var env = Environment.GetEnvironmentVariable("ASPNETCORE_ENVIRONMENT");

        var directory = new DirectoryInfo(@"..\..\..\").FullName;
        var root = Path.Combine(directory, $"appsettings.{env}.json");

        configBuilder.AddJsonFile(root, true, true);

        IConfiguration config = configBuilder.Build();

        services.Configure<InputFileInfo>(config.GetSection("InputFileInfo"));
    }
}

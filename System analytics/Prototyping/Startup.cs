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
        ConfigureLog();

        ConfigureServices(services);

        ConfigureFilePath(services);
    }

    private static void ConfigureServices(IServiceCollection services)
    {
        services.AddLogging(builder =>
        {
            builder.AddSerilog(Log.Logger, true);
        });

        services.AddTransient<IJsonConverter<List<PurchaseDto>>, JsonConverter<List<PurchaseDto>>>();
        services.AddTransient<IJsonConverter<ReportResponse>, JsonConverter<ReportResponse>>();

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

        configBuilder.AddJsonFile($"appsettings.{env}.json", true, true);

        IConfiguration config = configBuilder.Build();

        services.Configure<FileInfo>(config.GetSection("FileInfo"));
    }
}

using Microsoft.Extensions.DependencyInjection;

IServiceCollection services = new ServiceCollection();

services.Configure<ILogger>()

ServiceProvider serviceProvider  = services.BuildServiceProvider();

ILogger logger = serviceProvider.GetService<ILogger>();
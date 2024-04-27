using System.Text;
using Croc.Domain;
using Croc.Infrastructure.Contracts;
using Croc.Infrastructure.Extensions;
using Croc.Infrastructure.Services;
using Croc.Infrastructure.Services.Interfaces;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace Croc.Infrastructure;

public class Program
{
    public static void Main()
    {
        IReportService reportService = new ReportService();
        
        string filePath = Options.GetPath();
        StringBuilder text = new StringBuilder();

        if (File.Exists(filePath))
        {
            StreamReader textFile = new StreamReader(filePath);
            
            while (textFile.ReadLine() is { } line)
            {
                text.Append(line);
            }
            
            textFile.Close();
            
            var jsonOrders = JsonSerializer.Deserialize<List<JsonOrder>>(text.ToString());

            List<Order> orders = new List<Order>();
            foreach (var order in jsonOrders)
            {
                orders.Add(order.ToDomainOrder());
            }
            reportService.AddOrders(orders.ToArray());
            var months = reportService.CreateReport();

            string jsonString = JsonSerializer.Serialize(new {months});
            Console.WriteLine(jsonString);
        }
        else
        {
            Console.WriteLine("File does not exist");
        }
    }
}
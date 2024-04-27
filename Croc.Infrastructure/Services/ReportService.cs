using System.Globalization;
using Croc.Domain;
using Croc.Infrastructure.Services.Interfaces;

namespace Croc.Infrastructure.Services;

public class ReportService : IReportService
{
    private readonly Dictionary<string, decimal> _report;

    public ReportService()
    {
        _report = new Dictionary<string, decimal>();

        for (int i = 1; i <= 12; i++)
        {
            var monthName = DateTimeFormatInfo.InvariantInfo.GetMonthName(i);
            _report.Add(monthName, 0);
        }
    }
    
    public void AddOrders(Order[] orders)
    {
        foreach (var order in orders)
        {
            var monthName = order.OrderedAt.ToString("MMMM", CultureInfo.InvariantCulture);

            if (order.Status == Status.Completed) 
                _report[monthName] += order.Total;
        }
    }

    public string[] CreateReport()
    {
        var maxTotal = GetMaxSpending();
        
        var result = new List<string>();
        foreach (var data in _report)
        {
            if (data.Value == maxTotal)
                result.Add(data.Key.ToLower());
        }
        
        return result.ToArray();
    }

    private decimal GetMaxSpending()
    {
        return _report.Values.Prepend(0).Max();
    }
}
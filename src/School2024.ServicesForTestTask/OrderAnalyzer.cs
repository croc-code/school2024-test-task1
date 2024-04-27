namespace School2024.ServicesForTestTask;

using System.Collections.Generic;
using System.Globalization;
using System.Runtime.Serialization;
using School2024.Application;
using School2024.Domain;

public class OrderAnalyzer : IOrderAnalyzer
{
    public IEnumerable<string> GetMostProfitableMonths(List<Order> orders)
    {
        Order[] temp =  orders
                            .Where(x => x.Status == OrderStatus.COMPLETED)
                            .OrderBy(x => x.OrderedAt.Month)
                            .ToArray();

        DateTimeFormatInfo dateInfo = new DateTimeFormatInfo();

        int lastMonthFromArray = 0;
        
        for (int i = 0; i < temp.Length; i++)
        {
            if (temp[i].OrderedAt.Month != lastMonthFromArray){
                lastMonthFromArray = temp[i].OrderedAt.Month;
                continue;
            }

            yield return dateInfo
                            .GetMonthName(temp[i].OrderedAt.Month);
            
            for (int j = i; j < temp.Length; j++)
            {
                if (temp[j].OrderedAt != temp[i].OrderedAt){
                    i = j;
                    break;
                }
            }
        }
    }
}
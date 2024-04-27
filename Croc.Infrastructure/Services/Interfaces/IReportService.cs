using Croc.Domain;

namespace Croc.Infrastructure.Services.Interfaces;

public interface IReportService
{
    void AddOrders(Order[] orders); 
    string[] CreateReport();
}
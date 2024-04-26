namespace School2024.Application;

using School2024.Domain;

public interface IOrderAnalyzer
{
    public IEnumerable<string> GetMostProfitableMonths(List<Order> orders);
}
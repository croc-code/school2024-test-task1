namespace School2024.Application;

using School2024.Domain;

// <summary>
// Интерфейс для разных реализаций аналитики заказов 
// </summary>

public interface IOrderAnalyzer
{
    public IEnumerable<string> GetMostProfitableMonths(List<Order> orders);
}
namespace School2024.Application;

using School2024.Domain;

public interface IReportCreator
{
    public List<Order> orders {get;} 

    public void SetOrders();

    public void Create();
}
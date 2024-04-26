namespace School2024.Application;

using School2024.Domain;

public interface IReportCreator
{
    public List<Order> Orders {get;} 

    public void SetOrders();

    public void Create();
}
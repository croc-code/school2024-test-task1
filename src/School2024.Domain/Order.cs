namespace School2024.Domain;

// <summary>
// Базовая сущность Заказа
// </summary>

public class Order
{
    public Guid UserId {get; set;}

    public DateTime OrderedAt {get; set;} 

    public OrderStatus Status {get; set;}

    public double Total {get; set;}
}
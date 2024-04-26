namespace School2024.Domain;


public class Order
{
    public Guid UserId {get; set;}

    public DateTime OrderedAt {get; set;} 

    public OrderStatus Status {get; set;}

    public decimal Total {get; set;}
}
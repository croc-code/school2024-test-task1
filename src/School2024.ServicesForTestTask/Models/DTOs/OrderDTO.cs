namespace School2024.ServicesForTestTask.Models;

using School2024.Application;

// <summary>
// DTO сущность для базовой сущность Заказа
// </summary>

public class OrderDTO : IDTO
{
	public Guid UserId {get; set;}

	public DateTime OrderedAt {get; set;}

	public string Status {get; set;}

	public string Total {get; set;}

}
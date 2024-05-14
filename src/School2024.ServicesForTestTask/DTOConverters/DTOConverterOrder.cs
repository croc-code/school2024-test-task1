namespace School2024.ServicesForTestTask;

using School2024.Application;
using School2024.Domain;
using School2024.ServicesForTestTask.Models;

// <summary>
// Реализация конвертора для Заказа
// </summary>

public class DTOConverterOrder : IDTOConverter
{
	private Dictionary<string, OrderStatus> _statuses = 
			new Dictionary<string, OrderStatus>();

	public DTOConverterOrder() 
	{ 
		_statuses.Add("COMPLETED", OrderStatus.COMPLETED);
		_statuses.Add("CANCELED", OrderStatus.CANCELED);
		_statuses.Add("CREATED", OrderStatus.CREATED);
		_statuses.Add("DELIVERY", OrderStatus.DELIVERY);
	}

	public object Convert(IDTO dto)
	{
		OrderDTO orderDTO = dto as OrderDTO;

		return new Order()
				{
					UserId = orderDTO.UserId,
					OrderedAt = orderDTO.OrderedAt,
					Status = _statuses[orderDTO.Status],
					Total = double.Parse(orderDTO.Total)
				};
	}
}
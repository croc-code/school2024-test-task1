namespace School2024.ServicesForTestTask.Models;


public class OrderDTO
{
	public Guid UserId {get; set;}

	public DateTime OrderedAt {get; set;}

	public string Status {get; set;}

	public string Total {get; set;}

}
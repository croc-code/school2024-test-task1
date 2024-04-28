namespace School2024.Application;

// <summary>
// Интерфейс для сущностей конвертирования DTO сущеостей в базовые
// </summary>

public interface IDTOConverter
{
	object Convert(IDTO dto);
}
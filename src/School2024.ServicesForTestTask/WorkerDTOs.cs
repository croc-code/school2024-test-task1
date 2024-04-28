namespace School2024.ServicesForTestTask;

using School2024.Application;

// <summary>
// Сущность для управления конвертерами
// </summary>

public class WorkerDTOs
{
	private Dictionary<string, Func<IDTOConverter>> _converters;

	public WorkerDTOs()
	{
		_converters = new Dictionary<string, Func<IDTOConverter>>();
	}

	public void AddConverter(string key, Func<IDTOConverter> converter)
	{
		_converters.Add(key, converter);
	}

	public IDTOConverter GetConverter(string key)
	{
		if (!_converters.ContainsKey(key)){
			throw new InvalidOperationException("No converter was found for such a key");
		}

		return _converters[key].Invoke();
	}
}
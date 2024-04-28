namespace School2024.ServicesForTestTask;

using School2024.Application;

public class WorkerDTOs
{
	private Dictionary<string, Func<IDTOsConverter>> _converters;

	public WorkerDTOs()
	{
		_converters = new Dictionary<string, Func<IDTOsConverter>>();
	}

	public void AddConverter(string key, Func<IDTOsConverter> converter)
	{
		_converters.Add(key, converter);
	}

	public IDTOsConverter GetConverter(string key)
	{
		if (!_converters.ContainsKey(key)){
			throw new InvalidOperationException("No converter was found for such a key");
		}

		return _converters[key].Invoke();
	}
}
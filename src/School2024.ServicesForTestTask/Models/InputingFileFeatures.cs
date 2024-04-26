namespace School2024.ServicesForTestTask.Models;


public class InputingFileFeatures
{
    private readonly string _fullName;

    public string FullName {get {return _fullName; }}

    public InputingFileFeatures(string fullName)
    {
        _fullName = fullName;
    }
}
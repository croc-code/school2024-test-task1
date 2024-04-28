namespace School2024.Application;

using School2024.Domain;

public interface IReportCreator
{
    public Dictionary<string, List<string>> Create();
}
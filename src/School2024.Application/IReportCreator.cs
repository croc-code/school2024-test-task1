namespace School2024.Application;

using School2024.Domain;

// <summary>
// Интерфейс для разных реализаций создания отчетов 
// </summary>

public interface IReportCreator
{
    public Dictionary<string, List<string>> Create();
}
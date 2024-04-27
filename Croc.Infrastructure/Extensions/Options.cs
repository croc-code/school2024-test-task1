namespace Croc.Infrastructure.Extensions;

public class Options
{
	//Укажите путь до своего json-файла
    private const string FilePath = @"Path to your file";

    public static string GetPath()
    {
        return FilePath;
    }
}
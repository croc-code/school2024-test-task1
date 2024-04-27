namespace Croc.Infrastructure.Extensions;

public class Options
{
    private const string FilePath = @"Path to your file";

    public static string GetPath()
    {
        return FilePath;
    }
}
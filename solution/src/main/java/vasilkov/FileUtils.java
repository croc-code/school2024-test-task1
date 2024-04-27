package vasilkov;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Утилитарный класс для работы с файлами.
 */
public class FileUtils {

    /**
     * Получение пути к ресурсному файлу по его имени.
     * @param resourceName Имя ресурсного файла.
     * @return Путь к ресурсному файлу.
     */
    public static String getResourceFilePath(String resourceName) {
        // Формирование пути к ресурсному файлу
        Path resourcePath = Paths.get(resourceName);
        return resourcePath.toString();
    }

    /**
     * Получение текущей рабочей директории.
     * @return Путь к текущей рабочей директории.
     */
    public static String getCurrentDirectory() {
        return System.getProperty("user.dir");
    }
}
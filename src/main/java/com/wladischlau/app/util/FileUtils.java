package com.wladischlau.app.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Утилитарный класс для работы с файлами.
 */
public abstract class FileUtils {

    /**
     * Считывает все строки из файла и возвращает их в виде списка.
     *
     * @param filePath путь к файлу.
     * @return список строк, считанных из файла.
     * @throws IOException если возникает ошибка при чтении файла.
     */
    public static List<String> readFile(String filePath) throws IOException {
        return Files.readAllLines(Path.of(filePath));
    }

    /**
     * Считывает содержимое файла в формате JSON и возвращает его в виде строки.
     *
     * @param filePath путь к файлу.
     * @return содержимое файла в виде строки.
     * @throws IOException если возникает ошибка при чтении файла.
     */
    public static String readJsonFile(String filePath) throws IOException {
        return String.join("", readFile(filePath));
    }
}

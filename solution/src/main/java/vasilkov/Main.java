package vasilkov;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.util.*;

/**
 * Главный класс приложения для обработки заказов.
 */
public class Main {
    // Задание статуса заказа по умолчанию
    private static final OrderModels.Status status = OrderModels.Status.COMPLETED;

    private static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * Точка входа в программу.
     * @param args Аргументы командной строки (имя JSON-файла).
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            logger.error("Usage: java -jar ваш_файл.jar имя_файла.json");
            return;
        }

        // Получение имени JSON-файла из аргументов командной строки
        String fileName = args[0];

        // Получение пути к файлу с данными
        String filePath = FileUtils.getResourceFilePath(fileName);

        try {

            //Конфигурирование инструмента для чтения и вывода Json
            ObjectMapperProvider objectMapperProvider = new CustomObjectMapperProvider();
            ObjectMapper mapper = objectMapperProvider.getObjectMapper();

            // Чтение данных из файла и преобразование их в массив заказов
            List<OrderModels.Order> orderArray = List.of(
                    mapper.readValue(
                            new File(filePath),
                            OrderModels.Order[].class
                    )
            );

            Solution solution = new Solution(orderArray, status);

            // Получение результата решения и его преобразование в JSON
            OrderModels.Result result = solution.solve();

            String json = mapper.writeValueAsString(result);

            System.out.print(json);

        }catch (NoSuchFileException | AccessDeniedException | InvalidPathException e) {
            logger.error("An IO error occurred while accessing the file: \n " + e.getMessage());
        }catch (InvalidFormatException e) {
            logger.error("Invalid format inside file: \n" + e.getMessage());
        }catch (JsonMappingException e) {
            logger.error("Error occurred during JSON mapping: \n" + e.getMessage());
        }catch (NullPointerException e) {
            logger.error("NullPointerException occurred: \n" + e.getMessage());
        }catch (IOException e) {
            logger.error("An IO error occurred while processing the file: \n" + e.getMessage());
        }catch (NoSuchElementException e) {
            logger.error("Error occurred because file is empty! \nor\nThere NO!!! elements with \"status\": \"COMPLETED\"!");
        }catch (Exception e) {
            logger.error("An unexpected error occurred: \n" + e.getMessage());
        }
    }

}

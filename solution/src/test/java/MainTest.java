import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vasilkov.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс для тестирования класса Main.
 */
public class MainTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    /**
     * Устанавливает перенаправление стандартного вывода перед каждым тестом.
     */
    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Восстанавливает стандартный вывод после каждого теста.
     */
    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    /**
     * Проверяем при передаче корректного файла.
     */
    @Test
    public void testMainWithValidFile() {

        String correctFilePath = "src\\test\\resources\\format.json";
        String[] args = {correctFilePath};

        Main.main(args);

        String expectedOutput = "{«months»:[«december»]}";

        assertEquals(expectedOutput, outputStream.toString());

    }

    /**
     * Проверяем при передаче файла с некорректным форматом.
     */
    @Test
    public void testMainWithInvalidFormatFile() {
        String filePath = "src\\test\\resources\\wrong_json.json";
        String[] args = {filePath};

        Main.main(args);

        String expectedOutput = "Error occurred during JSON mapping:";
        assertTrue(outputStream.toString().contains(expectedOutput));
    }

    /**
     * Проверяем при наличии более одного месяца в результате.
     */
    @Test
    public void testMainWithMoreThanOneMonthForResult() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String correctFilePath = "src\\test\\resources\\correct.json";
        String[] args = {correctFilePath};

        Main.main(args);

        String expectedOutput = "{«months»:[«february»,«march»,«may»]}";
        assertEquals(expectedOutput, outputStream.toString());
    }
}

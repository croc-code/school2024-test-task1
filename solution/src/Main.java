import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Создаю экземпляр класса DataAnalytics, чтобы получить доступ к функции getAnalytics()
        var data = new DataAnalytics();
        // Вывожу в консоль получившийся результат
        System.out.println(data.getAnalytics());

    }
}
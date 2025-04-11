import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TrafficScraper {
    public static void main(String[] args) {
        try {
            // Получаем HTML-страницу
            Document doc = Jsoup.connect("https://www.moscowmap.ru/probki.html")
                    .userAgent("Mozilla/5.0")
                    .get();

            // Находим элемент с id="id_174429663474196984561"
            Element ymapsElement = doc.select("#id_174429663474196984561").first();

            if (ymapsElement != null) {
                // Получаем текст из элемента
                String text = ymapsElement.text();

                // Извлекаем число (баллы пробок)
                String[] parts = text.split(" ");
                String trafficLevel = parts[0];

                try {
                    // Преобразуем в число
                    int result = Integer.parseInt(trafficLevel);
                    System.out.println("Уровень пробок: " + result);
                } catch (NumberFormatException e) {
                    System.out.println("Не удалось преобразовать значение в число");
                }
            } else {
                System.out.println("Элемент не найден");
            }

        } catch (IOException e) {
            System.out.println("Ошибка при загрузке страницы: " + e.getMessage());
        }
    }
}

/*
Этот код:

Загружает страницу
Находит элемент по ID
Извлекает текст
Парсит число из текста
Выводит результат
Важные замечания:

ID элемента может меняться
Структура страницы может измениться
Рекомендуется добавить дополнительную обработку ошибок
Можно добавить проверку актуальности данных
Для более надёжного решения можно добавить:

// Проверка актуальности данных
String time = parts[2];
if (isRecentData(time)) {
    // Использовать данные
}

// Пример метода проверки актуальности
private static boolean isRecentData(String time) {
    // Логика проверки времени
    return true;
}
*/

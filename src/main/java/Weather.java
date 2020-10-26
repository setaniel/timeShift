import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Weather {

    public static void drawWeather() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.gismeteo.ru/").get();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Нет интернета");
        }
        Elements spans=doc.select("span [class=value unit unit_temperature_c]");
        Element span = spans.get(0);
        System.out.println(span.text());
        Utility.getWeatherLabel().setText(span.text() + "°");
    }
}
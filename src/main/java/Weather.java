import javafx.application.Platform;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Weather {

    private static void weatherParse(){
        Document doc;
        try {
            doc = Jsoup.connect("https://www.gismeteo.ru/").get();
            Elements spans=doc.select("span [class=value unit unit_temperature_c]");
            Element span = spans.get(0);
            Utility.getWeatherLabel().setText(span.text() + "°");
        } catch (IOException e) {
            Utility.getWeatherLabel().setText("- °");
        }
    }

    public static void showWeather() {
        weatherParse();
        // Long running operation runs on different thread
        Thread thread = new Thread(() -> {
            Runnable updater = Weather::weatherParse;
            // Thread sleep every 2 seconds and start Platform.runLater(updater)
            while (true) {
                try {
                    Thread.sleep(5*60000);
                } catch (InterruptedException ex) {
                    ex.getStackTrace();
                }
                // UI update is run on the Application thread
                Platform.runLater(updater);
            }
            // New iteration
        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();
    }
}
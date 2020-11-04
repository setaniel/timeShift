import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Traffic {
    private static Label parsedTraffic = new Label();
    private static StackPane stackPane;
    private static ImageView trafficLight = new ImageView(new Image(Traffic.class.getResourceAsStream("images/ya_green.png")));

    public static void main(String[] args) {
        trafficParse();
    }

    private static void trafficParse(){
        stackPane = new StackPane();
        parsedTraffic.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD,13));
        stackPane.getChildren().addAll(trafficLight, parsedTraffic);

        Document doc;
        try {
            doc = Jsoup.connect("https://www.yandex.ru/").get();
//            Elements spans=doc.select("span [class=value unit unit_temperature_c]");
            Elements divs=doc.select("div [class=\"traffic__rate-text\"]");
//            <div class="traffic__rate-text">0</div>
            Element div = divs.get(0);
//            Utility.getWeatherLabel().setText(span.text() + "°");
            parsedTraffic.setText(div.text());
        } catch (IOException e) {
//            Utility.getWeatherLabel().setText("- °");
            System.out.println("errors everywhere");
        }
    }
    public static StackPane getTraffic(){
        trafficParse();
        return stackPane;
    }

    public static void threadTraffic() {
        trafficParse();
        // Long running operation runs on different thread
        Thread thread = new Thread(() -> {
            Runnable updater = Traffic::trafficParse;
            // Thread sleep every 2 seconds and start Platform.runLater(updater)
            while (true) {
                try {
                    Thread.sleep(600000);
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

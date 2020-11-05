import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Traffic {
    private static final StackPane parsedStackTraffic  = new StackPane();
    private static final Label trafficLabelValue = new Label();
    private static ImageView trafficColorImage;
    private static AnchorPane trafficAppPane;

    private static void trafficParse(){
        trafficLabelValue.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD,13));
        Document doc;
        try {
            doc = Jsoup.connect("https://www.yandex.ru/").get();
            Elements divs=doc.select("div [class=\"traffic__rate-text\"]");
            Element div = divs.get(0);
            trafficLabelValue.setText(div.text());
        } catch (IOException e) {
            trafficLabelValue.setText("--");
        }

        setTrafficColor();

        ImageView yaLogo = new ImageView(new Image(Utility.class.getResourceAsStream("images/ya_logo.png")));

        parsedStackTraffic.getChildren().removeAll(trafficColorImage, trafficLabelValue);
        parsedStackTraffic.getChildren().addAll(trafficColorImage, trafficLabelValue);

        trafficAppPane.getChildren().removeAll(yaLogo, parsedStackTraffic);
        trafficAppPane.getChildren().addAll(yaLogo, parsedStackTraffic);
        parsedStackTraffic.setAlignment(Pos.CENTER);

        AnchorPane.setLeftAnchor(yaLogo, 30.0);
        AnchorPane.setRightAnchor(parsedStackTraffic, 65.0);
    }
    private static void setTrafficColor(){
        int val = Integer.parseInt(trafficLabelValue.getText());

        if (val < 4) trafficColorImage = new ImageView(new Image(Traffic.class.getResourceAsStream("images/ya_green.png")));
        if (val >= 4 && val <= 6)  trafficColorImage = new ImageView(new Image(Traffic.class.getResourceAsStream("images/ya_yellow.png")));
        if (val > 6) trafficColorImage = new ImageView(new Image(Traffic.class.getResourceAsStream("images/ya_red.png")));
    }

    public static void showTraffic(AnchorPane fxTrafficPane) {
        trafficAppPane = fxTrafficPane;
        trafficParse();
        // Long running operation runs on different thread
        Thread thread = new Thread(() -> {
            Runnable updater = Traffic::trafficParse;
            // Thread sleep every 2 seconds and start Platform.runLater(updater)
            while (true) {
                try {
                    Thread.sleep(300000);
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

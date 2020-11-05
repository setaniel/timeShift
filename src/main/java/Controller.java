import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The class that controls the relationship between
 * the FXML file and Java code. Here the link fx:id
 * of links is initialized and translated into Java code.
 * */
public class Controller extends View implements Initializable{
    @FXML private AnchorPane fxTrafficPane;
    @FXML private Label weatherLabel;
    @FXML private Label netLabel;
    @FXML private ImageView minimize;
    @FXML private ScrollPane scroll;
    @FXML private ImageView info;
    @FXML private ImageView settings;
    @FXML private ImageView addButton;
    @FXML private ImageView closeButton;
    @FXML private ImageView pomodoro;
    @FXML private VBox content;

    @FXML private void onNewNoteClick(){
        addNote();
    }
    @FXML private void onPomodoroClick(){
        startPomodoro();
    }
    @FXML private void onInfoClick(){
        Info.drawInfo();
    }
    @FXML private void onSettingsClick(){
        Settings.drawSettings();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Traffic.showTraffic(fxTrafficPane);
        Utility.noteEditor.fxAddButton = addButton;
        Utility.setNetLabel(netLabel);
        Utility.setWeatherLabel(weatherLabel);
        Utility.setContent(content);
        scroll.getStylesheets().add(Controller.class.getResource("/styles.css").toExternalForm());
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setScrollVisible();
        Utility.setInterfaceStaticShadows(minimize);
        Utility.setInterfaceStaticShadows(info);
        Utility.setInterfaceStaticShadows(settings);
        Utility.setInterfaceStaticShadows(addButton);
        Utility.setInterfaceStaticShadows(closeButton);
        Utility.setInterfaceStaticShadows(pomodoro);
        Serializer.serializeNotes();
        NetChecker.ping();
        Weather.drawWeather();
    }
    private void setScrollVisible(){
        // Start scrolling
        scroll.addEventHandler(ScrollEvent.SCROLL_STARTED, event ->
                scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED));
        // Finish scrolling
        scroll.addEventHandler(ScrollEvent.SCROLL_FINISHED, event ->
                scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER));
    }
    @FXML private void minimizeApp(){
        Utility.getPrimaryStage().setIconified(true);
    }
}
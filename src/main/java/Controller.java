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
    @FXML private Label fxWeatherLabel;
    @FXML private Label fxNetLabel;
    @FXML private ImageView fxMinimize;
    @FXML private ScrollPane fxScroll;
    @FXML private ImageView fxInfo;
    @FXML private ImageView fxSettings;
    @FXML private ImageView fxAddNoteButton;
    @FXML private ImageView fxCloseButton;
    @FXML private ImageView fxPomodoro;
    @FXML private VBox fxContent;

    @FXML private void onNewNoteClick() {
        addNote();
    }
    @FXML private void onPomodoroClick() {
        startPomodoro();
    }
    @FXML private void onInfoClick() {
        if (!Info.isInfoShow) Info.showInfo(fxInfo);
        Info.isInfoShow = true;
    }
    @FXML private void onSettingsClick() {
        if (!Settings.isSettingsShow) Settings.showSettings(fxSettings);
        Settings.isSettingsShow = true;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utility.setAddNoteButton(fxAddNoteButton);
        Utility.setNetLabel(fxNetLabel);
        Utility.setWeatherLabel(fxWeatherLabel);
        Utility.setContent(fxContent);
        fxScroll.getStylesheets().add(Controller.class.getResource("/styles.css").toExternalForm());
        fxScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setScrollVisibility();
        Utility.setUIShadows(fxMinimize);
        Utility.setUIShadows(fxInfo);
        Utility.setUIShadows(fxSettings);
        Utility.setUIShadows(fxAddNoteButton);
        Utility.setUIShadows(fxCloseButton);
        Utility.setUIShadows(fxPomodoro);
        Serializer.serializeNotes();
        NetChecker.ping();
        Weather.showWeather();
        Traffic.showTraffic(fxTrafficPane);
    }
    private void setScrollVisibility() {
        // Start scrolling
        fxScroll.addEventHandler(ScrollEvent.SCROLL_STARTED, event ->
                fxScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED));
        // Finish scrolling
        fxScroll.addEventHandler(ScrollEvent.SCROLL_FINISHED, event ->
                fxScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER));
    }
    @FXML private void minimizeApp() {
        Utility.getPrimaryStage().setIconified(true);
    }
}
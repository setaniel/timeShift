import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The class that controls the relationship between
 * the FXML file and Java code. Here the link fx:id
 * of links is initialized and translated into Java code.
 * */
public class Controller implements Initializable{
    @FXML private StackPane fxRootStackPane;
    @FXML private AnchorPane fxTrafficPane;
    @FXML private ImageView fxMinimize;
    @FXML private ScrollPane fxScroll;
    @FXML private ImageView fxInfo;
    @FXML private ImageView fxSettings;
    @FXML private ImageView fxCloseButton;
    @FXML private ImageView fxPomodoro;
    @FXML AnchorPane fxRootUI;
    @FXML StackPane fxContentStack;
    @FXML Pane fxContentAnchor;
    @FXML Label fxWeatherLabel;
    @FXML Label fxNetLabel;
    @FXML ImageView fxAddNoteButton;
    @FXML VBox fxContent;

    void onNewNoteClick() {
        View.getInstance().setFxAddNoteButton(fxAddNoteButton);
        View.getInstance().addNote(new Note());
        Utility.removeButtonHandler(fxAddNoteButton);
    }

    void onNoteClick(Note note) {
        View.getInstance().setFxAddNoteButton(fxAddNoteButton);
        View.getInstance().addNote(note);
    }

    @FXML private void onPomodoroClick() {
        if (!Pomodoro.isPomodoroShow) {
            Pomodoro.stageClose();
            new Pomodoro().startPomodoro();
        }
        Pomodoro.isPomodoroShow = true;
    }

    @FXML private void onInfoClick() {
        if (!Info.getInstance().isInfoShow()) {
            Info.getInstance().closeStage();
            Info.getInstance().showInfo(fxInfo);
        }
    }

    @FXML private void onSettingsClick() {
        if (!Settings.getInstance().isSettingsShow()) {
            Settings.getInstance().closeStage();
            Settings.getInstance().showSettings(fxSettings);
        }
    }

    void fxThemeBackground(){
        fxRootStackPane.getChildren().set(0, ThemeSwitcher.getInstance().getBackgroundAppImage());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utility.setController(this);
        fxScroll.getStylesheets().add(Controller.class.getResource("scroll.css").toExternalForm());
        fxScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setScrollVisibility();
        Utility.setStaticInnerShadow(fxRootStackPane);
        Utility.setUIShadows(fxMinimize);
        Utility.setUIShadows(fxInfo);
        Utility.setUIShadows(fxSettings);
        Utility.setUIShadows(fxAddNoteButton);
        Utility.setUIShadows(fxCloseButton);
        Utility.setUIShadows(fxPomodoro);
        Serializer.getInstance().serializeNotes();
        NetChecker.getInstance().ping();
        Weather.showWeather();
        Traffic.getInstance().showTraffic(fxTrafficPane);
        SlideScene.getInstance().initScene();
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

    // Closing app, run serialization
    @FXML private void closeApp() {
        Utility.setIsAppClosing(true);
        Serializer.getInstance().serializeNotes();
    }
}
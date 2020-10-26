import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The class that controls the relationship between
 * the FXML file and Java code. Here the link fx:id
 * of links is initialized and translated into Java code.
 * */
public class Controller extends View implements Initializable{
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
        Utility.setNetLabel(netLabel);
        Utility.setContent(content);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setScrollVisible();
        setDropShadow(minimize, Color.BLACK);
        setDropShadow(info, Color.BLACK);
        setDropShadow(settings, Color.BLACK);
        setDropShadow(addButton, Color.BLACK);
        setDropShadow(closeButton, Color.BLACK);
        setDropShadow(pomodoro, Color.BLACK);
        Serializer.serializeNotes();
        NetChecker.ping();
    }
    static void setDropShadow(Node node, Color color){
        DropShadow shadow = new DropShadow();
        shadow.setColor(color);
        // Adding the shadow when the mouse cursor is on
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> node.setEffect(shadow));
        // Removing the shadow when the mouse cursor is off
        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> node.setEffect(null));

    }static void setInnerShadow(Node node, Color color){
        InnerShadow shadow = new InnerShadow();
        shadow.setColor(color);
        // Adding the shadow when the mouse cursor is on
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> node.setEffect(shadow));
        // Removing the shadow when the mouse cursor is off
        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> node.setEffect(null));
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
        minimize.setOnMouseClicked(event ->  Utility.getPrimaryStage().setIconified(true));
    }
}
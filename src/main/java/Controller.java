import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The class that controls the relationship between
 * the FXML file and Java code. Here the link fx:id
 * of links is initialized and translated into Java code.
 * */
public class Controller extends View implements Initializable{
    @FXML
    private ImageView addButton;
    @FXML
    private ImageView closeButton;
    @FXML
    private ImageView pomodoro;
    @FXML
    private VBox content;

    @FXML
    private void onNewNoteClick(){
        addNote();
    }
    @FXML
    private void onPomodoroClick(){
        startPomodoro();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDropShadow(addButton);
        setDropShadow(closeButton);
        setDropShadow(pomodoro);
        serializeNotes(content.getChildren());
    }
    static void setDropShadow(Node node){
        DropShadow shadow = new DropShadow();
        // Adding the shadow when the mouse cursor is on
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> node.setEffect(shadow));
        // Removing the shadow when the mouse cursor is off
        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> node.setEffect(null));
    }
}
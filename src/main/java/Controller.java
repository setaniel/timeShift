import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

/** The class that controls the relationship between
 * the FXML file and Java code. Here the link fx:id
 * of links is initialized and translated into Java code.*/
public class Controller extends View /*implements Initializable*/ {
    private final Model model = new Model();

    @FXML
    private void onNewNoteClick(){
        addNote(new TextArea());
    }




     /* @Override
    public void initialize(URL location, ResourceBundle resources) {
    }*/
}
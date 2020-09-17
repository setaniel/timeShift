import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class Controller extends View implements Initializable {
    private final Model model = new Model();
    static private Stage primaryStage;
    @FXML
    Button addButton;

    //--------------------------
    public static void setPrimaryStage(Stage stage){
            primaryStage = stage;
    }
    @FXML
    private void closeApp(){
        primaryStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Controller.super.addNote(primaryStage, new TextArea());
            }
        });
        SetNote.button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Controller.super.manageNote();
            }
        });
    }
}
import com.sun.istack.internal.NotNull;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.annotation.Resources;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


public class Controller extends View implements Initializable {
    private Model model = new Model();
    static private Stage primaryStage;
    @FXML
    VBox content;
    @FXML
    Button addButton;
    @FXML
    AnchorPane noteList;

    //--------------------------
    public static void setPrimaryStage(Stage stage){
            primaryStage = stage;
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
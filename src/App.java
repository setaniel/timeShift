import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App extends Application {
    public Button myButton;
    public TextField myTextField;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        root.setStyle("-fx-background-radius: 6;" +
                "-fx-background-color: rgb(45, 45, 50), rgb(60, 60, 65);" +
                "-fx-background-insets: 0, 0 1 1 0;");
        Scene scene = new Scene(root, 420, 585, Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
        // За верхнюю часть высотой 22 можно перемещать окно:
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(22);
    }
    @FXML
    void showDateTime(ActionEvent event) {
        System.out.println("Button Clicked!");
        Date now= new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        // Model Data
        String dateTimeString = df.format(now);
        // Show in VIEW
        myTextField.setText(dateTimeString);
    }

    private String dateTime(){
        return new Date().toString();
    }

}

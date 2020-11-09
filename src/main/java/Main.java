import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    /** The starting point of the program. Only run
     * the application, do not contain any logic. The
     * main window is designed here. This class loads
     * the FXML that describes the appearance of the program. */
    public static Stage primeStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primeStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene scene = new Scene(root, 400, 545, Color.TRANSPARENT); //width 420 height 565
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Undecorated window cap. Create a 22-wide top border and set drag and drop:
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(17);
        // background bottom transparent window
        root.setStyle("-fx-background-radius: 16;" +
                "-fx-background-color: rgb(66, 112, 112), rgb(66, 112, 112);"); // 1- 45,45,50    2-60,60,65
        // send stage on View, for close button use
        Utility.setPrimaryStage(primaryStage);
        Serializer.deserializeNotes();
    }
    @FXML public static void main(String[] args) {
        launch(args);
    }
}
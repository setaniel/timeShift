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

    @Override
    public void start(Stage primaryStage) throws Exception {
        // test load
/*        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("MainWindow.fxml"));*/
        //____________
        // init FXML
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        // init Window
        Scene scene = new Scene(root, 420, 565, Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Undecorated window cap. Create a 22-wide top border and set drag and drop:
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(22);
        // background bottom transparent window
        root.setStyle("-fx-background-radius: 6;" +
                "-fx-background-color: rgb(45, 45, 50), rgb(60, 60, 65);" +
                "-fx-background-insets: 0, 0 1 1 0;");
        Controller.setPrimaryStage(primaryStage);
    }
    @FXML
    public static void main(String[] args) {
        launch(args);
    }
}
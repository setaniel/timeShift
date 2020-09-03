import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    //Program start point. Only start application, don't contain any logic.

    public void start(Stage primaryStage) throws Exception {
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
    public static void main(String[] args) {
        launch(args);
    }
}
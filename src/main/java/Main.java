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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 386, 528, Color.TRANSPARENT); //width 420 height 565
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().add(NoteEditor.class.getResource("notepad.css").toExternalForm());

        // Undecorated window cap. Create a 22-wide top border and set drag and drop:
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(14);
        // Set round shadow for window
        root.setStyle("-fx-background-radius: 22;" +
                "-fx-background-insets: 10; " +
                "-fx-effect: dropShadow(three-pass-box, black, 10, 0, 0, 0);");

        Utility.setPrimaryStage(primaryStage);
        Serializer.deserializeNotes();

        Controller controller = loader.getController();
        controller.fxAddNoteButton.setOnMouseClicked(event -> controller.onNewNoteClick());
    }
    @FXML public static void main(String[] args) {
        launch(args);
    }
}
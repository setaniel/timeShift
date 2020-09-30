import com.sun.org.apache.xerces.internal.impl.dv.ValidatedInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.*;

public class NoteEditor {
    private double xOffset = 0;
    private double yOffset = 0;
    final Button doneButton = new Button("Done");

    public void noteEditWindow(Note note, Stage winMod) {
        // window building
        TextArea text = new TextArea(note.getText());
        Stage stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene(setLayout(text, stage), 300, 300, Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("notepad.css").toExternalForm());
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(winMod);
        // set position this modal on parent window
        stage.setX(winMod.getX() + 30);
        stage.setY(winMod.getY() + 60);
        stage.show();
        //-----------------------------------
        doneButton.setOnAction(event -> {
            if (text.getText().length() > 0) {
                System.out.println("inside  noteEditWindow, length == " + text.getText().length());
                note.update(text.getText());
                View.manageNotes(note);
            }
            stage.close();
        });
    }

    private VBox setLayout(TextArea editableText, Stage stage){

        VBox layout = new VBox(editableText, doneButton);
        layout.setPadding(new Insets(10));
        layout.setStyle("-fx-background-radius: 6;" +
                "-fx-background-color: rgb(45, 45, 50), rgb(60, 60, 65);" +
                "-fx-background-insets: 0, 0 1 1 0;");
        VBox.setVgrow(editableText, Priority.ALWAYS);
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(22);

        layout.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        layout.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        return layout;
    }
}

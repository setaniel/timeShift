import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.*;

public class NoteEditor {
    private static double xOffset = 0;
    private static double yOffset = 0;
    static Image image = new Image(NoteEditor.class.getResourceAsStream("images/done.png"));
    static ImageView imageView = new ImageView(image);
    private static final Button doneButton = new Button("", imageView);
    static Stage stage;

    public void noteEditWindow(Note note) {
        doneButton.setStyle("-fx-background-color : transparent;");
        Utility.setDropShadow(doneButton, Color.ALICEBLUE);
        Utility.setDropShadow(note, Color.BLACK);
        TextArea text = new TextArea(note.getText());
        text.setWrapText(true);
        stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene(setLayout(text, stage), 300, 300, Color.TRANSPARENT);

        scene.getStylesheets().add(NoteEditor.class.getResource("notepad.css").toExternalForm());
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(Utility.getPrimaryStage());
        // set position this modal on parent frame
        stage.setX(Utility.getPrimaryStage().getX() + 30);
        stage.setY(Utility.getPrimaryStage().getY() + 60);
        stage.show();

        //-----------------------------------
        doneButton.setOnAction(event -> {
            if (text.getText() != null && text.getText().length() > 0) {
                note.update(text.getText());
                View.manageNotes(note);
            }
            stage.close();
        });
        EventHandler<MouseEvent> primeStageClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // on click actions here
                if (text.getText() != null && text.getText().length() > 0) {
                    note.update(text.getText());
                    View.manageNotes(note);
                }
                stage.close();
                Utility.root.removeEventFilter(MouseEvent.MOUSE_CLICKED, this); // at the bottom
            }
        };
        Utility.root.addEventFilter(MouseEvent.MOUSE_CLICKED, primeStageClicked); // add the eventhandler to the node
    }

    private static VBox setLayout(TextArea editableText, Stage stage){
        VBox layout = new VBox(editableText, doneButton);
        layout.setPadding(new Insets(10, 10, 0, 10));
        layout.setStyle("-fx-background-radius: 16;" +
                "-fx-background-color: rgb(45, 45, 50), rgb(60, 60, 65);" +
                "-fx-background-insets: 0, 0 1.ser 1.ser 0;");
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

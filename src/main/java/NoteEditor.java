import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class NoteEditor {
    private static double xOffset = 0;
    private static double yOffset = 0;
    private static final ImageView okButton = new ImageView(
            new Image(NoteEditor.class.getResourceAsStream("images/done.png")));

    private static Stage stage;
    private static String noteObjectText;
    private static TextArea text;
    private static Note editableNote;
    private static NoteEditor instance;
    private static ImageView fxAddButton;

    private static StackPane parentContainer = Utility.contentStack;

    private NoteEditor(){
        noteEditWindow();
    }
    public static NoteEditor getInstance(){
        if (instance == null) instance = new NoteEditor();
        return instance;
    }

    private static void noteEditWindow() {
        Utility.setSwitchShadows(okButton, Color.WHITE, Color.BLUE);
        text = new TextArea();
        text.setWrapText(true);
        text.getStylesheets().add(NoteEditor.class.getResource("notepad.css").toExternalForm());

        StackPane root = new StackPane();
        root.setPrefSize(270, 450);
        Utility.contentAnchor.getChildren().add(root);
        AnchorPane note = new AnchorPane(text);
        note.setPrefSize(270, 450);

        note.translateXProperty().set(Utility.contentAnchor.getWidth());
        note.getChildren().add(text);
        if (Utility.contentAnchor.getChildren().size() == 3) Utility.contentAnchor.getChildren().remove(1);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.6), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();



        /*Scene scene = new Scene(text, 270, 450, Color.TRANSPARENT);
        stage = new Stage(StageStyle.UNDECORATED);
        scene.getStylesheets().add(NoteEditor.class.getResource("notepad.css").toExternalForm());
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(Utility.getPrimaryStage());*/
    }

    public void editNote(Note note){
        editableNote = note;
        Utility.setDropShadow(editableNote, Color.valueOf("#98ecf2"));
        if (editableNote.getText() == null){
            noteObjectText = null;
            text.setText("");
        }else {
            noteObjectText = editableNote.getText();
            text.setText(noteObjectText);
        }
        stage.show();
        View.isNoteEditorShow = true;
        stage.requestFocus();

        // set position this modal on parent frame
        stage.setX(Utility.getPrimaryStage().getX() + 30);
        stage.setY(Utility.getPrimaryStage().getY() + 60);
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(22);
        setEventHandlers();
    }

    private static void textChecks(){
        if (noteObjectText == null){ // new note object, if symbols >= 1 - create note
            if (text.getText().length() > 0){
                editableNote.update(text.getText());
                editableNote.setNoteDate();
                View.manageNotes(editableNote);
            }
        } else if (!noteObjectText.equals(text.getText())){ // existing note, but has changes
            editableNote.update(text.getText());
            View.manageNotes(editableNote);
            editableNote.setNoteDate();
        }
        if (noteObjectText != null && text.getText().length() == 0){ // Deleted all content, deleted note
            Utility.getContent().getChildren().remove(editableNote);
        }
        removeEventHandlers();
        stage.hide();
        View.isNoteEditorShow = false;
    }

    private static void removeEventHandlers(){

        okButton.setOnMouseClicked(null);
        Main.primeStage.getScene().setOnMouseClicked(null);
        stage.getScene().setOnKeyPressed(null);
        stage.getScene().setOnMouseExited(null);
        fxAddButton.setOnMouseExited(null);
    }

    private void setEventPrimeClick(){
        // On out of node click event
        Utility.getPrimaryStage().getScene().setOnMouseClicked(event -> {
            event.consume();
            textChecks();
        });
    }

    private void setEventHandlers(){
        // okButton press event
        okButton.setOnMouseClicked(event -> {
            event.consume();
            textChecks();
        });
        // part of event
        stage.getScene().setOnMouseExited(event -> setEventPrimeClick());
        fxAddButton = Utility.getAddNoteButton();
        fxAddButton.setOnMouseExited(event -> setEventPrimeClick());

        // Close on Esc pressed event
        stage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                // ----
                event.consume();
                textChecks();
                // ----
            }
        });
    }

}

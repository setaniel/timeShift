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

    private static final ImageView okButton = new ImageView(
            new Image(NoteEditor.class.getResourceAsStream("images/done.png")));
    private static Stage stage;
    private static String noteObjectText;
    public static TextArea text;
    private static Note editableNote;
    private static NoteEditor instance;
    private static ImageView fxAddButton;

    private NoteEditor(){
        noteEditWindow();
    }
    public static NoteEditor getInstance(){
        if (instance == null) instance = new NoteEditor();
        return instance;
    }

    private static void noteEditWindow() {
        Utility.setUIShadows(okButton);
        text = new TextArea();
        text.setWrapText(true);
        text.setPrefSize(280, 395);
    }

    public void showEditor(Note note){
        Utility.rootUI.getChildren().add(okButton);
        AnchorPane.setRightAnchor(okButton, 5.0);
        AnchorPane.setTopAnchor(okButton, 140.0);

        editableNote = note;
        Utility.setDropShadow(editableNote, Color.DARKGREY);
        if (editableNote.getText() == null){
            noteObjectText = null;
            text.setText("");
        }else {
            noteObjectText = editableNote.getText();
            text.setText(noteObjectText);
        }

        View.isNoteEditorShow = true;
        stage.requestFocus();

        // set position this modal on parent frame
        stage.setX(Utility.getPrimaryStage().getX() + 30);
        stage.setY(Utility.getPrimaryStage().getY() + 50);
//        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(22);
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
//        stage.hide();
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
                event.consume();
                textChecks();
            }
        });
    }
}

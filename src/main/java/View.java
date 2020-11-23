import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Create a view frame.
 * Managing notes
 * */
class View {
    static boolean isNoteEditorShow = false;
    static ImageView fxAddNoteButton;

    public static void setFxAddNoteButton(ImageView fxAddNoteButton) {
        View.fxAddNoteButton = fxAddNoteButton;
    }




    static void addNote(Note note) {
        if (!isNoteEditorShow) {
            NoteEditor.removeOkButton(fxAddNoteButton);
            Utility.getNoteEditor().initEditor(note);
            SlideScene.showEditor();
        }
    }

    static void manageNotes(Note note) {
        if (note.getText() != null) {
            Utility.getContent().getChildren().remove(note);
            Utility.getContent().getChildren().add(0, note);
            Serializer.deleteSerializeFiles();
            Serializer.serializeNotes();
        }
    }

    // Closing app, run serialization
    @FXML private void closeApp() {
        Utility.setIsAppClosing(true);
        Serializer.serializeNotes();
    }
}

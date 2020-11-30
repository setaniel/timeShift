import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


/** Managing notes **/
class View {
    static boolean isNoteEditorShow = false;
    static ImageView fxAddNoteButton;

     static void setFxAddNoteButton(ImageView fxAddNoteButton) {
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
        Utility.getContent().getChildren().remove(note);
        Utility.getContent().getChildren().add(0, note);
        Serializer.deleteSerializeFiles();
        Serializer.serializeNotes();
    }

    // Closing app, run serialization
    @FXML private void closeApp() {
        Utility.setIsAppClosing(true);
        Serializer.serializeNotes();
    }
}

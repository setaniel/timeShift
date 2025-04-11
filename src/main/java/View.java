import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


/**
 * Managing notes
 * @author Setaniel
 * @version 1.0
 * **/
class View {
    private boolean isNoteEditorShow = false;
    private ImageView AddNoteButton;
    private static View instance;

    static View getInstance(){
        if (instance == null) instance = new View();
        return instance;
    }

    private View(){
    }

    public boolean isNoteEditorShow() {
        return isNoteEditorShow;
    }

    public void setNoteEditorShow(boolean noteEditorShow) {
        isNoteEditorShow = noteEditorShow;
    }

    void setFxAddNoteButton(ImageView fxAddNoteButton) {
        AddNoteButton = fxAddNoteButton;
    }

    void addNote(Note note) {
        if (!isNoteEditorShow) {
            NoteEditor.getInstance().removeOkButton(AddNoteButton);
            NoteEditor.getInstance().initEditor(note);
            SlideScene.getInstance().showEditor();
        }
    }

    void manageNotes(Note note) {
        Utility.getInstance().getContent().getChildren().remove(note);
        Utility.getInstance().getContent().getChildren().add(0, note);
        Serializer.getInstance().deleteSerializeFiles();
        Serializer.getInstance().serializeNotes();
    }
}

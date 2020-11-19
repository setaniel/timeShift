import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * Create a view frame.
 * Managing notes
 * */
class View {

    static boolean isPomodoroStarted = false;
    static boolean isNoteEditorShow = false;
    static SlideScene slideScene = new SlideScene();



    void addNote() {
//        if (!isNoteEditorShow) Utility.noteEditor.showEditor(new Note());
        slideScene.showEditor();
    }

    static void startPomodoro() {
        if (isPomodoroStarted) return;
        new Pomodoro().startPomodoro();
        isPomodoroStarted = true;
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

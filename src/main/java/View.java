import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

/**
 * Create a view frame.
 * Managing notes
 * */
class View {
    private  final NoteEditor noteEditor = new NoteEditor();
    static boolean isPomodoroStarted = false;

    protected void addNote(){
        noteEditor.noteEditWindow(new Note());
    }
    protected void startPomodoro(){
        if (isPomodoroStarted) return;
        new Pomodoro().startPomodoro();
        isPomodoroStarted = true;
    }

    protected static void manageNotes(Note note){
        if (note.getText() != null) {
            Utility.getContent().getChildren().remove(note);
            Utility.getContent().getChildren().add(0, note);
            Serializer.deleteSerializeFiles();
            Serializer.serializeNotes();
        }
    }


    // Closing app, run serialization
    @FXML private void closeApp(){
        NetChecker.stopPing();
        Utility.setIsAppClosing(true);
        Serializer.serializeNotes();
    }
}

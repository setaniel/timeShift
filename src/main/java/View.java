import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.*;

/**
 * Create a view frame.
 * Managing notes
 * */
class View {
    private  final NoteEditor noteEditor = new NoteEditor();
    static boolean isPomodoroStarted = false;

    protected void addNote(){
        noteEditor.noteEditWindow(new Note(Instances.getContent()));
    }
    protected void startPomodoro(){
        if (isPomodoroStarted) return;
        new Pomodoro().startPomodoro();
        isPomodoroStarted = true;
    }

    protected static void manageNotes(Note note){
        if (note.getText() != null) {
            Instances.getContent().getChildren().remove(note);
            Instances.getContent().getChildren().add(0, note);
            Serializer.deleteSerializeFiles();
            Serializer.serializeNotes();
        }
    }

    @FXML
    // Closing app, run serialization
    private void closeApp(){
        Instances.setIsAppClosing(true);
        Serializer.serializeNotes();
    }
}

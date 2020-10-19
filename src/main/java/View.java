import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.plaf.nimbus.AbstractRegionPainter;
import java.io.*;

/**
 * Create a view frame.
 * Managing notes
 * */
class View {
    private static Stage mainStage;
    private  final NoteEditor noteEditor = new NoteEditor();
    private static View instance;
    boolean isAppClosing = false;
    static boolean isPomodoroStarted = false;
    @FXML
    private VBox content;

    public View(){
        instance = this;
    }
    public static View getViewInstance(){
        return instance;
    }

    protected void addNote(){
        noteEditor.noteEditWindow(new Note(content), mainStage);
    }
    protected void startPomodoro(){
        if (isPomodoroStarted) return;
        new Pomodoro().startPomodoro(mainStage);
        isPomodoroStarted = true;
    }

    protected void manageNotes(Note note){
        if (note.getText() != null) {
            content.getChildren().remove(note);
            content.getChildren().add(0, note);
            deleteSerializeFiles();
            serializeNotes(instance.content.getChildren());
        }
    }
    public static void setMainStage(Stage stage){
        View.mainStage = stage;
    }

    /**
     * Serializing notes in user home directory
     * */
    public static void serializeNotes(ObservableList<Node> list){
        for (Node node : list) {
            try {
                Note serialNote = (Note)node;
                serialNote.setIndex(instance.content.getChildren().indexOf(node));
                new File(System.getProperty("user.home") + "/documents/timeShift/serialize").mkdirs();
                String path = String.format(System.getProperty("user.home") + "/documents/timeShift/serialize/%d.ser", serialNote.getIndex());
                FileOutputStream fileOut = new FileOutputStream(path);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(serialNote.getNoteModel());
                out.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (instance.isAppClosing){
            mainStage.close();
            System.exit(0);
        }
    }

    /**
     * Deserializing notes, from files in user home directory
     * */
    public static void deserializeNotes(){
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String path = String.format(System.getProperty("user.home") + "/documents/timeShift/serialize/%d.ser", i);
            File file = new File(path);
            if (file.exists()) {
                try {
                    FileInputStream fileIn = new FileInputStream(file);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    Note note = new Note(instance.content, (Model) in.readObject());
                    note.setMainStage(mainStage);
                    instance.content.getChildren().add(note.getIndex(), note);
                    in.close();
                    fileIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException c){
                    break;
                }
            }
            else break;
        }
        deleteSerializeFiles();
    }
    /**
     * After deserialize, delete all .ser files
     * */
    public static void deleteSerializeFiles(){
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String path = String.format(System.getProperty("user.home") + "/documents/timeShift/serialize/%d.ser", i);
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }else break;
        }
    }
    @FXML
    // Closing app, run serialization
    private void closeApp(){
        isAppClosing = true;
        serializeNotes(content.getChildren());
    }
}

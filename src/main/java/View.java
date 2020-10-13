import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Observable;
// function to get and output the result

class View {
    private static Stage mainStage;     // stage of modal window (set note text)
    private  final NoteEditor noteEditor = new NoteEditor();
    private static View instance;
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

    protected void manageNotes(Note note){
        if (note.getText() != null) {
            content.getChildren().remove(note);
            content.getChildren().add(0, note);
        }
    }

    public static void serializeNotes(ObservableList<Node> list){
        for (Node node : list) {
            try {
                Note serialNote = (Note)node;
                serialNote.setIndex(getViewInstance().content.getChildren().indexOf(node));
                String path = String.format("dataSerialize/%d.ser", serialNote.getIndex());
                FileOutputStream fileOut = new FileOutputStream(new File(path));
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(serialNote.getNoteModel());
                out.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mainStage.close();
    }
    public static void deserializeNotes(){

    }
    public static void setMainStage(Stage stage){
        View.mainStage = stage;
    }
    @FXML
    private void closeApp(){
        serializeNotes(content.getChildren());
    }
}

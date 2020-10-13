import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

class View {
    private static Stage mainStage;
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
                String path = String.format("src/main/java/dataSerialize/%d.ser", serialNote.getIndex());
                FileOutputStream fileOut = new FileOutputStream(path);
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
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            File file = new File(String.format("src/main/java/dataSerialize/%d.ser", i));
            if (file.exists()) {
                try {
                    FileInputStream fileIn = new FileInputStream(file);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    Note note = new Note(View.instance.content, (Model) in.readObject());
                    note.setMainStage(mainStage);
                    View.instance.content.getChildren().add(note.getIndex(), note);
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
    public static void setMainStage(Stage stage){
        View.mainStage = stage;
    }
    private static void deleteSerializeFiles(){
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            File file = new File(String.format("src/main/java/dataSerialize/%d.ser", i));
            if (file.exists()) {
                file.delete();
            }else break;
        }
    }
    @FXML
    private void closeApp(){
        serializeNotes(content.getChildren());
    }
}

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
// function to get and output the result

class View {
    private static Stage mainStage;     // stage of modal window (set note text)
    private  final NoteEditor noteEditor = new NoteEditor();
    private static View instance;
    // FXML linked nodes
    @FXML
    private VBox content;

    public View(){
        instance = this;
    }
    public static View getInstance(){
        return instance;
    }

    protected void addNote(){
        noteEditor.noteEditWindow(new Note(content), mainStage);
        System.out.println("inside addNote");
    }

    protected void manageNotes(Note note){
        System.out.println("inside manageNotes");
        if (note.getText() != null) {
            System.out.println(content);
            System.out.println(note.getText());
            content.getChildren().remove(note.getNote());
            content.getChildren().add(0, note.getNote());
        }
    }

    public static void setMainStage(Stage stage){
        View.mainStage = stage;
    }
    @FXML
    private void closeApp(){
        mainStage.close();
    }
}

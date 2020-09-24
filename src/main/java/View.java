import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
// function to get and output the result

class View {
    private static Stage mainStage;
    // stage of modal window (set note text)
    private  final EditNote editNote = new EditNote();
//    private Stage mainStage;
    private TextArea note;
    private Model model;
    // FXML linked nodes
    @FXML
    private VBox content;
    //--------------------------
    {
        EditNote.doneButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> manageNote());
    }

    protected void addNote(TextArea newText){
        note = newText;
        note = editNote.showWindow(note, mainStage);
    }
    // Add note on main window(note list)
    protected void manageNote(){
            if (note.getText().length() > 0){

//                content.getChildren().indexOf(anchorPane);
//                getting index of this pane

                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setStyle("-fx-background-color: transparent; -fx-border-color: brown;");
                //trashButton note Button
                Button trashButton = createTrashButton();
                trashButton.setOnAction(evt -> content.getChildren().remove(anchorPane));
                // get note text and null local note variable
                TextArea text = new TextArea(note.getText());
                note.setText("");
                // set note title and text, getting out textarea
                Label titleLabel = new Label();
                Label previewLabel = new Label();
                setNotePreview(titleLabel, previewLabel, text.getText());
                // set positions of text and buttons in note
                AnchorPane.setBottomAnchor(previewLabel, 0.0);
                AnchorPane.setLeftAnchor(previewLabel, 5.0);
                AnchorPane.setLeftAnchor(titleLabel, 5.0);
                AnchorPane.setTopAnchor(titleLabel, 0.0);
                AnchorPane.setRightAnchor(trashButton, 5.0);
                AnchorPane.setTopAnchor(trashButton, 5.0);
                AnchorPane.setBottomAnchor(trashButton, 5.0);
                anchorPane.getChildren().addAll(titleLabel, trashButton, previewLabel);
                // add a note in notes list in 0 index
                content.getChildren().add(0, anchorPane);
                //onClick on note for edit
                anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    addNote(text);
                    content.getChildren().remove(anchorPane);
                });
            }
    }
    private void setNotePreview(Label titleLabel, Label previewLabel, String noteContent){
        Model model = new Model();
        model.setTitleLabel(titleLabel);
        model.setPreviewLabel(previewLabel);
        List<String> list = Arrays.asList(noteContent.split("\n"));
        model.getTitleLabel().setText(list.get(0));
        if(list.size()>1)model.getPreviewLabel().setText(list.get(1));
    }
    private Button createTrashButton(){
        Image image = new Image(View.class.getResourceAsStream("images/trash.png"), 30, 30, true, false);
        ImageView imageView = new ImageView(image);
        Button trashButton = new Button("", imageView);
        trashButton.setMaxSize(30, 30);
        trashButton.setStyle("-fx-background-color : transparent;");
        return trashButton;
    }
    public static void setMainStage(Stage stage){
        View.mainStage = stage;
    }
    @FXML
    private void closeApp(){
        mainStage.close();
    }
}

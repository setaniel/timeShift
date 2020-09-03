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
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
// function to get and output the result

public class View {

    // stage of modal window (set note text)
    private SetNote setNote = new SetNote();
    private Stage primaryStage;
    // FXML linked nodes
    @FXML
    private VBox content;
    TextArea note;
    //--------------------------
    //  logic for integrate
    protected void addNote(Stage primaryStage, TextArea newText){
        this.note = newText;
        note = setNote.showWindow(note, primaryStage);
        this.primaryStage = primaryStage;
    }
    // Add note on main window(note list)
    protected void manageNote(){
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setStyle("-fx-background-color: transparent");
            // set delete note button
            Image image = new Image(getClass().getResourceAsStream("resources/trash.png"), 30, 30, true, false);
            ImageView imageView = new ImageView(image);
            Button button = new Button("", imageView);
            button.setMaxSize(30, 30);
            button.setStyle("-fx-background-color : transparent;");
            button.setOnAction(evt -> content.getChildren().remove(anchorPane));
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
            AnchorPane.setRightAnchor(button, 5.0);
            AnchorPane.setTopAnchor(button, 5.0);
            AnchorPane.setBottomAnchor(button, 5.0);
            anchorPane.getChildren().addAll(titleLabel, button, previewLabel);
            // add a note in notes list in 0 index
            content.getChildren().add(0, anchorPane); //onClick on note for edit
            anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
            public void handle(MouseEvent mouseEvent) {
                addNote(primaryStage, text);
                content.getChildren().remove(anchorPane);
            }
        });
        text.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addNote(primaryStage, text);
                content.getChildren().remove(anchorPane);
            }
        });
    }
    private void setNotePreview(Label titleLabel, Label previewLabel, String noteContent){
        Model model = new Model();
        model.setTitleLabel(titleLabel);
        model.setPreviewLabel(previewLabel);
        List<String> list = Arrays.asList(noteContent.split("\n"));
        model.getTitleLabel().setText(list.get(0));
        model.getPreviewLabel().setText(list.get(1));
    }
}

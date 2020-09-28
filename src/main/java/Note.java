import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

public class Note {
    private final VBox noteList;
    private final Model noteModel;
    private AnchorPane anchorPaneNote;
    private Label titleLabel;
    private Label previewLabel;

    public Note(VBox content, String text){
        this.noteList = content;
        noteModel = new Model();
        anchorPaneNote = createAnchorPane();
        update(text);
    }

    public void setText(String text){
        noteModel.setNoteText(text);
    }
    public void setTitle(String title){
        noteModel.setTitleLabelText(title);
        titleLabel.setText(title);
    }
    public void setPreview(String preview){
        noteModel.setPreviewLabelText(preview);
        previewLabel.setText(preview);
    }
    public void update(String text){
        List<String> list = Arrays.asList(text.split("\n"));
        setTitle(list.get(0));
        if (list.size()>1) setPreview(list.get(1));
        setText(text);
    }
    public String getText(){
        return noteModel.getNoteText();
    }
    public String getTitle(){
        return noteModel.getTitleLabelText();
    }
    public String getPreview(){
        return noteModel.getPreviewLabelText();
    }

    private AnchorPane createAnchorPane(){
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: transparent; -fx-border-color: brown;");
        Button trashButton = createTrashButton();
        trashButton.setOnAction(evt -> noteList.getChildren().remove(anchorPane));
        // set note title and text, getting out model
        titleLabel = new Label(getTitle());
        titleLabel.setStyle("-fx-font-size: 15; -fx-font-weight: bold");
        previewLabel = new Label(getPreview());
        // set positions of text and buttons in note
        AnchorPane.setBottomAnchor(previewLabel, 0.0);
        AnchorPane.setLeftAnchor(previewLabel, 5.0);
        AnchorPane.setLeftAnchor(titleLabel, 5.0);
        AnchorPane.setTopAnchor(titleLabel, 0.0);
        AnchorPane.setRightAnchor(trashButton, 5.0);
        AnchorPane.setTopAnchor(trashButton, 5.0);
        AnchorPane.setBottomAnchor(trashButton, 5.0);
        anchorPane.getChildren().addAll(titleLabel, trashButton, previewLabel);
        anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            update();
        });
        return anchorPane;
    }

    private Button createTrashButton(){
        Image image = new Image(View.class.getResourceAsStream("images/trash.png"), 30, 30, true, false);
        ImageView imageView = new ImageView(image);
        Button trashButton = new Button("", imageView);
        trashButton.setMaxSize(30, 30);
        trashButton.setStyle("-fx-background-color : transparent;");
        return trashButton;
    }
}
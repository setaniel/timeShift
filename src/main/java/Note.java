import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class Note {
    private Stage mainStage;
    private final VBox noteListContent;
    private final Model noteModel;
    private final AnchorPane anchorPaneNote;
    private final NoteEditor noteEditor = new NoteEditor();
    private Note note;
    private Label titleLabel;
    private Label previewLabel;

    public Note(VBox content){
        System.out.println("inside noteConstructor");
        this.noteListContent = content;
        noteModel = new Model();
        note = this;
        anchorPaneNote = createAnchorPane();
    }

    public void setText(String text){
        noteModel.setNoteText(text);
        System.out.println("inside noteSetText");
    }
    public void setTitle(String title){
        noteModel.setTitleLabelText(title);
        titleLabel.setText(title);
        System.out.println("inside noteSetTitle");
    }
    public void setPreview(String preview){
        noteModel.setPreviewLabelText(preview);
        previewLabel.setText(preview);
        System.out.println("inside noteSetPreview");
    }
    public void update(String text){
        System.out.println("inside noteUpdate");
        List<String> list = Arrays.asList(text.split("\n"));
        setTitle(list.get(0));
        if (list.size()>1) setPreview(list.get(1));
        setText(text);
    }
    public AnchorPane getNote(){
        System.out.println("inside noteGetNote");
        System.out.println(anchorPaneNote);
        return anchorPaneNote;
    }
    public String getText(){
        System.out.println("inside noteGetText");
        return noteModel.getNoteText();
    }
    public String getTitle(){
        System.out.println("inside noteGetTitle");
        return noteModel.getTitleLabelText();
    }
    public String getPreview(){
        System.out.println("inside noteGetPreview");
        return noteModel.getPreviewLabelText();
    }

    private AnchorPane createAnchorPane(){
        System.out.println("inside noteCreateAnchorPane");
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: transparent; -fx-border-color: brown;");
        Button trashButton = createTrashButton();
        trashButton.setOnAction(evt -> noteListContent.getChildren().remove(anchorPane));
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
            System.out.println("inside noteAnchorPaneOnClick");
            System.out.println("note == " + note);
            System.out.println("mainStage == " + mainStage);
            noteEditor.noteEditWindow(note, mainStage);
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
    public void setMainStage(Stage stage){
        mainStage = stage;
    }
}

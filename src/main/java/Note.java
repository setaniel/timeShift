import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.time.Instant;
/**
 * Note is a fabric-class of notes.
 * */
public class Note extends AnchorPane {
    private final VBox noteListContent;
    private final Model noteModel;
    private final NoteEditor noteEditor = new NoteEditor();
    private Label titleLabel;
    private Label previewLabel;

    /** Initialize new Note object, initialize model and content(note list) */
    public Note(VBox content){
        this.noteListContent = content;
        noteModel = new Model();
        createNoteInstance();
    }
    /** Create Note, from deserialized model objects. */
    public Note(VBox content, Model deserializedModel){
        this.noteListContent = content;
        noteModel = deserializedModel;
        createNoteInstance();
        this.update(noteModel.getNoteText());
    }

    public void setText(String text){
        noteModel.setNoteText(text);
    }
    public void setTitle(String title){
        noteModel.setTitleLabelText(title);
        titleLabel.setFont(Font.font("Lucida Console", FontWeight.BOLD, 15));
        titleLabel.setText(title);
    }
    public void setPreview(String preview){
        noteModel.setPreviewLabelText(preview);
        previewLabel.setFont(Font.font("Lucida Console", 12));
        previewLabel.setText(preview);
    }
    public void setIndex(int index){
        noteModel.setIndex(index);
    }
    public void update(String text){
        if (text.contains("\n")){
            setTitle(text.indexOf("\n") > 28 ? text.substring(0, 28)+"..." : text.substring(0, text.indexOf("\n")));
            String s = text.substring(text.indexOf("\n")+1); // for code length economy
            setPreview(s.length() > 35 ? s.substring(0, 35) + "..." : s);
        }else {
            setTitle(text.length() > 28 ? text.substring(0, 28)+"..." : text);
            setPreview("Нет дополнительного текста");
        }
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
    public Model getNoteModel(){
        return noteModel;
    }
    public int getIndex(){
        return noteModel.getIndex();
    }

    private void createNoteInstance(){
        this.setStyle("-fx-background-color: transparent; -fx-border-color: brown;");
        Button trashButton = createTrashButton();
        // set note title and text, getting out model
        titleLabel = new Label(getTitle());
        previewLabel = new Label(getPreview());

        Label dateStampLabel = new Label(Instant.now().toString());

        // set positions of text and buttons in note
        AnchorPane.setBottomAnchor(previewLabel, 0.0);
        AnchorPane.setLeftAnchor(previewLabel, 5.0);
        AnchorPane.setLeftAnchor(titleLabel, 5.0);
        AnchorPane.setTopAnchor(titleLabel, 0.0);
        AnchorPane.setRightAnchor(trashButton, 5.0);
        AnchorPane.setTopAnchor(trashButton, 5.0);
        AnchorPane.setBottomAnchor(trashButton, 5.0);
        this.getChildren().addAll(titleLabel, previewLabel, trashButton);
        this.setOnMouseClicked(event -> noteEditor.noteEditWindow(this));
    }

    private Button createTrashButton(){
        Image image = new Image(Note.class.getResourceAsStream("images/trash.png"));
        ImageView imageView = new ImageView(image);
        Button button = new Button("", imageView);
        Instances.setDropShadow(button, Color.BLACK);
        button.setStyle("-fx-background-color : transparent;");
        button.setOnAction(evt -> {
            noteListContent.getChildren().remove(this);
            Serializer.deleteSerializeFiles();
            Serializer.serializeNotes();
        });
        return button;
    }
}

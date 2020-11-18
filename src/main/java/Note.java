import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Note is a fabric-class of notes.
 * */
public class Note extends AnchorPane {
    private final Model noteModel;
    private Label titleLabel;
    private Label previewLabel;
    private Label dateStampLabel;

    /** Initialize new Note object, initialize model and content(note list) */
    public Note(){
        noteModel = new Model();
        createNoteInstance();
    }
    /** Create Note, from deserialized model objects. */
    public Note(Model deserializedModel){
        noteModel = deserializedModel;
        createNoteInstance();
        this.update(noteModel.getNoteText());
    }

    public void setText(String text){
        noteModel.setNoteText(text);
    }
    public void setTitle(String title){
        noteModel.setTitleLabelText(title);
        titleLabel.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 14));
        titleLabel.setText(title);
    }
    public void setPreview(String preview){
        noteModel.setPreviewLabelText(preview);
        previewLabel.setFont(Font.font("Courier New", 12));
        previewLabel.setText(preview);
    }
    public void setNoteDate(){
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM-HH:mm");
        String formatDateTime = ldt.format(formatter);
        dateStampLabel.setText(formatDateTime);
        noteModel.setNoteDate(formatDateTime);
    }
    public void setIndex(int index){
        noteModel.setIndex(index);
    }
    public void update(String text){
        if (text.contains("\n")){
            setTitle(text.indexOf("\n") > 21 ? text.substring(0, 21) + "..." : text.substring(0, text.indexOf("\n")));
            String s = text.substring(text.indexOf("\n")+1);
//            String prev = s.substring(0, s.indexOf("\n"));
            setPreview(s.length() > 26 ? s.substring(0, 26) + "..." : s);
//            setPreview(s.length() > 26 && !s.contains("\n") ? s.substring(0, 26) + "..." : s);
        }else{
            setTitle(text.length() > 21 ? text.substring(0, 21)+"..." : text);
            setPreview("Нет дополнительного текста");
        }
        if (text.length() < 21 && !text.contains("\n") || text.charAt(text.length()-1) == '\n') {
            setTitle(text);
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
    public String getNoteDate(){
        return noteModel.getNoteDate();
    }
    public int getIndex(){
        return noteModel.getIndex();
    }
    public Model getNoteModel(){
        return noteModel;
    }

    private void createNoteInstance(){
        final String cssNoteBorders = "-fx-border-color: blue;\n"
                + "-fx-background-color: transparent;\n"
                + "-fx-stroke-dash-array: 12 2 4 2;\n"
                + "-fx-border-width: 0.3;\n"
                + "-fx-border-color: brown;\n"
                + "-fx-border-style: dashed;\n";

        this.setStyle(cssNoteBorders);
        Button trashButton = createTrashButton();
        // set note title and text, getting out model
        titleLabel = new Label(getTitle());
        previewLabel = new Label(getPreview());
        dateStampLabel = new Label(getNoteDate());
        dateStampLabel.setTextFill(Color.valueOf("#008500"));
        dateStampLabel.setFont(Font.font("Courier New", FontWeight.LIGHT, 9));

        // set positions of labels and buttons in note
        AnchorPane.setBottomAnchor(previewLabel, 0.0);
        AnchorPane.setLeftAnchor(previewLabel, 5.0);

        AnchorPane.setTopAnchor(dateStampLabel, 17.0);
        AnchorPane.setBottomAnchor(dateStampLabel, 15.0);
        AnchorPane.setLeftAnchor(dateStampLabel, 5.0);

        AnchorPane.setLeftAnchor(titleLabel, 5.0);
        AnchorPane.setTopAnchor(titleLabel, 0.0);

        AnchorPane.setRightAnchor(trashButton, 5.0);
        AnchorPane.setTopAnchor(trashButton, 3.0);
        AnchorPane.setBottomAnchor(trashButton, 3.0);
        this.getChildren().addAll(titleLabel, previewLabel, trashButton, dateStampLabel);
        this.setMaxWidth(252.0);
        this.setOnMouseClicked(event -> {
            if (!View.isNoteEditorShow) Utility.noteEditor.showEditor(this);
        });
    }

    private Button createTrashButton(){
        Image image = new Image(Note.class.getResourceAsStream("images/trash.png"));
        ImageView imageView = new ImageView(image);
        Button button = new Button("", imageView);
        Utility.setDropShadow(button, Color.BLACK);
        button.setStyle("-fx-background-color : transparent;");
        button.setOnAction(evt -> {
            Utility.getContent().getChildren().remove(this);
            Serializer.deleteSerializeFiles();
            Serializer.serializeNotes();
        });
        return button;
    }
}

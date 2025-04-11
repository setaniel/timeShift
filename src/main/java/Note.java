import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Note is a fabric-class of notes.
 * @author Setaniel
 * @version 1.0
 * */
class Note extends AnchorPane {
    private final Model noteModel;
    private Label titleLabel;
    private Label previewLabel;
    private Label dateStampLabel;

    /** Initialize new Note object, initialize model and content(note list) */
    Note(){
        noteModel = new Model();
        createNoteInstance();
    }
    /** Create Note, from deserialized model objects. */
    Note(Model deserializedModel){
        noteModel = deserializedModel;
        createNoteInstance();
    }

    void setText(String text){
        noteModel.setNoteText(text);
    }

    void setTitle(String title){
        noteModel.setTitleLabelText(title);
        titleLabel.setText(title);
        setTitleLabelColor();
    }

    void setPreview(String preview){
        noteModel.setPreviewLabelText(preview);
        previewLabel.setText(preview);
        setPreviewLabelColor();
    }

    void setNoteDate(){
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM-HH:mm");
        String formatDateTime = ldt.format(formatter);
        dateStampLabel.setText(formatDateTime);
        noteModel.setNoteDate(formatDateTime);
        setDateStampLabelColor();

    }

    void setIndex(int index){
        noteModel.setIndex(index);
    }

    /** Updates all the information, stored in the note, from the received string*/
    boolean update(String text){
        boolean result = false;
        setText(text);
        titleLabel.setText(null);
        previewLabel.setText(null);
        dateStampLabel.setText(null);

        Pattern pattern = Pattern.compile(".*\\S", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher;
        String[] splitSpace = text.split("\n");

        for (String s : splitSpace) {
            matcher = pattern.matcher(s);
            if (matcher.find()) {
                result = true;
                s = s.trim();

                if (titleLabel.getText() != null) {
                    setPreview(s.length() > 26 ? s.substring(0, 26) + "..." : s);
                    break;
                }else {
                    setTitle(s.length() > 21 ? s.substring(0, 21) + "..." : s);
                }
            }
        }
        setNoteDate();
        if (previewLabel.getText() == null) setPreview("Нет дополнительного текста");
        return result;
    }

    String getText(){
        return noteModel.getNoteText();
    }

    String getTitle(){
        return noteModel.getTitleLabelText();
    }

    String getPreview(){
        return noteModel.getPreviewLabelText();
    }

    String getNoteDate(){
        return noteModel.getNoteDate();
    }

    int getIndex(){
        return noteModel.getIndex();
    }

    Model getNoteModel(){
        return noteModel;
    }

    private void createNoteInstance(){
        final String cssNoteBorders =
                "-fx-background-color: transparent;\n"
                + "-fx-stroke-dash-array: 12 2 4 2;\n"
                + "-fx-border-width: 0.3;\n"
                + "-fx-border-color: red;\n"
                + "-fx-border-style: dashed;\n"
                + "-fx-border-radius: 8;";

        this.setStyle(cssNoteBorders);
        Button trashButton = createTrashButton();
        // set note title and text, getting out model
        titleLabel = new Label(getTitle());
        setTitleLabelColor();
        previewLabel = new Label(getPreview());
        setPreviewLabelColor();
        dateStampLabel = new Label(getNoteDate());
        setDateStampLabelColor();

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
            if (!View.getInstance().isNoteEditorShow()) Utility.getInstance().getController().onNoteClick(this);
        });
    }


    void setTitleLabelColor() {
        titleLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 13));
        titleLabel.setTextFill(ThemeSwitcher.getInstance().getTitleColor());
    }

    void setPreviewLabelColor() {
        previewLabel.setFont(Font.font("Courier New", 12));
        previewLabel.setTextFill(ThemeSwitcher.getInstance().getPreviewColor());
    }

    void setDateStampLabelColor() {
        dateStampLabel.setFont(Font.font("Courier New", FontWeight.LIGHT, 9));
        dateStampLabel.setTextFill(ThemeSwitcher.getInstance().getDateStampColor());
    }

    void setNoteShadow() {
        Utility.getInstance().setDropShadow(this, ThemeSwitcher.getInstance().getNoteShadow());
    }

    private Button createTrashButton(){
        Image image = new Image(Note.class.getResourceAsStream("images/trash.png"));
        ImageView imageView = new ImageView(image);
        Button button = new Button("", imageView);
        Utility.getInstance().setDropShadow(button, Color.BLACK);
        button.setStyle("-fx-background-color : transparent;");
        button.setOnAction(evt -> {
            Utility.getInstance().getContent().getChildren().remove(this);
            Serializer.getInstance().deleteSerializeFiles();
            Serializer.getInstance().serializeNotes();
        });
        return button;
    }
}

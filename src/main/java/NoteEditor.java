import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class NoteEditor extends Pane{
    private static final ImageView okButton = new ImageView(
            new Image(NoteEditor.class.getResourceAsStream("images/done.png")));
    private String noteObjectText;
    private TextArea text;
    private Note editableNote;
    private static ImageView fxAddButton;

    public static void removeOkButton(ImageView fxAddNoteButton){
        fxAddButton = fxAddNoteButton;
        Utility.getRootUI().getChildren().remove(okButton);

        // fading fxAddNoteButton
        Utility.getFadeInAnimation(fxAddButton.opacityProperty()).play();

    }

    public void initEditor(Note note){
        if (this.getChildren().size() > 0) this.getChildren().remove(0);
        this.setPrefSize(250, 395);
        Utility.getRootUI().getChildren().add(okButton);
        Utility.setUIShadows(okButton);

        okButton.setOpacity(0);
        Utility.getFadeOutAnimation(okButton.opacityProperty()).play();

        text = new TextArea();
        this.getChildren().add(text);
        text.setWrapText(true);
        text.setPrefSize(280, 395);
        AnchorPane.setRightAnchor(okButton, 17.0);
        AnchorPane.setTopAnchor(okButton, 140.0);

        editableNote = note;
        Utility.setDropShadow(editableNote, Color.DARKGREY);
        if (editableNote.getText() == null){
            noteObjectText = null;
            text.setText("");
        }else {
            noteObjectText = editableNote.getText();
            text.setText(noteObjectText);
        }
        View.isNoteEditorShow = true;
        setEventHandlers();
    }

    private void textChecks(){
        if (editableNote.update(text.getText())){
            editableNote.setNoteDate();
            View.manageNotes(editableNote);
        }else {
            Utility.getContent().getChildren().remove(editableNote);
        }

        removeEventHandlers();
        SlideScene.hideEditor(this);

        Utility.getFadeInAnimation(okButton.opacityProperty()).play();
        Utility.getFadeOutAnimation(fxAddButton.opacityProperty()).play();

        View.isNoteEditorShow = false;
    }

    private void removeEventHandlers(){

        okButton.setOnMouseClicked(null);
        Utility.getPrimaryStage().getScene().setOnMouseClicked(null);
        this.setOnKeyPressed(null);
        this.setOnMouseExited(null);
        fxAddButton.setOnMouseExited(null);
    }

    private void setEventPrimeClick(){
        // On out of node click event
        Utility.getPrimaryStage().getScene().setOnMouseClicked(event -> {
            event.consume();
            textChecks();
        });
    }

    private void setEventHandlers(){
        // okButton press event
        okButton.setOnMouseClicked(event -> {
            event.consume();
            textChecks();
        });
        // part of event
        this.setOnMouseExited(event -> setEventPrimeClick());
        fxAddButton = Utility.getAddNoteButton();
        fxAddButton.setOnMouseExited(event -> setEventPrimeClick());

        // Close on Esc pressed event
        this.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                event.consume();
                textChecks();
            }
        });
    }
}

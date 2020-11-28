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
    private String currentObjectText;
    private TextArea text;
    private Note newNote;
    private static ImageView fxAddButton;

    public static void removeOkButton(ImageView fxAddNoteButton){
        fxAddButton = fxAddNoteButton;
        Utility.getRootUI().getChildren().remove(okButton);

        // fading fxAddNoteButton
        Utility.getFadeInAnimation(fxAddButton.opacityProperty()).play();

    }

    public void initEditor(Note note){
        newNote = note;
        View.isNoteEditorShow = true;
        setEventHandlers();
        Utility.setDropShadow(newNote, Color.DARKGREY);
        if (this.getChildren().size() > 0) this.getChildren().remove(0);
        this.setPrefSize(250, 395);

        Utility.getRootUI().getChildren().add(okButton);
        Utility.setUIShadows(okButton);
        AnchorPane.setRightAnchor(okButton, 17.0);
        AnchorPane.setTopAnchor(okButton, 140.0);
        okButton.setOpacity(0);
        Utility.getFadeOutAnimation(okButton.opacityProperty()).play();

        text = new TextArea();
        this.getChildren().add(text);
        text.setWrapText(true);
        text.setPrefSize(280, 395);

        if (newNote.getText() == null){
            currentObjectText = null;
            text.setText("");
        }else {
            currentObjectText = newNote.getText();
            text.setText(currentObjectText);
        }
    }

    private void textChecks(){
        if (currentObjectText == null) {
            if (newNote.update(text.getText()) ){
                newNote.setNoteDate();
                View.manageNotes(newNote);
            }/*else {
                Utility.getContent().getChildren().remove(newNote);
            }*/
        }else {
            if (!currentObjectText.equals(text.getText())){
                newNote.update(text.getText());
                View.manageNotes(newNote);
            }
        }


        removeEventHandlers();
        SlideScene.hideEditor(this);
        Utility.getFadeInAnimation(okButton.opacityProperty()).play();
        Utility.getFadeOutAnimation(fxAddButton.opacityProperty()).play();
        fxAddButton.setOnMouseClicked(event -> Utility.getController().onNewNoteClick());
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

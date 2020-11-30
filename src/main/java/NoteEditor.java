import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

class NoteEditor extends Pane{
    private static final ImageView okButton = new ImageView(
            new Image(NoteEditor.class.getResourceAsStream("images/done.png")));
    private String currentObjectText;
    private TextArea text;
    private Note newNote;
    private static ImageView fxAddButton;
    private static NoteEditor instance;

    private NoteEditor(){
    }

    static NoteEditor getInstance() {
        if (instance == null) instance = new NoteEditor();
        return instance;
    }

    static void removeOkButton(ImageView fxAddNoteButton){
        fxAddButton = fxAddNoteButton;
        Utility.getRootUI().getChildren().remove(okButton);
        // fading fxAddNoteButton
        Utility.getFadeOutAnimation(fxAddButton.opacityProperty()).play();
    }

    void initEditor(Note note){
        newNote = note;
        View.isNoteEditorShow = true;
        setEventHandlers();
        if (this.getChildren().size() > 0) this.getChildren().remove(0);
        Utility.setDropShadow(note, ThemeSwitcher.getCurrentTheme().getNoteShadow());
        this.setPrefSize(250, 395);

        Utility.getRootUI().getChildren().add(okButton);
        Utility.setUIShadows(okButton);
        AnchorPane.setRightAnchor(okButton, 17.0);
        AnchorPane.setTopAnchor(okButton, 140.0);
        okButton.setOpacity(0);
        Utility.getFadeInAnimation(okButton.opacityProperty()).play();

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

    private void procTextHideEditor(){
        if (!text.getText().equals("")) {

            if (currentObjectText == null && newNote.update(text.getText())) {
                View.manageNotes(newNote);
            }else if (!currentObjectText.equals(text.getText())){
                newNote.update(text.getText());
                View.manageNotes(newNote);
            }

        } else {
            Utility.getContent().getChildren().remove(newNote);
        }

        removeEventHandlers();
        SlideScene.hideEditor(this);
        Utility.getFadeOutAnimation(okButton.opacityProperty()).play();
        Utility.getFadeInAnimation(fxAddButton.opacityProperty()).play();
        fxAddButton.setOnMouseClicked(event -> Utility.getController().onNewNoteClick());
        View.isNoteEditorShow = false;
    }

    private void removeEventHandlers(){

        okButton.setOnMouseClicked(null);
        Utility.getPrimaryStage().getScene().setOnMouseClicked(null);
        this.setOnKeyPressed(null);
        fxAddButton.setOnMouseExited(null);
    }

    private void setEventPrimeStageClick(){
        // On out of node click event
        Utility.getPrimaryStage().getScene().setOnMouseClicked(event -> {
            event.consume();
            procTextHideEditor();
        });
    }

    private void setEventHandlers(){
        // okButton press event
        okButton.setOnMouseClicked(event -> {
            event.consume();
            procTextHideEditor();
        });
        // part of event
        fxAddButton.setOnMouseExited(event -> setEventPrimeStageClick());

        // Close on Esc pressed event
        this.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                event.consume();
                procTextHideEditor();
            }
        });
    }
}

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NoteEditor {
    private static double xOffset = 0;
    private static double yOffset = 0;
    private static final Image image = new Image(NoteEditor.class.getResourceAsStream("images/done.png"));
    private static final ImageView okButton = new ImageView(image);

    private static Stage stage;
    private static String noteObjectText;
    private static TextArea text;
    private static Note editableNote;
    private static NoteEditor instance;
    private static ImageView fxAddButton;

    private NoteEditor(){
        noteEditWindow();
    }
    public static NoteEditor getInstance(){
        if (instance == null) instance = new NoteEditor();
        return instance;
    }

    private static void noteEditWindow() {
        Utility.setSwitchShadows(okButton, Color.WHITE, Color.BLUE);
        text = new TextArea();
        text.setWrapText(true);
        stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene(setLayout(text, stage), 300, 300, Color.TRANSPARENT);
        scene.getStylesheets().add(NoteEditor.class.getResource("notepad.css").toExternalForm());
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(Utility.getPrimaryStage());
    }

    public void editNote(Note note){
        editableNote = note;
        Utility.setDropShadow(editableNote, Color.DARKGREY);
        if (editableNote.getText() == null){
            noteObjectText = null;
            text.setText("");
        }else {
            noteObjectText = editableNote.getText();
            text.setText(noteObjectText);
        }
        stage.show();
        View.isNoteEditorShow = true;
        stage.requestFocus();

        // set position this modal on parent frame
        stage.setX(Utility.getPrimaryStage().getX() + 30);
        stage.setY(Utility.getPrimaryStage().getY() + 60);
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(22);
        setEventHandlers();
    }

    private static void textChecks(){
        if (noteObjectText == null){ // new note object, if symbols >= 1 - create note
            if (text.getText().length() > 0){
                editableNote.update(text.getText());
                editableNote.setNoteDate();
                View.manageNotes(editableNote);
            }
        } else if (!noteObjectText.equals(text.getText())){ // existing note, but has changes
            editableNote.update(text.getText());
            View.manageNotes(editableNote);
            editableNote.setNoteDate();
        }
        if (noteObjectText != null && text.getText().length() == 0){ // Deleted all content, deleted note
            Utility.getContent().getChildren().remove(editableNote);
        }
        removeEventHandlers();
        stage.hide();
        View.isNoteEditorShow = false;
    }

    private static void removeEventHandlers(){

        okButton.setOnMouseClicked(null);
        Main.primeStage.getScene().setOnMouseClicked(null);
        stage.getScene().setOnKeyPressed(null);
        stage.getScene().setOnMouseExited(null);
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
        stage.getScene().setOnMouseExited(event -> setEventPrimeClick());
        fxAddButton = Utility.getAddNoteButton();
        fxAddButton.setOnMouseExited(event -> setEventPrimeClick());

        // Close on Esc pressed event
        stage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                // ----
                event.consume();
                textChecks();
                // ----
            }
        });
    }

    private static VBox setLayout(TextArea editableText, Stage stage){
        AnchorPane anchorPane = new AnchorPane(okButton);
        AnchorPane.setRightAnchor(okButton, 10.0);
        AnchorPane.setTopAnchor(okButton, 4.0);
        AnchorPane.setBottomAnchor(okButton, 4.0);
        VBox layout = new VBox(editableText, anchorPane);
        layout.setPadding(new Insets(10, 10, 0, 10));
        layout.setStyle("-fx-background-radius: 16;" +
                "-fx-background-color: rgb(45, 45, 50), rgb(60, 60, 65);" +
                "-fx-background-insets: 0, 0 1.ser 1.ser 0;");
        VBox.setVgrow(editableText, Priority.ALWAYS);

        layout.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        layout.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        return layout;
    }
}

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class NoteEditor {

    private static final ImageView okButton = new ImageView(
            new Image(NoteEditor.class.getResourceAsStream("images/done.png")));
    private static Stage stage;
    private static String noteObjectText;
    public static TextArea text;
    private static Note editableNote;
    private static NoteEditor instance;
    private static ImageView fxAddButton;
    public static final StackPane parentContainer = new StackPane();
    public static final Pane parentRoot = new Pane(parentContainer);

    private NoteEditor(){
        noteEditWindow();
    }
    public static NoteEditor getInstance(){
        if (instance == null) instance = new NoteEditor();
        return instance;
    }

    private static void noteEditWindow() {
        Utility.setUIShadows(okButton);
        text = new TextArea();
        text.setWrapText(true);
        text.setPrefSize(280, 395);
        parentRoot.setBackground(Background.EMPTY);
        stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene(parentRoot, 280, 395, Color.TRANSPARENT);
        scene.getStylesheets().add(NoteEditor.class.getResource("notepad.css").toExternalForm());
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(Utility.getPrimaryStage());
        stage.show();
    }

    public void showEditor(Note note){
        Utility.rootUI.getChildren().add(okButton);
        AnchorPane.setRightAnchor(okButton, 5.0);
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
        stage.requestFocus();

        // set position this modal on parent frame
        stage.setX(Utility.getPrimaryStage().getX() + 30);
        stage.setY(Utility.getPrimaryStage().getY() + 50);
//        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(22);
        setEventHandlers();


        text.translateXProperty().set(parentRoot.getWidth());

        parentContainer.getChildren().add(text);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(text.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(3), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
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
//        stage.hide();
        View.isNoteEditorShow = false;

        text.translateXProperty().set(0);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(text.translateXProperty(), 280, Interpolator.EASE_OUT);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(3), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        System.out.println(parentContainer.getChildren().size());
        Utility.rootUI.getChildren().remove(okButton);
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
                event.consume();
                textChecks();
            }
        });
    }

/*    private static VBox setLayout(TextArea editableText){

        VBox layout = new VBox(editableText *//*anchorPane*//*);
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
    }*/
}

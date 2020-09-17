import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.*;

public class SetNote {
    private double xOffset = 0;
    private double yOffset = 0;

    static final Button button = new Button("Done");
    public TextArea showWindow(TextArea textArea, Window mod) {
        Stage stg = new Stage(StageStyle.UNDECORATED);
        //-----------------------------------
        TextArea editableText = new TextArea(textArea.getText());
        VBox layout = new VBox(editableText, button);
        layout.setPadding(new Insets(10));
        layout.setStyle("-fx-background-radius: 6;" +
                "-fx-background-color: rgb(45, 45, 50), rgb(60, 60, 65);" +
                "-fx-background-insets: 0, 0 1 1 0;");
        VBox.setVgrow(editableText, Priority.ALWAYS);
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(22); 

        layout.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        layout.setOnMouseDragged(event -> {
            stg.setX(event.getScreenX() - xOffset);
            stg.setY(event.getScreenY() - yOffset);
        });

        Scene scene = new Scene(layout, 300, 300, Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("notepad.css").toExternalForm());
        stg.setScene(scene);
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(mod);
        // set position this modal on parent window
        stg.setX(mod.getX() + 30);
        stg.setY(mod.getY() + 60);
        stg.show();

        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stg.close();
            }
        });
        return editableText;
    }
}

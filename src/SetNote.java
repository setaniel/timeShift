import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SetNote {

    static final Button button = new Button("Done");
    public TextArea showWindow(TextArea textArea, Window mod) {
        Stage stg = new Stage();
        //-----------------------------------
        TextArea editableText = new TextArea(textArea.getText());
        VBox layout = new VBox(editableText, button);
        layout.setPadding(new Insets(10));
        VBox.setVgrow(editableText, Priority.ALWAYS);
        Scene scene = new Scene(layout, 300, 300);
        scene.getStylesheets().add(getClass().getResource("notepad.css").toExternalForm());
        stg.setScene(scene);
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(mod);
        // set position this modal on parent window
        stg.setX(mod.getX() + 0);
        stg.setY(mod.getY() + 0);
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

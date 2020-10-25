import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.lang3.*;

public class Settings {
    private static double xOffset = 0;
    private static double yOffset = 0;
    public static void drawSettings(){
        Image image = new Image(NoteEditor.class.getResourceAsStream("images/done.png"));
        ImageView imageView = new ImageView(image);
        Button setTimeButton = new Button("", imageView);
        //____
        VBox layout = new VBox();
        TextField text = new TextField();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(layout);
        AnchorPane.setRightAnchor(setTimeButton, 15.0);
        AnchorPane.setLeftAnchor(text, 15.0);
        AnchorPane.setBottomAnchor(layout, 10.0);
        AnchorPane.setLeftAnchor(layout, 10.0);
        AnchorPane.setRightAnchor(layout, 10.0);
        AnchorPane.setTopAnchor(layout, 10.0);
        layout.setBackground(new Background(new BackgroundFill(Paint.valueOf("#e8e4db"), new CornerRadii(16), Insets.EMPTY)));
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 10, 0, 10));


        text.setFont(Font.font("Lucida Console", FontWeight.BOLD, 15));
        layout.getChildren().addAll(text, setTimeButton);

        Stage stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene(anchorPane, 200, 100, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(Utility.getPrimaryStage());
        // set position this modal on parent frame
        stage.setX(Utility.getPrimaryStage().getX() + 145);
        stage.setY(Utility.getPrimaryStage().getY() + 350);
        stage.show();
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(22);
        anchorPane.setStyle("-fx-background-radius: 16;" +
                "-fx-background-color: rgb(45, 45, 50), rgb(60, 60, 65);" +
                "-fx-background-insets: 0, 0 1.ser 1.ser 0;");

        //-----------------------------------
        setTimeButton.setOnAction(event -> {
            if (StringUtils.isNumeric(text.getCharacters())){
                Utility.setPomodoroTime(Integer.parseInt(text.getText()));
                stage.close();
            }
        });
        // on out of node click
        Utility.closeOnActions(stage);


        // Drag window
        layout.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        layout.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
}

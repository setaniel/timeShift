import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class Info {
    private static double xOffset = 0;
    private static double yOffset = 0;
    public static void drawInfo(){
        Image image = new Image(NoteEditor.class.getResourceAsStream("images/done.png"));
        ImageView imageView = new ImageView(image);
        Button doneButton = new Button("", imageView);
        //____
        VBox layout = new VBox();
        layout.setAlignment(Pos.BASELINE_CENTER);
        layout.setPadding(new Insets(10, 10, 0, 10));
        Label author = new Label("Kirill Orlov @setaniel");
        Label mail = new Label("i4data@ya.ru");
        Hyperlink site = new Hyperlink("https://setaniel.github.io");
        site.setOnMouseClicked(event -> {
            try {
                Desktop.getDesktop().browse(new URI(site.getText()));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });



        layout.getChildren().addAll(author, mail, site);
        /*site.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                getHostServices().showDocument("https://eclipse.org");
            }
        });*/

        Stage stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene(layout, 300, 300, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.getStylesheets().add(Info.class.getResource("notepad.css").toExternalForm());
        stage.initOwner(Instances.getPrimaryStage());
        // set position this modal on parent frame
        stage.setX(Instances.getPrimaryStage().getX() + 30);
        stage.setY(Instances.getPrimaryStage().getY() + 60);
        stage.show();
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(22);
        layout.setStyle("-fx-background-radius: 16;" +
                "-fx-background-color: rgb(45, 45, 50), rgb(60, 60, 65);" +
                "-fx-background-insets: 0, 0 1.ser 1.ser 0;");

        //-----------------------------------
        doneButton.setOnAction(event -> stage.close());
        layout.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        layout.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        Instances.closeOnClickOutThis(stage);
    }
}

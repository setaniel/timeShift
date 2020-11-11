import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.omg.PortableServer.POA;


public class SlideScene extends Application
{
    Scene scene;
    Pane paneRoot;
    StackPane parentContainer;
    AnchorPane temp;
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        Label text = new Label("THIS IS <-FIRST-> SCENE");
        Button nextSceneBtn = new Button("Next scene");
        VBox vBox = new VBox(text, nextSceneBtn);
        AnchorPane anchorPane = new AnchorPane(vBox);
        parentContainer = new StackPane(anchorPane);
        paneRoot = new Pane(parentContainer);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(400, 400);
        // Create the Scene
        scene = new Scene(paneRoot);
        scene.getStylesheets().add(SlideScene.class.getResource("notepad.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        nextSceneBtn.setOnAction(event -> showEditor(parentContainer));
    }

    AnchorPane nextScene() {
        Button button = new Button("Close");
        VBox vBox = new VBox(new TextArea(), button);
        AnchorPane anchorPane = new AnchorPane(vBox);
        anchorPane.setPrefSize(400, 400);
        vBox.setPrefSize(400, 400);
        button.setOnAction(event -> {
            hideEditor(anchorPane);

        });
        return anchorPane;
    }


    void hideEditor(AnchorPane root){
        root.translateXProperty().set(0);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 400, Interpolator.EASE_OUT);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.6), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        System.out.println(parentContainer.getChildren().size());
    }


    void showEditor(StackPane parentContainer){
        AnchorPane root = nextScene(); // куда впихиваю
        root.translateXProperty().set(paneRoot.getWidth());
        parentContainer.getChildren().add(root);
        if (parentContainer.getChildren().size() == 3) parentContainer.getChildren().remove(1);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.6), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
}
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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class SlideScene extends Application
{
    static Scene scene;
    static Pane parentRoot;
    static StackPane parentContainer;
    static AnchorPane temp;
    static NoteEditor noteEditor = Utility.noteEditor;
    public static void main(String[] args)
    {
        Application.launch(args);
    }
    SlideScene(){
        initScene();
    }

    public static void initScene(){
        Stage stage = new Stage();
        // first init?
        Label text = new Label("THIS IS <-FIRST-> SCENE");
        Button nextSceneBtn = new Button("Next scene");
        VBox vBox = new VBox(text, nextSceneBtn);
        AnchorPane anchorPane = new AnchorPane(vBox);
        // ________

        parentContainer = new StackPane();
        parentRoot = new Pane(parentContainer);
        vBox.setAlignment(Pos.CENTER);
        // Create the Scene
        Pane scenePane = new Pane(parentRoot);
        scenePane.setPrefSize(280, 395);
        scenePane.setBackground(Background.EMPTY);
        scenePane.setStyle("-fx-border-color: red");
        scene = new Scene(scenePane, Color.TRANSPARENT);
        scene.getStylesheets().add(SlideScene.class.getResource("notepad.css").toExternalForm());
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(Main.getPrimeStage());
        stage.show();
        nextSceneBtn.setOnAction(event -> showEditor());
        // set position this modal on parent frame
        stage.setX(Main.getPrimeStage().getX() + 30);
        stage.setY(Main.getPrimeStage().getY() + 50);
    }

    @Override
    public void start(Stage stage) {
        // first init?
        Label text = new Label("THIS IS <-FIRST-> SCENE");
        Button nextSceneBtn = new Button("Next scene");
        VBox vBox = new VBox(text, nextSceneBtn);
        AnchorPane anchorPane = new AnchorPane(vBox);
        // ________

        parentContainer = new StackPane(nextScene());
        parentRoot = new Pane(parentContainer);
        vBox.setAlignment(Pos.CENTER);
        // Create the Scene
        Pane scenePane = new Pane(parentRoot);
        scenePane.setBackground(Background.EMPTY);
        scenePane.setPrefSize(280, 395);
        scene = new Scene(scenePane);
        scene.getStylesheets().add(SlideScene.class.getResource("notepad.css").toExternalForm());
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(Main.getPrimeStage());
        stage.show();
        nextSceneBtn.setOnAction(event -> showEditor());
        // set position this modal on parent frame
        stage.setX(Main.getPrimeStage().getX() + 30);
        stage.setY(Main.getPrimeStage().getY() + 50);
    }

    static AnchorPane nextScene() {
        Button button = new Button("Close");
        VBox vBox = new VBox(new TextArea(), button);
        AnchorPane anchorPane = new AnchorPane(vBox);
        anchorPane.setPrefSize(280, 395);
        vBox.setPrefSize(280, 395);
        button.setOnAction(event -> {
            hideEditor(anchorPane);

        });
        return anchorPane;
    }

    static void hideEditor(AnchorPane root){
        root.translateXProperty().set(0);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 280, Interpolator.EASE_OUT);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.6), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        System.out.println(parentContainer.getChildren().size());
    }


    public static void showEditor(){
        System.out.println("showEditor");
        AnchorPane root = nextScene(); // куда впихиваю
        root.translateXProperty().set(parentRoot.getWidth());
        parentContainer.getChildren().add(root);
        if (parentContainer.getChildren().size() == 3) parentContainer.getChildren().remove(1);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.6), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
}
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


class SlideScene {
    private Pane parentRoot;
    private StackPane parentContainer;
    private static SlideScene instance;

    static SlideScene getInstance(){
        if (instance == null) instance = new SlideScene();
        return instance;
    }

    void initScene(){
        parentRoot = Utility.getContentAnchor();
        parentContainer = Utility.getContentStack();
    }

    void hideEditor(Pane root){
        root.translateXProperty().set(0);
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), -318, Interpolator.EASE_OUT);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }


    void showEditor(){
        Pane root = NoteEditor.getInstance();

        if (parentContainer.getChildren().size() == 2) {
            parentContainer.getChildren().remove(1);
        }
        parentContainer.getChildren().add(root);

        root.translateXProperty().set( -parentRoot.getWidth());
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_OUT);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        root.getChildren().get(0).requestFocus();
    }
}
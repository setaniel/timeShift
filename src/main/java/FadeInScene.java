import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class FadeInScene extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = new StackPane( new Label( "Hello World!" ) );
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.initStyle( StageStyle.TRANSPARENT );
//        primaryStage.setOpacity( 0 );
        primaryStage.show();
        root.setOpacity(0);


        Animation anim = getAnimation( root.opacityProperty() );
        anim.play();
    }

    private Animation getAnimation( DoubleProperty property ) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame( Duration.ZERO, new KeyValue( property, 0 ) ),
                new KeyFrame( Duration.millis( 5000 ), new KeyValue( property, 1 ) )
        );
        return timeline;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
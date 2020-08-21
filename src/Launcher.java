import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Launcher extends Application{

    private Pane root = new Pane();
    private Scene scene;

    private ScrollPane scroller;
    private Pane content = new Pane();

    private TextField textF = new TextField();
    private TextArea textA = new TextArea();

    private Text textHolder = new Text();
    private double oldHeight = 0;


    @Override
    public void start(Stage stage) throws Exception {

        root.getChildren().addAll(yourSP());
        scene = new Scene(root,300,316);
        stage.setScene(scene);
        stage.show();

    }

    private ScrollPane yourSP(){

        content.setMinSize(300, 300);
        textF.setPrefSize(260, 40);
        textF.setLayoutX(20);
        textF.setLayoutY(20);
        textA.setPrefSize(260, 200);
        textA.setLayoutX(20);
        textA.setLayoutY(80);

        textA.getStylesheets().add(getClass().getResource("texta.css").toExternalForm());
        content.getChildren().addAll(textA,textF);

        /*************************@Uluk Biy Code**************************/
        textA.setWrapText(true);
        textHolder.textProperty().bind(textA.textProperty());
        textHolder.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                if (oldHeight != newValue.getHeight()) {
                    oldHeight = newValue.getHeight();
                    textA.setPrefHeight(textHolder.getLayoutBounds().getHeight() + 20);
                    System.out.println(textHolder.getLayoutBounds().getHeight());
                }
            }
        });
        /****************************************************************/

        scroller = new ScrollPane(content);
        scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
        scroller.setPrefSize(300, 316);


        return scroller;
    }


    public static void main(String[] args) {

        launch(args);

    }

}
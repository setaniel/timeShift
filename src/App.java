import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App extends Application {
    public Button myButton;
    public TextField myTextField;
    public TextFlow textFlow;

    private int tfCount = 1;

    //--------------------------
    private Pane root = new Pane();
    private Scene scene;

    private ScrollPane scroller;
    private Pane content = new Pane();

    private TextField textF = new TextField();
    private TextArea textA = new TextArea();

    private Text textHolder = new Text();
    private double oldHeight = 0;
    public VBox frameVB;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));

        root.setStyle("-fx-background-radius: 6;" +
                "-fx-background-color: rgb(45, 45, 50), rgb(60, 60, 65);" +
                "-fx-background-insets: 0, 0 1 1 0;");
        frameVB.getChildren().addAll(yourSP());
        Scene scene = new Scene(root, 420, 565, Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.show();

        // За верхнюю часть высотой 22 можно перемещать окно:
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(22);
    }
    @FXML
    void showDateTime(ActionEvent event) {
        System.out.println("Button Clicked!");
        Date now= new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        // Model Data
        String dateTimeString = df.format(now);
        // Show in VIEW
        myTextField.setText(dateTimeString);
    }

    private String dateTime(){
        return new Date().toString();
    }

    @FXML
    private void addText(MouseEvent event){
        TextArea textArea = new TextArea((String.valueOf(tfCount)));
        tfCount++;
        textArea.setPrefRowCount(2);
        textArea.setPrefColumnCount(22);
        textArea.setStyle("-fx-background-color: rgba(53,89,119,0.4); -fx-background-color: transparent;");
        textFlow.getChildren().add(textArea);
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

        scroller = new ScrollPane(frameVB);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setPrefSize(300, 316);


        return scroller;
    }


}

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Utility {
    private static Node root;
    private static Stage primaryStage;
    private static VBox content;
    private static Label netLabel;
    private static Label weatherLabel;
    private static boolean appClosingState = false;
    private static int pomodoroTime = 25;
    public static NoteEditor noteEditor = NoteEditor.getInstance();

    // Setters
    public static void setTraffic(AnchorPane fxTrafficPane){
        StackPane buildedTraffic = Traffic.getTraffic();
        ImageView yaImage = new ImageView(new Image(Utility.class.getResourceAsStream("images/ya.png")));
        Label trafficText = new Label("Traffic");
        trafficText.setFont(Font.font("Courier New", FontWeight.BOLD, 13));
        fxTrafficPane.getChildren().addAll(yaImage, trafficText, buildedTraffic);
        AnchorPane.setLeftAnchor(yaImage, 2.0);
        AnchorPane.setLeftAnchor(trafficText, 30.0);
        AnchorPane.setRightAnchor(buildedTraffic, 10.0);
    }
    public static void setWeatherLabel(Label fxWeather){
        weatherLabel = fxWeather;
        // Adding the shadow
        InnerShadow shadow = new InnerShadow();
        weatherLabel.setEffect(shadow);
        weatherLabel.setAlignment(Pos.CENTER);
        weatherLabel.setPadding(new Insets(5, 8, 5, 8));
        weatherLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 13));
        weatherLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf("#98ecf2"),
                new CornerRadii(16), Insets.EMPTY)));
    }
    public static void setNetLabel(Label fxNetLabel){
        // Adding the shadow
        InnerShadow shadow = new InnerShadow();
        netLabel = fxNetLabel;
        netLabel.setEffect(shadow);
        netLabel.setAlignment(Pos.CENTER);
        netLabel.setPadding(new Insets(5, 8, 5, 8));
        netLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
    }
    public static void setContent(VBox fxContent){
        content = fxContent;
    }
    public static void setPrimaryStage(Stage primeStage){
        primaryStage = primeStage;
    }
    public static void setDropShadow(Node node, Color color){
        DropShadow shadow = new DropShadow(10.0, color);
        // Adding the shadow when the mouse cursor is on
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> node.setEffect(shadow));
        // Removing the shadow when the mouse cursor is off
        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> node.setEffect(null));
    }
    public static void setInnerShadow(Node node, Color color){
        InnerShadow shadow = new InnerShadow(10.0, color);
        // Adding the shadow when the mouse cursor is on
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> node.setEffect(shadow));
        // Removing the shadow when the mouse cursor is off
        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> node.setEffect(null));
    }
    public static void setIsAppClosing(boolean state){
        appClosingState = state;
    }
    public static void setRoot(Node node){
        root = node;
    }
    public static void setPomodoroTime(int time){
        pomodoroTime = time;
    }


    // Getters
    public static Node getRoot(){
        return root;
    }
    public static Label getWeatherLabel(){
        return weatherLabel;
    }
    public static Label getNetLabel(){
        return netLabel;
    }
    public static int getPomodoroTime(){
        return pomodoroTime;
    }
    public static VBox getContent(){
        return content;
    }
    public static Stage getPrimaryStage(){
        return primaryStage;
    }
    public static boolean isAppClosing() {
        return appClosingState;
    }

    public static void setSwitchInnerShadows(Node node, Color viewColor, Color actionColor){
        InnerShadow viewShadow = new InnerShadow(10.0, viewColor);
        InnerShadow actionShadow = new InnerShadow(10.0, actionColor);
        node.setEffect(viewShadow);
        node.setOnMouseEntered(event -> node.setEffect(actionShadow));
        node.setOnMouseExited(event -> node.setEffect(viewShadow));
    }

    public static void closeOnActions(Stage modalStage) {
        EventHandler<MouseEvent> primeStageClicked=new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // on click actions here
                modalStage.close();
                Utility.root.removeEventFilter(MouseEvent.MOUSE_CLICKED, this); // at the bottom
            }
        };
        Utility.root.addEventFilter(MouseEvent.MOUSE_CLICKED, primeStageClicked); // add the eventhandler to the node
        // Close on Esc pressed
        modalStage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) modalStage.close();
        });
    }
}

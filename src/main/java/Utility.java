import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
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
    private static Controller controller;
    private static Stage primaryStage;
    private static boolean appClosingState = false;
    private static int pomodoroTime = 25;
    private static NoteEditor noteEditor = new NoteEditor();




    // Setters
    public static void setController(Controller controller) {
        Utility.controller = controller;
    }
    public static void setPrimaryStage(Stage primeStage){
        primaryStage = primeStage;
    }
    public static void setIsAppClosing(boolean state){
        appClosingState = state;
    }
    public static void setPomodoroTime(int time){
        pomodoroTime = time;
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
    public static void setUIShadows(Node node){
        DropShadow blackShadow = new DropShadow(10.0, Color.BLACK);
        DropShadow redShadow = new DropShadow(17.0, Color.RED);
        node.setEffect(blackShadow);
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> node.setEffect(redShadow));
        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> node.setEffect(blackShadow));
    }
    public static void setSwitchInnerShadows(Node node, Color viewColor, Color actionColor){
        InnerShadow viewShadow = new InnerShadow(10.0, viewColor);
        InnerShadow actionShadow = new InnerShadow(10.0, actionColor);
        node.setEffect(viewShadow);
        node.setOnMouseEntered(event -> node.setEffect(actionShadow));
        node.setOnMouseExited(event -> node.setEffect(viewShadow));
    }
    public static void setSwitchShadows(Node node, Color viewColor, Color actionColor){
        DropShadow viewShadow = new DropShadow(10.0, viewColor);
        InnerShadow actionShadow = new InnerShadow(10.0, actionColor);
        node.setEffect(viewShadow);
        node.setOnMouseEntered(event -> node.setEffect(actionShadow));
        node.setOnMouseExited(event -> node.setEffect(viewShadow));
    }
    public static void setOKButtonShadows(Node node){
        DropShadow blackShadow = new DropShadow(10.0, Color.BLACK);
        DropShadow greenShadow = new DropShadow(10.0, Color.GREEN);
        node.setEffect(blackShadow);
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> node.setEffect(greenShadow));
        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> node.setEffect(blackShadow));
    }


    // Getters
    public static Pane getContentAnchor() {
        return controller.fxContentAnchor;
    }
    public static StackPane getContentStack() {
        return controller.fxContentStack;
    }
    public static ImageView getAddNoteButton(){
        return controller.fxAddNoteButton;
    }
    public static Label getWeatherLabel(){
        // Adding the shadow
        InnerShadow shadow = new InnerShadow();
        controller.fxWeatherLabel.setEffect(shadow);
        controller.fxWeatherLabel.setAlignment(Pos.CENTER);
        controller.fxWeatherLabel.setPadding(new Insets(5, 8, 5, 8));
        controller.fxWeatherLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 13));
        controller.fxWeatherLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf("#98ecf2"),
                new CornerRadii(16), Insets.EMPTY)));
        return controller.fxWeatherLabel;
    }
    public static Label getNetLabel(){
        // Adding the shadow
        InnerShadow shadow = new InnerShadow();
        controller.fxNetLabel.setEffect(shadow);
        controller.fxNetLabel.setAlignment(Pos.CENTER);
        controller.fxNetLabel.setPadding(new Insets(5, 8, 5, 8));
        controller.fxNetLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
        return controller.fxNetLabel;
    }
    public static int getPomodoroTime(){
        return pomodoroTime;
    }
    public static VBox getContent(){
        return controller.fxContent;
    }
    public static AnchorPane getRootUI() {
        return controller.fxRootUI;
    }
    public static Stage getPrimaryStage(){
        return primaryStage;
    }
    public static boolean isAppClosing() {
        return appClosingState;
    }
    public static NoteEditor getNoteEditor() {
        return noteEditor;
    }

}

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
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
import javafx.util.Duration;

class Utility {

    private static Controller controller;
    private static Stage primaryStage;
    private static boolean appClosingState = false;
    private static int pomodoroTime = 25;
    // Setters
    static void setController(Controller controller) {
        Utility.controller = controller;
    }
    static void setPrimaryStage(Stage primeStage) {
        primaryStage = primeStage;
    }
    static void setIsAppClosing(boolean state) {
        appClosingState = state;
    }
    static void setPomodoroTime(int time) {
        pomodoroTime = time;
    }

    static void setDropShadow(Node node, Color color) {
        DropShadow shadow = new DropShadow(10.0, color);
        // Adding the shadow when the mouse cursor is on
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> node.setEffect(shadow));
        // Removing the shadow when the mouse cursor is off
        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> node.setEffect(null));
    }
    static void setInnerShadow(Node node) {
        InnerShadow shadow = new InnerShadow(10.0, Color.RED);
        // Adding the shadow when the mouse cursor is on
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> node.setEffect(shadow));
        // Removing the shadow when the mouse cursor is off
        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> node.setEffect(null));
    }
    static void setUIShadows(Node node) {
        DropShadow blackShadow = new DropShadow(10.0, Color.BLACK);
        DropShadow redShadow = new DropShadow(17.0, Color.RED);
        node.setEffect(blackShadow);
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> node.setEffect(redShadow));
        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> node.setEffect(blackShadow));
    }
    static void setSwitchInnerShadows(Node node) {
        InnerShadow viewShadow = new InnerShadow(10.0, Color.BLACK);
        InnerShadow actionShadow = new InnerShadow(10.0, Color.RED);
        node.setEffect(viewShadow);
        node.setOnMouseEntered(event -> node.setEffect(actionShadow));
        node.setOnMouseExited(event -> node.setEffect(viewShadow));
    }

    static void setSwitchInnerShadows(Node node, Color constant, Color action) {
        InnerShadow constantShadow = new InnerShadow(10.0, constant);
        InnerShadow actionShadow = new InnerShadow(10.0, action);
        node.setEffect(constantShadow);
        node.setOnMouseEntered(event -> node.setEffect(actionShadow));
        node.setOnMouseExited(event -> node.setEffect(constantShadow));
    }

    static void setStaticInnerShadow(Node node) {
        InnerShadow innerShadow = new InnerShadow(10.0, Color.BLACK);
        node.setEffect(innerShadow);
    }
    static void setStaticDropShadow(Node node, Color color) {
        DropShadow dropShadow = new DropShadow(10.0, color);
        node.setEffect(dropShadow);
    }


    // Getters
    static Pane getContentAnchor() {
        return controller.fxContentAnchor;
    }
    static StackPane getContentStack() {
        return controller.fxContentStack;
    }

    static Label getWeatherLabel(){
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
    static Label getNetLabel(){
        // Adding the shadow
        InnerShadow shadow = new InnerShadow();
        controller.fxNetLabel.setEffect(shadow);
        controller.fxNetLabel.setAlignment(Pos.CENTER);
        controller.fxNetLabel.setPadding(new Insets(5, 8, 5, 8));
        controller.fxNetLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
        return controller.fxNetLabel;
    }
    static int getPomodoroTime(){
        return pomodoroTime;
    }
    static VBox getContent(){
        return controller.fxContent;
    }
    static AnchorPane getRootUI() {
        return controller.fxRootUI;
    }
    static Stage getPrimaryStage(){
        return primaryStage;
    }
    static boolean isAppClosing() {
        return appClosingState;
    }

    static Controller getController() {
        return controller;
    }

    // Animations

    /** Usage:
     * getFadeOutAnimation( Node.opacityProperty() ).play();
     */
    static Animation getFadeOutAnimation(DoubleProperty property) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame( Duration.ONE, new KeyValue( property, 1 ) ),
                new KeyFrame( Duration.seconds(0.4), new KeyValue( property, 0 ) )
        );
        return timeline;
    }

    /** Usage: <br>
     * Node.setOpacity( 0 ); <br>
     * getFadeInAnimation( Node.opacityProperty() ).play();
     */
    static Animation getFadeInAnimation(DoubleProperty property ) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame( Duration.ZERO, new KeyValue( property, 0 ) ),
                new KeyFrame( Duration.seconds(0.4), new KeyValue( property, 1 ) )
        );
        return timeline;
    }

    static void setCloseOnActions(Stage stage, Node fxButton, Object object) {

        fxButton.setOnMouseExited(event -> {
            Utility.getPrimaryStage().getScene().setOnMouseClicked(event1 -> removeOnActEvents(stage, fxButton, object));
            // Close on Esc pressed
            stage.getScene().setOnKeyPressed(event2 -> {
                if (event2.getCode() == KeyCode.ESCAPE) removeOnActEvents(stage, fxButton, object);
            });
        });
    }



    private static void removeOnActEvents(Stage stage, Node fxButton, Object object) {
        Utility.getPrimaryStage().getScene().setOnMouseClicked(null);
        stage.getScene().setOnKeyPressed(null);
        if (object.getClass().getName().equals("Settings")){
            Settings.getInstance().setSettingsShow(false);
        }else {
            Info.getInstance().setInfoShow(false);
        }
        fxButton.setOnMouseExited(null);
        Utility.getFadeOutAnimation(stage.opacityProperty()).play();
    }
    static void removeButtonHandler(ImageView fxButton){
        fxButton.setOnMouseClicked(null);
    }
}

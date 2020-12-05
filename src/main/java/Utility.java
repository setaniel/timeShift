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
    private Controller controller;
    private Stage primaryStage;
    private boolean appClosingState = false;
    private int pomodoroTime = 25;
    private static Utility instance;

    static Utility getInstance(){
        if (instance == null) instance = new Utility();
        return instance;
    }

    private Utility(){
    }

    // Setters
    void setController(Controller fxController) {
        controller = fxController;
    }

    void setPrimaryStage(Stage primeStage) {
        primaryStage = primeStage;
    }

    void setIsAppClosing(boolean state) {
        appClosingState = state;
    }

    void setPomodoroTime(int time) {
        pomodoroTime = time;
    }

    void setDropShadow(Node node, Color color) {
        DropShadow shadow = new DropShadow(10.0, color);
        // Adding the shadow when the mouse cursor is on
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> node.setEffect(shadow));
        // Removing the shadow when the mouse cursor is off
        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> node.setEffect(null));
    }

    void setInnerShadow(Node node) {
        InnerShadow shadow = new InnerShadow(10.0, Color.RED);
        // Adding the shadow when the mouse cursor is on
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> node.setEffect(shadow));
        // Removing the shadow when the mouse cursor is off
        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> node.setEffect(null));
    }

    void setUIShadows(Node node) {
        DropShadow blackShadow = new DropShadow(10.0, Color.BLACK);
        DropShadow redShadow = new DropShadow(17.0, Color.RED);
        node.setEffect(blackShadow);
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> node.setEffect(redShadow));
        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> node.setEffect(blackShadow));
    }

    void setSwitchInnerShadows(Node node) {
        InnerShadow viewShadow = new InnerShadow(10.0, Color.BLACK);
        InnerShadow actionShadow = new InnerShadow(10.0, Color.RED);
        node.setEffect(viewShadow);
        node.setOnMouseEntered(event -> node.setEffect(actionShadow));
        node.setOnMouseExited(event -> node.setEffect(viewShadow));
    }

    void setSwitchInnerShadows(Node node, Color constant, Color action) {
        InnerShadow constantShadow = new InnerShadow(10.0, constant);
        InnerShadow actionShadow = new InnerShadow(10.0, action);
        node.setEffect(constantShadow);
        node.setOnMouseEntered(event -> node.setEffect(actionShadow));
        node.setOnMouseExited(event -> node.setEffect(constantShadow));
    }

    void setStaticInnerShadow(Node node) {
        InnerShadow innerShadow = new InnerShadow(10.0, Color.BLACK);
        node.setEffect(innerShadow);
    }

    void setStaticDropShadow(Node node, Color color) {
        DropShadow dropShadow = new DropShadow(10.0, color);
        node.setEffect(dropShadow);
    }

    // Getters
    Pane getContentAnchor() {
        return controller.fxContentAnchor;
    }

    StackPane getContentStack() {
        return controller.fxContentStack;
    }

    Label getWeatherLabel(){
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

    Label getNetLabel(){
        // Adding the shadow
        InnerShadow shadow = new InnerShadow();
        controller.fxNetLabel.setEffect(shadow);
        controller.fxNetLabel.setAlignment(Pos.CENTER);
        controller.fxNetLabel.setPadding(new Insets(5, 8, 5, 8));
        controller.fxNetLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
        return controller.fxNetLabel;
    }

    int getPomodoroTime(){
        return pomodoroTime;
    }

    VBox getContent(){
        return controller.fxContent;
    }

    AnchorPane getRootUI() {
        return controller.fxRootUI;
    }

    Stage getPrimaryStage(){
        return primaryStage;
    }

    boolean isAppClosing() {
        return appClosingState;
    }

    Controller getController() {
        return controller;
    }

    // Animations

    /** Usage:
     * getFadeOutAnimation( Node.opacityProperty() ).play();
     */
    Animation getFadeOutAnimation(DoubleProperty property) {
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
    Animation getFadeInAnimation(DoubleProperty property ) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame( Duration.ZERO, new KeyValue( property, 0 ) ),
                new KeyFrame( Duration.seconds(0.4), new KeyValue( property, 1 ) )
        );
        return timeline;
    }

    void setCloseOnActions(Stage stage, Node fxButton, Object object) {

        fxButton.setOnMouseExited(event -> {
            getPrimaryStage().getScene().setOnMouseClicked(event1 -> removeOnActEvents(stage, fxButton, object));
            // Close on Esc pressed
            stage.getScene().setOnKeyPressed(event2 -> {
                if (event2.getCode() == KeyCode.ESCAPE) removeOnActEvents(stage, fxButton, object);
            });
        });
    }

    void removeOnActEvents(Stage stage, Node fxButton, Object object) {
        getPrimaryStage().getScene().setOnMouseClicked(null);
        stage.getScene().setOnKeyPressed(null);
        if (object.getClass().getName().equals("Settings")){
            Settings.getInstance().setSettingsShow(false);
        }else {
            Info.getInstance().setInfoShow(false);
        }
        fxButton.setOnMouseExited(null);
        getFadeOutAnimation(stage.opacityProperty()).play();
    }

    void removeButtonHandler(ImageView fxButton){
        fxButton.setOnMouseClicked(null);
    }
}

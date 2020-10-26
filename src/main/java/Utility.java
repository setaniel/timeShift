import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Utility {
    public static Node root;
    private static Stage primaryStage;
    private static VBox content;
    private static Label netLabel;
    private static boolean appClosingState = false;
    private static int pomodoroTime = 25;

    // Setters
    public static void setNetLabel(Label fxNetLabel){
        // Adding the shadow
        InnerShadow shadow = new InnerShadow();
        netLabel = fxNetLabel;
        netLabel.setEffect(shadow);
        netLabel.setAlignment(Pos.CENTER);
        netLabel.setPadding(new Insets(5, 8, 5, 8));
        netLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
    }
    public static void setContent(VBox fxContent){
        content = fxContent;
    }
    public static void setPrimaryStage(Stage primeStage){
        primaryStage = primeStage;
    }
    public static void setDropShadow(Node node, Color color){
        Controller.setDropShadow(node, color);
    }
    public static void setInnerShadow(Node node, Color color){
        Controller.setInnerShadow(node, color);
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

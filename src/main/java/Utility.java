import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Utility {
    public static Node root;
    private static Stage primaryStage;
    private static VBox content;
    private static boolean appClosingState = false;

    public static void setContent(VBox fxContent){
        content = fxContent;
    }
    public static void setPrimaryStage(Stage primeStage){
        primaryStage = primeStage;
    }
    public static void setDropShadow(Node node, Color color){
        Controller.setDropShadow(node, color);
    }
    public static void setIsAppClosing(boolean state){
        appClosingState = state;
    }
    public static void setRoot(Node node){
        root = node;
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

    public static void closeOnClickOutThis(Stage modalStage) {
        EventHandler<MouseEvent> primeStageClicked=new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // on click actions here
                modalStage.close();
                Utility.root.removeEventFilter(MouseEvent.MOUSE_CLICKED, this); // at the bottom
            }
        };
        Utility.root.addEventFilter(MouseEvent.MOUSE_CLICKED, primeStageClicked); // add the eventhandler to the node
    }
}

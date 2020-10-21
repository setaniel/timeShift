import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.Timer;
import java.util.TimerTask;

public class Pomodoro {
    private double xOffset = 0;
    private double yOffset = 0;
    private static Button button;
    private static AnchorPane anchorPane;
    private final StackPane stackPane = new StackPane();
    private Stage stage;
    private Stage mainStage;
    private Timer timer;
    private Image image;
    Scene scene;
    private static int minutes = 3;
    private static int time = 3;

    public Pomodoro(){
        setImage("Tomato");
    }

    public void startPomodoro(Stage mainStage){

        this.mainStage = mainStage;
        button = createAnchorButton(25, 43, "Start");
        setImage("Tomato");

        anchorPane.getChildren().add(button);
        // on click, set timer on button
        setDefaultButton();

        setStage();
    }

    private void setStage(){
        // set scene
        stage = new Stage(StageStyle.UNDECORATED);
        stage.initModality(Modality.NONE);
        stage.initOwner(mainStage);
        anchorPane.setMaxWidth(image.getWidth());
        anchorPane.setMaxHeight(image.getHeight());
        scene = new Scene(stackPane, image.getWidth(), image.getHeight(), Color.TRANSPARENT);
        stage.setScene(scene);
        // set transparent background
        scene.setFill(null);
        stackPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        stage.initStyle(StageStyle.TRANSPARENT);
        // set position this modal on parent frame
        stage.setX(mainStage.getX() + 350);
        stage.setY(mainStage.getY() + 50);
        stage.show();

        stackPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        stackPane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    private void setImage(String imageName){
        anchorPane = new AnchorPane();
        AnchorPane anchorClosePane = new AnchorPane();
        image = new Image(Pomodoro.class.getResourceAsStream(String.format("images/%s.png", imageName)));
        anchorPane.setPrefSize(image.getWidth(), image.getHeight());
        anchorClosePane.setPrefSize(image.getWidth(), image.getHeight());
        ImageView tomato = new ImageView(image);

        Image closeImage = new Image(Pomodoro.class.getResourceAsStream("images/close.png"));
        ImageView closeImageView = new ImageView(closeImage);
        Controller.setDropShadow(closeImageView, Color.BLACK);
        anchorPane.getChildren().add(closeImageView);
        AnchorPane.setRightAnchor(closeImageView, 0.0);
        AnchorPane.setTopAnchor(closeImageView,56.0);
        closeImageView.setOnMouseClicked(event -> {
            stage.close();
            if (timer != null) timer.cancel();
            View.isPomodoroStarted = false;
        });

        stackPane.getChildren().removeAll(stackPane.getChildren());
        stackPane.getChildren().addAll(tomato, anchorPane);
    }
    private void setDefaultButton(){
        anchorPane.getChildren().remove(button);
        button = createAnchorButton(25, 43, "Start");
        anchorPane.getChildren().add(button);
        button.setOnAction(event -> startTimerButton());
    }

    private void startTimerButton(){
        anchorPane.getChildren().remove(button);
        button = createAnchorButton(37.0, 26, String.valueOf(minutes));
        anchorPane.getChildren().add(button);
        // stop timer
//        button.setOnAction(event -> setDefaultButton());

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    button.setText(String.valueOf(minutes--));
                    if (minutes == -1){
                        timer.cancel();
                        minutes = time;
                        setImage("CongraTomato");
                        setDefaultButton();
                        button.setText("Clear");
                    }
                    button.setOnAction(event -> {
                        timer.cancel();
                        minutes = time;
                        setImage("Tomato");
                        setDefaultButton();
                    });
                });
            }
        }, /*(1+1) * 1000*/0, 1 * 1000);
    }

    private Button createAnchorButton(double leftAnchor, int width, String text){
        button = new Button(text);
        AnchorPane.setBottomAnchor(button, 15.0);
        AnchorPane.setLeftAnchor(button, leftAnchor);
        button.setFont(Font.font("Lucida console", FontWeight.EXTRA_BOLD,13));
        button.setStyle(String.format(
                "-fx-background-radius: 5em; " +
                        "-fx-min-width: %dpx; " +
                        "-fx-min-height: 17px; " +
                        "-fx-max-width: %dpx; " +
                        "-fx-max-height: 17px; " +
                        "-fx-background-color: -fx-body-color;" +
                        "-fx-background-insets: 0px; " +
                        "-fx-padding: 0px;" +
                        "-fx-background-color: #22CC00; " //+
                        , width, width
        ));
        Controller.setDropShadow(button, Color.BLACK);
        return button;
    }
}

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
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
    static boolean isPomodoroStarted = false;
    private double xOffset = 0;
    private double yOffset = 0;
    private static Button button;
    private static AnchorPane anchorPane;
    private final StackPane stackPane = new StackPane();
    private static Stage stage;
    private Timer timer;
    private Image image;
    Scene scene;
    private static int pomTimer;

    public static void stageClose(){
        if (stage != null && stage.isShowing()) {
            stage.close();
        }
    }

    public Pomodoro(){
        setImage("Tomato");
    }

    public void startPomodoro(){
        createAnchorButton(25, 43, "Start");
        DropShadow dropShadow = new DropShadow();
        button.setEffect(dropShadow);
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
        stage.initOwner(Utility.getPrimaryStage());
        anchorPane.setMaxWidth(image.getWidth());
        anchorPane.setMaxHeight(image.getHeight());
        scene = new Scene(stackPane, image.getWidth(), image.getHeight(), Color.TRANSPARENT);
        stage.setScene(scene);
        // set transparent background
        scene.setFill(null);
        stackPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        stage.initStyle(StageStyle.TRANSPARENT);
        // set position this modal on parent frame
        stage.setX(Utility.getPrimaryStage().getX() + 350);
        stage.setY(Utility.getPrimaryStage().getY() + 50);
        stage.show();

        stage.setOpacity(0);
        Utility.getFadeOutAnimation(stage.opacityProperty()).play();

        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        scene.setOnMouseDragged(event -> {
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
        ImageView closeImageView = createCloseButton();
        anchorPane.getChildren().add(closeImageView);
        stackPane.getChildren().removeAll(stackPane.getChildren());
        stackPane.getChildren().addAll(tomato, anchorPane);
    }
    private ImageView createCloseButton(){
        Image closeImage = new Image(Pomodoro.class.getResourceAsStream("images/close.png"));
        ImageView closeImageView = new ImageView(closeImage);
        AnchorPane.setRightAnchor(closeImageView, 0.0);
        AnchorPane.setTopAnchor(closeImageView,5.0);
        closeImageView.setOnMouseClicked(event -> {
            Utility.getFadeInAnimation(stage.opacityProperty()).play();
            if (timer != null) timer.cancel();
            isPomodoroStarted = false;
        });
        Utility.setInnerShadow(closeImageView, Color.RED);
        return closeImageView;
    }
    private void setDefaultButton(){
        anchorPane.getChildren().remove(button);
        createAnchorButton(25, 43, "Start");
        anchorPane.getChildren().add(button);
        button.setOnAction(event -> startTimerButton());
    }

    private void startTimerButton(){
        pomTimer = Utility.getPomodoroTime();
        anchorPane.getChildren().remove(button);
        createAnchorButton(37.0, 26, String.valueOf(pomTimer));
        anchorPane.getChildren().add(button);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    button.setText(String.valueOf(pomTimer--));
                    if (pomTimer == -1){
                        timer.cancel();
                        pomTimer = Utility.getPomodoroTime();
                        setImage("sadTomato");
                        setDefaultButton();
                        button.setText("Clear");
                    }
                    button.setOnAction(event -> {
                        timer.cancel();
                        pomTimer = Utility.getPomodoroTime();
                        setImage("Tomato");
                        setDefaultButton();
                    });
                });
            }
        },0, 60 * 1000);
    }

    private void createAnchorButton(double leftAnchor, int width, String text){
        button = new Button(text);
        AnchorPane.setBottomAnchor(button, 15.0);
        AnchorPane.setLeftAnchor(button, leftAnchor);
        button.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD,12));
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
        Utility.setSwitchInnerShadows(button, Color.BLACK, Color.RED);
    }
}

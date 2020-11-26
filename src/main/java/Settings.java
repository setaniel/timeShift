import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.lang3.StringUtils;

import java.util.Timer;
import java.util.TimerTask;

public class Settings {
    private static double xOffset = 0;
    private static double yOffset = 0;
    static boolean isSettingsShow = false;
    private static Stage stage;

    public static void stageClose(){
        if (stage != null && stage.isShowing()) {
            stage.close();
        }
    }

    public static void showSettings(ImageView fxButton, StackPane rootStackPane){
//        Button button = new Button();
//        button.setOnMouseClicked(event -> rootStackPane);   ???????????????????????
        ImageView imageView = new ImageView(new Image(NoteEditor.class.getResourceAsStream("images/done.png")));
        Button okButton = new Button("", imageView);
        okButton.setStyle("-fx-background-color : transparent;");
        //____
        VBox layout = new VBox();
        TextField text = new TextField("0-999");
        text.setAlignment(Pos.TOP_CENTER);
        text.setMaxWidth(80.0);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(layout, okButton);

        AnchorPane.setBottomAnchor(okButton, 12.0);
        AnchorPane.setRightAnchor(okButton, 13.0);

        AnchorPane.setBottomAnchor(layout, 3.0);
        AnchorPane.setLeftAnchor(layout, 3.0);
        AnchorPane.setRightAnchor(layout, 3.0);
        AnchorPane.setTopAnchor(layout, 3.0);

        layout.setBackground(new Background(new BackgroundFill(Paint.valueOf("#e8e4db"), new CornerRadii(16), Insets.EMPTY)));
//        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10.0);
        layout.setPadding(new Insets(10, 10, 10, 10));


        text.setFont(Font.font("Courier New", FontWeight.BOLD, 15));
        Utility.setDropShadow(text, Color.BLACK);
        Utility.setUIShadows(okButton);
//        text.setPrefSize(15, 10);
        text.setBackground(new Background(new BackgroundFill(Paint.valueOf("white"), new CornerRadii(16), Insets.EMPTY)));
        Label label = new Label("Pomodoro timer");
        label.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
        label.setPrefHeight(20.0);
        label.setBackground(new Background(new BackgroundFill(Paint.valueOf("transparent"), new CornerRadii(16), Insets.EMPTY)));
        layout.getChildren().addAll(label, text);

        stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene(anchorPane, 165, 90, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(Utility.getPrimaryStage());
        // set position this modal on parent frame
        stage.setX(Utility.getPrimaryStage().getX() + 160);
        stage.setY(Utility.getPrimaryStage().getY() + 320);
        stage.show();
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(22);
        anchorPane.setStyle("-fx-background-radius: 22;" +
                "-fx-background-insets: 10; " +
                "-fx-effect: dropShadow(three-pass-box, black, 10, 0, 0, 0);");

        //-----------------------------------
        okButton.setOnAction(event -> {
            DropShadow shadow = new DropShadow();
            shadow.setColor(Color.RED);
            if (StringUtils.isNumeric(text.getCharacters())){
                int i;
                if ((i = Integer.parseInt(text.getText())) <= 999){
                    Utility.setPomodoroTime(i);
                    stage.close();
                }else {
                    label.setText(" Incorrect value ");
                    text.setEffect(shadow);
                    label.setBackground(new Background(new BackgroundFill(Paint.valueOf("red"),
                            new CornerRadii(16), Insets.EMPTY)));
                }
            }else {
                label.setText(" Incorrect value ");
                text.setEffect(shadow);
                label.setBackground(new Background(new BackgroundFill(Paint.valueOf("red"),
                        new CornerRadii(16), Insets.EMPTY)));
            }

        });

        // Drag window
        layout.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        layout.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        closeOnActions(fxButton);
        stage.setOpacity(0);
        Utility.getFadeOutAnimation(stage.opacityProperty()).play();
        layout.getChildren().get(1).requestFocus();
    }

    public static void closeOnActions(Node fxButton) {

        fxButton.setOnMouseExited(event -> {
            Utility.getPrimaryStage().getScene().setOnMouseClicked(event1 -> removeOnActEvents(fxButton));
            // Close on Esc pressed
            stage.getScene().setOnKeyPressed(event2 -> {
                if (event2.getCode() == KeyCode.ESCAPE) removeOnActEvents(fxButton);
            });
        });
    }



    private static void removeOnActEvents(Node fxButton) {
        Utility.getPrimaryStage().getScene().setOnMouseClicked(null);
        stage.getScene().setOnKeyPressed(null);
        fxButton.setOnMouseExited(null);
        isSettingsShow = false;
        Utility.getFadeInAnimation(stage.opacityProperty()).play();
    }
}

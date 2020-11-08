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

public class Settings {
    private static double xOffset = 0;
    private static double yOffset = 0;
    static boolean isSettingsShow = false;
    public static void showSettings(ImageView fxButton){
        Image image = new Image(NoteEditor.class.getResourceAsStream("images/done.png"));
        ImageView imageView = new ImageView(image);
        Button okButton = new Button("", imageView);
        okButton.setStyle("-fx-background-color : transparent;");
        //____
        VBox layout = new VBox();
        TextField text = new TextField("0-999");
        text.setAlignment(Pos.TOP_CENTER);
        text.setMaxWidth(80.0);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(layout);

        AnchorPane.setBottomAnchor(layout, 10.0);
        AnchorPane.setLeftAnchor(layout, 10.0);
        AnchorPane.setRightAnchor(layout, 10.0);
        AnchorPane.setTopAnchor(layout, 10.0);

        layout.setBackground(new Background(new BackgroundFill(Paint.valueOf("#e8e4db"), new CornerRadii(16), Insets.EMPTY)));
        layout.setAlignment(Pos.CENTER);
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
        layout.getChildren().addAll(label, text, okButton);

        Stage stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene(anchorPane, 170, 110, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(Utility.getPrimaryStage());
        // set position this modal on parent frame
        stage.setX(Utility.getPrimaryStage().getX() + 175);
        stage.setY(Utility.getPrimaryStage().getY() + 320);
        stage.show();
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(22);
        anchorPane.setStyle("-fx-background-radius: 16;" +
                "-fx-background-color: rgb(45, 45, 50), rgb(60, 60, 65);");

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
        closeOnActions(stage, fxButton);
    }

    public static void closeOnActions(Stage modalStage, Node fxButton) {

        fxButton.setOnMouseExited(event -> {
            Utility.getPrimaryStage().getScene().setOnMouseClicked(event1 -> {
                modalStage.close();
                removeOnActEvents(fxButton, modalStage);

            });
            // Close on Esc pressed
            modalStage.getScene().setOnKeyPressed(event2 -> {
                if (event2.getCode() == KeyCode.ESCAPE) modalStage.close();
                removeOnActEvents(fxButton, modalStage);
            });
        });
    }



    private static void removeOnActEvents(Node fxButton, Stage modalStage) {
        Utility.getPrimaryStage().getScene().setOnMouseClicked(null);
        modalStage.getScene().setOnKeyPressed(null);
        fxButton.setOnMouseExited(null);
        isSettingsShow = false;
    }
}

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
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

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class Info {
    private static double xOffset = 0;
    private static double yOffset = 0;
    static boolean isInfoShow = false;
    public static void showInfo(ImageView fxButton){
        VBox layout = new VBox();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(layout);
        AnchorPane.setBottomAnchor(layout, 10.0);
        AnchorPane.setLeftAnchor(layout, 10.0);
        AnchorPane.setRightAnchor(layout, 10.0);
        AnchorPane.setTopAnchor(layout, 10.0);
        layout.setBackground(new Background(new BackgroundFill(Paint.valueOf("#e8e4db"), new CornerRadii(16), Insets.EMPTY)));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10));

        Label version = new Label("Ver.1.0 release");
        setLabelFont(version);
        Label nickName = new Label("@Setaniel");
        setLabelFont(nickName);
        Label author = new Label("Kirill Orlov");
        setLabelFont(author);
        Label mail = new Label("i4data@ya.ru");
        setLabelFont(mail);
        Hyperlink site = new Hyperlink("setaniel.github.io");
        site.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
        site.setOnMouseClicked(event -> {
            try {
                Desktop.getDesktop().browse(new URI("https://setaniel.github.io"));
                site.setVisited(true);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        layout.getChildren().addAll(version, nickName, author, mail, site);
        Stage stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene(anchorPane, 200, 120, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(Utility.getPrimaryStage());
        // set position this modal on parent frame
        stage.setX(Utility.getPrimaryStage().getX() + 145);
        stage.setY(Utility.getPrimaryStage().getY() + 330);
        stage.show();
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(22);
        anchorPane.setStyle("-fx-background-radius: 16;" +
                "-fx-background-color: rgb(45, 45, 50), rgb(60, 60, 65);");

        //-----------------------------------
//        Utility.closeOnActions(stage);
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
    private static void setLabelFont(Label label){
        label.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
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
        isInfoShow = false;
    }
}

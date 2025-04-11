import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 @author Setaniel
 @version 1.0
 */

class Info {
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean isInfoShow = false;
    private Stage stage;
    private VBox layout;
    private static Info instance;

    private Info(){
    }

    public static Info getInstance() {
        if (instance == null) instance = new Info();
        return instance;
    }

    boolean isInfoShow() {
        return isInfoShow;
    }

    void setInfoShow(boolean infoShow) {
        isInfoShow = infoShow;
    }

    void closeStage(){
        if ( stage != null && stage.isShowing() ) stage.close();
    }

    void showInfo(ImageView fxButton) {
        setInfoShow(true);
        layout = new VBox();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(layout);
        AnchorPane.setBottomAnchor(layout, 3.0);
        AnchorPane.setLeftAnchor(layout, 3.0);
        AnchorPane.setRightAnchor(layout, 3.0);
        AnchorPane.setTopAnchor(layout, 3.0);
        setBackGroundColor();
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10));

        Label version = new Label("Ver.1.1.5");
        setLabelFont(version);
        Label nickName = new Label("Setaniel");
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
        stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene(anchorPane, 200, 120, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(Utility.getInstance().getPrimaryStage());
        // set position this modal on parent frame
        stage.setX(Utility.getInstance().getPrimaryStage().getX() + 125);
        stage.setY(Utility.getInstance().getPrimaryStage().getY() + 330);
        stage.show();
        anchorPane.setStyle("-fx-background-radius: 22;" +
                "-fx-background-insets: 10; " +
                "-fx-effect: dropShadow(three-pass-box, black, 10, 0, 0, 0);");

        //-----------------------------------
        // Drag window
        layout.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        layout.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        Utility.getInstance().setCloseOnActions(stage, fxButton, this);

        stage.setOpacity(0);
        Utility.getInstance().getFadeInAnimation(stage.opacityProperty()).play();
        layout.getChildren().get(4).requestFocus();
    }
    void setBackGroundColor(){
        layout.setBackground(new Background(new BackgroundFill(
                ThemeSwitcher.getInstance().getBackgroundColor(), new CornerRadii(16), Insets.EMPTY)));
    }
    private void setLabelFont(Label label) {
        label.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
    }

}

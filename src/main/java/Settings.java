import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.lang3.StringUtils;

class Settings {
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean isSettingsShow = false;
    private Stage stage;
    private VBox layout;
    private final Image imgPowerOff = new Image(Settings.class.getResourceAsStream("images/powerOff.png"));
    private final Image imgPowerOn = new Image(Settings.class.getResourceAsStream("images/powerOn.png"));
    private static Settings instance;

    static Settings getInstance(){
        if (instance == null) instance = new Settings();
        return instance;
    }

    private Settings(){
    }

    void closeStage(){
        if (stage != null && stage.isShowing()) {
            stage.close();
        }
    }

    boolean isSettingsShow() {
        return isSettingsShow;
    }

    void setSettingsShow(boolean settingsShow) {
        isSettingsShow = settingsShow;
    }

    void showSettings(ImageView fxButton){
        setSettingsShow(true);
        layout = new VBox();
        ToggleButton tgb = new ToggleButton();
        tgb.setStyle("-fx-background-radius: 8");
        Utility.setSwitchInnerShadows(tgb, Color.BLACK, Color.DARKGREY);
        tgb.setSelected(ThemeSwitcher.getInstance().isDark());
        tgb.setOnAction(event -> tgbOnAction(tgb));
        tgb.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
        if (ThemeSwitcher.getInstance().isDark()){
            tgb.setText("Dark theme");
            tgb.setGraphic(new ImageView(imgPowerOn));
        } else {
            tgb.setText("Light theme");
            tgb.setGraphic(new ImageView(imgPowerOff));
        }


        ImageView okButton = new ImageView(new Image(NoteEditor.class.getResourceAsStream("images/done.png")));
        okButton.setStyle("-fx-background-color : transparent;");
        //____

        TextField text = new TextField("0-999");
        text.setAlignment(Pos.TOP_CENTER);
        text.setMaxWidth(80.0);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(layout, okButton);
        setBackGroundColor();
        AnchorPane.setBottomAnchor(okButton, 44.0);
        AnchorPane.setRightAnchor(okButton, 16.0);
        AnchorPane.setRightAnchor(tgb, 0.0);

        AnchorPane.setBottomAnchor(layout, 3.0);
        AnchorPane.setLeftAnchor(layout, 3.0);
        AnchorPane.setRightAnchor(layout, 3.0);
        AnchorPane.setTopAnchor(layout, 3.0);

        layout.setSpacing(10.0);
        layout.setPadding(new Insets(10, 10, 10, 10));


        text.setFont(Font.font("Courier New", FontWeight.BOLD, 15));
        Utility.setDropShadow(text, Color.BLACK);
        Utility.setUIShadows(okButton);
        text.setBackground(new Background(new BackgroundFill(Paint.valueOf("white"), new CornerRadii(16), Insets.EMPTY)));
        Label label = new Label("Pomodoro timer");
        label.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
        label.setPrefHeight(20.0);
        label.setBackground(new Background(new BackgroundFill(Paint.valueOf("transparent"), new CornerRadii(16), Insets.EMPTY)));
        layout.getChildren().addAll(label, text, tgb);

        stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene(anchorPane, 155, 110, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(Utility.getPrimaryStage());
        // set position this modal on parent frame
        stage.setX(Utility.getPrimaryStage().getX() + 170);
        stage.setY(Utility.getPrimaryStage().getY() + 300);
        stage.show();
        com.sun.glass.ui.Window.getWindows().get(0).setUndecoratedMoveRectangle(22);
        anchorPane.setStyle("-fx-background-radius: 22;" +
                "-fx-background-insets: 10; " +
                "-fx-effect: dropShadow(three-pass-box, black, 10, 0, 0, 0);");

        //-----------------------------------
        okButton.setOnMouseClicked(event -> {
            if (StringUtils.isNumeric(text.getCharacters())){
                int i;
                if ((i = Integer.parseInt(text.getText())) <= 999){
                    Utility.setPomodoroTime(i);
                    stage.close();
                }else {
                    label.setText(" Incorrect value ");
                    Utility.setStaticDropShadow(text, Color.RED);
                    label.setBackground(new Background(new BackgroundFill(Paint.valueOf("red"),
                            new CornerRadii(16), Insets.EMPTY)));
                }
            }else {
                label.setText(" Incorrect value ");
                Utility.setStaticDropShadow(text, Color.RED);
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
        Utility.setCloseOnActions(stage, fxButton, this);
        stage.setOpacity(0);
        Utility.getFadeInAnimation(stage.opacityProperty()).play();
        layout.getChildren().get(1).requestFocus();
    }

    private void tgbOnAction(ToggleButton tgb) {
        if (tgb.isSelected()) {
            ThemeSwitcher.getInstance().setCurrentTheme(true);
            tgb.setText("Dark theme");
            tgb.setGraphic(new ImageView(imgPowerOn));
        }
        else {
            ThemeSwitcher.getInstance().setCurrentTheme(false);
            tgb.setText("Light theme");
            tgb.setGraphic(new ImageView(imgPowerOff));
        }
        setBackGroundColor();
    }

    void setBackGroundColor(){
        layout.setBackground(new Background(new BackgroundFill(
                ThemeSwitcher.getInstance().getBackgroundColor(), new CornerRadii(16), Insets.EMPTY)));
    }
}

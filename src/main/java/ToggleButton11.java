import javafx.application.Application;
import javafx.beans.binding.ObjectBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


class ToggleButton11 extends Application {

    private ToggleButton tgb;
    private TextField txf;

    private ToggleButton tgbLBind;
    private TextField txfLBind;

    private Image imgPowerOff;
    private Image imgPowerOn;

    private String on = "ON";
    private String off = "OFF";

    @Override
    public void start(Stage stage) throws Exception {

//		Parent root = FXMLLoader.load(this.getClass().getResource("ToggleButton.fxml"));
        AnchorPane root = new AnchorPane();
        root.prefHeight(400.0);
        root.prefWidth(600.0);
        Scene scene = new Scene(root);

        VBox vBox = new VBox();
        vBox.setLayoutX(20.0);
        vBox.setLayoutY(20.0);
        vBox.prefHeight(-1.0);
        vBox.setSpacing(5.0);
        root.getChildren().add(vBox);

        Label label = new Label("* Using Bind (Low-level API)");
        HBox hBox = new HBox();
        hBox.setSpacing(10.0);
        vBox.getChildren().addAll(label, hBox);

        tgbLBind = new ToggleButton();
        tgbLBind.setOnAction(event -> tgbOnAction());
        tgbLBind.prefWidth(130.0);
        tgbLBind.setText("Toggle Button");
        txfLBind = new TextField();
        tgbLBind.setAlignment(Pos.CENTER);
        txfLBind.setEditable(false);
        txfLBind.setFocusTraversable(false);
        txfLBind.setPrefWidth(100.0);
        txfLBind.setStyle("-fx-background-color: beige");


        stage.setTitle("JavaFX 2 toggle button");

        stage.setScene(scene);
        stage.show();


        imgPowerOff = new Image(this.getClass().getResourceAsStream("javaFX2toggleButton/res/powerOff.png"));
        imgPowerOn = new Image(this.getClass().getResourceAsStream("javaFX2toggleButton/res/powerOn.png"));

        // Using Bind (Low-level API)
		/*assert tgbLBind != null : "fx:id=\"tgbLBind\" was not injected: check your FXML file 'ToggleButton.fxml'.";
		assert txfLBind != null : "fx:id=\"txfLBind\" was not injected: check your FXML file 'ToggleButton.fxml'.";*/
        this.tgbLBind.setGraphic(new ImageView(imgPowerOff));
        this.tgbLBind.setSelected(false);
        this.txfLBind.textProperty().bind(this.observer(tgbLBind));

    }



    // Using Event Handler

    void tgbOnAction() {
        if (this.tgb.isSelected()) {
            this.txf.setText(on);
            this.tgb.setGraphic(new ImageView(imgPowerOn));
        }
        else {
            this.txf.setText(off);
            this.tgb.setGraphic(new ImageView(imgPowerOff));
        }
    }

    // Using Bind (Low-level API)
    private ObjectBinding<String> observer(ToggleButton p) {
        final ToggleButton tgb = p;
        ObjectBinding<String> sBinding = new ObjectBinding<String>() {
            {
                super.bind(tgb.selectedProperty());
            }

            @Override
            protected String computeValue() {
                String s;
                if (tgb.isSelected()) {
                    s = on;
                    tgbLBind.setGraphic(new ImageView(imgPowerOn));

                }
                else {
                    s = off;
                    tgbLBind.setGraphic(new ImageView(imgPowerOff));
                }
                return s;
            }
        };
        return sBinding;
    }

    public static void main(String[] args) {
        launch(args);
    }

}

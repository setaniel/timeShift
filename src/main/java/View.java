import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
// function to get and output the result

class View {
    private static Stage mainStage;
    // stage of modal window (set note text)
    private  final EditNote editNote = new EditNote();
//    private Stage mainStage;
    private Model viewModel;
    // FXML linked nodes
    @FXML
    private VBox content;
/*    {
        content.getChildren().addListener((ListChangeListener<Node>) c -> {
            for (Node anchorPane : content.getChildren()){
                content.getChildren().indexOf(anchorPane);
                //                content.getChildren().indexOf(anchorPane);
//                getting index of this pane

            }
        });
    }*/
    //--------------------------
    {
        EditNote.doneButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> manageNote(viewModel));
    }

    protected void addNote(Model model){
        viewModel = model;
        viewModel.setNoteText(editNote.showWindow(new TextArea(viewModel.getNoteText()), mainStage));

    }
    // Add note on main window(note list)
    protected void manageNote(Model viewModel){
        System.out.println(viewModel.getNoteText());
        if (viewModel.getTitleLabelText() == null){
            Model model = setModelData(viewModel.getNoteText());
            AnchorPane anchorPane = createAnchorPane(model);
            // add a note in notes list in 0 index
            content.getChildren().add(0, anchorPane);
            //onClick on note for edit
            anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
//                setModelData(editNote.showWindow(new TextArea(model.getNoteText()), mainStage).getText());
                addNote(model);
            });
        }
    }
    private void editAnchorPane(AnchorPane anchorPane, Model model){

        anchorPane = createAnchorPane(model);
    }
    private AnchorPane createAnchorPane(Model model){
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: transparent; -fx-border-color: brown;");
        Button trashButton = createTrashButton();
        trashButton.setOnAction(evt -> content.getChildren().remove(anchorPane));
        //-----------------
        // get note text and null local note variable
        viewModel = new Model();
        // set note title and text, getting out model
        Label titleLabel = new Label(model.getTitleLabelText());
        titleLabel.setStyle("-fx-font-size: 15; -fx-font-weight: bold");
        Label previewLabel = new Label(model.getPreviewLabelText());
        // set positions of text and buttons in note
        AnchorPane.setBottomAnchor(previewLabel, 0.0);
        AnchorPane.setLeftAnchor(previewLabel, 5.0);
        AnchorPane.setLeftAnchor(titleLabel, 5.0);
        AnchorPane.setTopAnchor(titleLabel, 0.0);
        AnchorPane.setRightAnchor(trashButton, 5.0);
        AnchorPane.setTopAnchor(trashButton, 5.0);
        AnchorPane.setBottomAnchor(trashButton, 5.0);
        anchorPane.getChildren().addAll(titleLabel, trashButton, previewLabel);
        return anchorPane;
    }
    private Model setModelData(String data){
        Model model = new Model();
        List<String> list = Arrays.asList(data.split("\n"));
        model.setTitleLabelText(list.get(0));
        if(list.size()>1)model.setPreviewLabelText(list.get(1));
        model.setNoteText(data);
        return model;
    }
    private Button createTrashButton(){
        Image image = new Image(View.class.getResourceAsStream("images/trash.png"), 30, 30, true, false);
        ImageView imageView = new ImageView(image);
        Button trashButton = new Button("", imageView);
        trashButton.setMaxSize(30, 30);
        trashButton.setStyle("-fx-background-color : transparent;");
        return trashButton;
    }
    public static void setMainStage(Stage stage){
        View.mainStage = stage;
    }
    @FXML
    private void closeApp(){
        mainStage.close();
    }
}

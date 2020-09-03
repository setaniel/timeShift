import javafx.scene.control.Label;

import java.awt.*;

public class Model {
    // Computing and internal data storage
    private TextArea note;
    private Label titleLabel;
    private Label previewLabel;

    public void setNote(TextArea noteText){
        this.note = noteText;
    }
    public void setTitleLabel(Label titleLabel){
        this.titleLabel = titleLabel;
        titleLabel.setStyle("-fx-font-size: 15; -fx-font-weight: bold");
    }
    public void setPreviewLabel(Label previewLabel){
        this.previewLabel = previewLabel;
    }

    public TextArea getNote(){
        return note;
    }
    public Label getTitleLabel(){
        return titleLabel;
    }
    public Label getPreviewLabel(){
        return previewLabel;
    }
}

import javafx.scene.control.Label;

import java.awt.*;

public class Model {
    // Computing and internal data storage
    private String noteText;
    private String titleLabelText;
    private String previewLabelText;
    private int index;

    public void setNoteText(String text){
        this.noteText = text;
    }
    public void setPreviewLabelText(String previewText){
        this.previewLabelText = previewText;
    }
    public void setTitleLabelText(String titleText) {
        this.titleLabelText = titleText;
    }
    public void setIndex(int contentIndex){
        this.index = contentIndex;
    }

    public String getNoteText(){
        return noteText;
    }
    public String getTitleLabelText(){
        return titleLabelText;
    }
    public String getPreviewLabelText(){
        return previewLabelText;
    }
    public int getIndex(){
        return index;
    }


}

import javafx.scene.control.Label;

import java.awt.*;

public class Model {
    // Computing and internal data storage
    private String noteText;
    private String titleLabelText;
    private String previewLabelText;
    private int index;

    public void setNoteText(String noteText){
        this.noteText = noteText;
    }
    public void setPreviewLabelText(String previewLabelText){
        this.previewLabelText = previewLabelText;
    }
    public void setTitleLabelText(String titleLabelText) {
        this.titleLabelText = titleLabelText;
    }
    public void setIndex(int index){
        this.index = index;
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

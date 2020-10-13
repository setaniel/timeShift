import javafx.scene.control.Label;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;
/** Internal data storage for notes.
 * Uses for serialize and deserialize,
 * collecting data of all notes.
 * Each note uses its own instance of the model*/
public class Model implements Serializable {
    private static final long serialVersionUID = 101;
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

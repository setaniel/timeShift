import java.io.Serializable;
/**
 * Internal data storage for notes.
 * Uses for serialize and deserialize,
 * collecting data of all notes.
 * Each note uses its own instance of the model
 *  @author Setaniel
 *  @version 1.0
 * */
class Model implements Serializable {
    private static final long serialVersionUID = 101;

    private String noteText;
    private String titleLabelText;
    private String previewLabelText;
    private String noteDate;
    private int index;

    void setNoteText(String text){
        this.noteText = text;
    }

    void setPreviewLabelText(String previewText){
        this.previewLabelText = previewText;
    }

    void setTitleLabelText(String titleText) {
        this.titleLabelText = titleText;
    }

    void setNoteDate(String date){
        noteDate = date;
    }

    void setIndex(int contentIndex){
        this.index = contentIndex;
    }


    String getNoteText(){
        return noteText;
    }

    String getTitleLabelText(){
        return titleLabelText;
    }

    String getPreviewLabelText(){
        return previewLabelText;
    }

    String getNoteDate(){return noteDate;}

    int getIndex(){
        return index;
    }
}

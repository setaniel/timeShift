import javafx.fxml.FXML;

/**
 * Create a view frame.
 * Managing notes
 * */
class View {

    static boolean isPomodoroStarted = false;
    static boolean isNoteEditorShow = false;

    public static void addNote(){
        if (!isNoteEditorShow) Utility.noteEditor.editNote(new Note());
    }
    protected void startPomodoro(){
        if (isPomodoroStarted) return;
        new Pomodoro().startPomodoro();
        isPomodoroStarted = true;
    }

    protected static void manageNotes(Note note){
        if (note.getText() != null) {
            Utility.getContent().getChildren().remove(note);
            Utility.getContent().getChildren().add(0, note);
            Serializer.deleteSerializeFiles();
            Serializer.serializeNotes();
        }
    }


    // Closing app, run serialization
    @FXML private void closeApp(){
        Utility.setIsAppClosing(true);
        Serializer.serializeNotes();
    }
}

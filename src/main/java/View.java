import javafx.fxml.FXML;

/**
 * Create a view frame.
 * Managing notes
 * */
class View {
    private  final NoteEditor noteEditor = new NoteEditor();
    static boolean isPomodoroStarted = false;

    protected void addNote(){
        noteEditor.noteEditWindow(new Note(Utility.getContent()));
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

    @FXML
    // Closing app, run serialization
    private void closeApp(){
        Utility.setIsAppClosing(true);
        Serializer.serializeNotes();
    }
}

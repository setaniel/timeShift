import javafx.scene.Node;

import java.io.*;

class Serializer {
    private static Serializer instance;
    private Serializer(){
    }
    static Serializer getInstance(){
        if (instance == null) instance = new Serializer();
        return instance;
    }
    /**
     * Serializing all notes, from files in user home directory
     * */
    void serializeNotes(){
        for (Node node : Utility.getInstance().getContent().getChildren()) {
            try {
                Note serialNote = (Note)node;
                serialNote.setIndex(Utility.getInstance().getContent().getChildren().indexOf(node));

                // save theme bound
                serialNote.getNoteModel().setCurrentTheme(ThemeSwitcher.getInstance().isDark());
                // _____________________

                new File(System.getProperty("user.home") + "/documents/timeShift/serialize").mkdirs();
                FileOutputStream fileOut = new FileOutputStream(String.format(System.getProperty("user.home") + "/documents/timeShift/serialize/%d.ser", serialNote.getIndex()));
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(serialNote.getNoteModel());
                out.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Utility.getInstance().isAppClosing()){
            Utility.getInstance().getPrimaryStage().close();
            System.exit(0);
        }

    }

    /**
     * Deserializing all notes, from files in user home directory
     * */
    void deserializeNotes(){
        Model model;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            File file = new File(String.format(System.getProperty("user.home") + "/documents/timeShift/serialize/%d.ser", i));
            if (file.exists()) {
                try {
                    FileInputStream fileIn = new FileInputStream(file);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    model = (Model) in.readObject();
                    // set theme for app
                    ThemeSwitcher.getInstance().setCurrentTheme(model.getCurrentTheme());
                    Note note = new Note(model);
                    Utility.getInstance().setDropShadow(note, ThemeSwitcher.getInstance().getNoteShadow());
                    Utility.getInstance().getContent().getChildren().add(note.getIndex(), note);
                    in.close();
                    fileIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException c){
                    break;
                }
            }
            else {
                break;
            }
        }
        deleteSerializeFiles();
        serializeNotes();
    }
    /**
     * After deserialize, delete all .ser files
     * */
    void deleteSerializeFiles(){
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            File file = new File(String.format(System.getProperty("user.home") + "/documents/timeShift/serialize/%d.ser", i));
            if (file.exists()) {
                file.delete();
            }else break;
        }
    }
}

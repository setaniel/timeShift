import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.io.*;

class Serializer {
    /**
     * Serializing all notes, from files in user home directory
     * */
    static void serializeNotes(){
        for (Node node : Utility.getContent().getChildren()) {
            try {
                Note serialNote = (Note)node;
                serialNote.setIndex(Utility.getContent().getChildren().indexOf(node));

                // save theme bound
                serialNote.getNoteModel().setCurrentTheme(ThemeSwitcher.isDark());
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
        if (Utility.isAppClosing()){
            Utility.getPrimaryStage().close();
            System.exit(0);
        }

    }

    /**
     * Deserializing all notes, from files in user home directory
     * */
    static void deserializeNotes(){
        Model model;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            File file = new File(String.format(System.getProperty("user.home") + "/documents/timeShift/serialize/%d.ser", i));
            if (file.exists()) {
                try {
                    FileInputStream fileIn = new FileInputStream(file);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    model = (Model) in.readObject();
                    // set theme for app
                    ThemeSwitcher.setCurrentTheme(model.getCurrentTheme());
                    Note note = new Note(model);
                    Utility.setDropShadow(note, ThemeSwitcher.getCurrentTheme().getNoteShadow());
                    Utility.getContent().getChildren().add(note.getIndex(), note);
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
    static void deleteSerializeFiles(){
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            File file = new File(String.format(System.getProperty("user.home") + "/documents/timeShift/serialize/%d.ser", i));
            if (file.exists()) {
                file.delete();
            }else break;
        }
    }
}

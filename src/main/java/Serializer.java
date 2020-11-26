import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.io.*;

public class Serializer {
    /**
     * Serializing all notes, from files in user home directory
     * */
    public static void serializeNotes(){

        for (Node node : Utility.getContent().getChildren()) {
            try {
                Note serialNote = (Note)node;
                serialNote.setIndex(Utility.getContent().getChildren().indexOf(node));

                // set theme for app
                if (serialNote.getIndex() == 0) serialNote.getNoteModel().setCurrentTheme(ThemeSwitcher.getCurrentTheme());
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
    public static void deserializeNotes(){
        Model model;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            File file = new File(String.format(System.getProperty("user.home") + "/documents/timeShift/serialize/%d.ser", i));
            if (file.exists()) {
                try {
                    FileInputStream fileIn = new FileInputStream(file);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    model = (Model) in.readObject();
                    Note note = new Note(model);
                    // set theme for app
                    if (ThemeSwitcher.getCurrentTheme() == null) ThemeSwitcher.setCurrentTheme(model.getCurrentTheme());
                    // _____________________
                    Utility.setDropShadow(note, Color.valueOf("#98ecf2"));
                    Utility.getContent().getChildren().add(note.getIndex(), note);
                    in.close();
                    fileIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException c){
                    break;
                }
            }
            else break;
        }
        deleteSerializeFiles();
        serializeNotes();
    }
    /**
     * After deserialize, delete all .ser files
     * */
    public static void deleteSerializeFiles(){
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            File file = new File(String.format(System.getProperty("user.home") + "/documents/timeShift/serialize/%d.ser", i));
            if (file.exists()) {
                file.delete();
            }else break;
        }
    }
}

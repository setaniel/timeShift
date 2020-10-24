import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.io.*;

public class Serializer {
    public static void serializeNotes(){
        for (Node node : Instances.getContent().getChildren()) {
            try {
                Note serialNote = (Note)node;
                serialNote.setIndex(Instances.getContent().getChildren().indexOf(node));
                new File(System.getProperty("user.home") + "/documents/timeShift/serialize").mkdirs();
                String path = String.format(System.getProperty("user.home") + "/documents/timeShift/serialize/%d.ser", serialNote.getIndex());
                FileOutputStream fileOut = new FileOutputStream(path);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(serialNote.getNoteModel());
                out.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Instances.isAppClosing()){
            Instances.getPrimaryStage().close();
            System.exit(0);
        }
    }

    /**
     * Deserializing notes, from files in user home directory
     * */
    public static void deserializeNotes(){
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String path = String.format(System.getProperty("user.home") + "/documents/timeShift/serialize/%d.ser", i);
            File file = new File(path);
            if (file.exists()) {
                try {
                    FileInputStream fileIn = new FileInputStream(file);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    Note note = new Note(Instances.getContent(), (Model) in.readObject());
                    Controller.setDropShadow(note, Color.BLACK);
                    Instances.getContent().getChildren().add(note.getIndex(), note);
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
    }
    /**
     * After deserialize, delete all .ser files
     * */
    public static void deleteSerializeFiles(){
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String path = String.format(System.getProperty("user.home") + "/documents/timeShift/serialize/%d.ser", i);
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }else break;
        }
    }
}

import javax.sound.sampled.*;

/**
 @author Setaniel
 @version 1.1.2
 */

public class Sound {
/*    public static void main(String[] args) {
        instance = getInstance();
        instance.PlayMusicSDL();
    }*/

    private static Sound instance;

    private Sound(){
    }

    public static Sound getInstance(){
        if (instance == null) instance = new Sound();
        return instance;
    }

    public void PlayMusicSDL() {
        byte[] b = new byte[2048];
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(getInstance().getClass().getResource("/sound/mario.wav"));
            AudioFormat af = ais.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
            if (AudioSystem.isLineSupported(info)) {
                SourceDataLine clipSDL = (SourceDataLine)AudioSystem.getLine(info);
                clipSDL.open(af);
                clipSDL.start();
                int num;
                while ((num=ais.read(b))!=-1)
                    clipSDL.write(b, 0, num);
                clipSDL.drain();
                ais.close();
                clipSDL.stop();
                clipSDL.close();
            }else {
                System.out.println("File format not supported");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

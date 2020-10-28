import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class check your internet connection.
 * {@link #ping} method pinging google.com, using system methods.
 * Result showing in Label, on app right-bottom.
 * */
public class NetChecker {
    private static final Label netLabel = Utility.getNetLabel();
    private static BufferedReader inStream;
    private static Process process;
    private static Thread thread;

    /**
     * Run command, set style on netLabel by command response
     * */
   private static void runSystemPing(String command) {
        // Init Process and InputStream
        try {
            process = Runtime.getRuntime().exec(command);
            inStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // reading output stream of the command
        String s;
        try {
            s = inStream.readLine();
            if (s != null/* && !s.contains("timeout")*/) {
                // Set green iNet background
                netLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf("#20db39"),
                        new CornerRadii(16), Insets.EMPTY)));
            }else {
                // Set red iNet background
                netLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf("#e05c3b"),
                        new CornerRadii(16), Insets.EMPTY)));
            }
            process.destroy();
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run ping on Threads
     * */
    public static void ping() {
        // Long running operation runs on different thread
        thread = new Thread(() -> {
            Runnable updater = () -> {
                runSystemPing("ping google.com");
            };

            // Thread sleep every 2 seconds and start Platform.runLater(updater)
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.getStackTrace();
                }
                // UI update is run on the Application thread
                Platform.runLater(updater);
            }
            // New iteration

        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();
    }
    public static void stopPing(){
        thread.interrupt();
    }
}

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
     * Run any terminal command, by method signature
     * */
    @Deprecated private static void runSystemCommand(String command) {
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
            // reading output stream of the command
            if (inputStream.readLine() != null) {
                // Set green background
                netLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf("#20db39"),
                        new CornerRadii(16), Insets.EMPTY)));
            }else {
                // Set red background
                netLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf("#e05c3b"),
                        new CornerRadii(16), Insets.EMPTY)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Run new Task and ping every 2 seconds -> google.com
     * */
    @Deprecated public static void pingX(){
        // Init Process and InputStream
        BufferedReader inputStream = null;
        Process p;
        try {
            p = Runtime.getRuntime().exec("ping google.com");
            inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Start Task
        Timer timer = new Timer();
        BufferedReader finalInputStream = inputStream;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("?");
//                Platform.runLater(() -> {
                    // reading output stream of the command
                    try {
                        if (finalInputStream.readLine() != null) {
                            // Set green background
                            netLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf("#20db39"),
                                    new CornerRadii(16), Insets.EMPTY)));
                        }else {
                            // Set red background
                            netLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf("#e05c3b"),
                                    new CornerRadii(16), Insets.EMPTY)));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }// ____End block________________
//                });
            }
        },0, 1 * 1000);
    }

    /**
     * Run ping on Threads
     * */
    public static void ping() {
        // Long running operation runs on different thread
        thread = new Thread(() -> {
            Runnable updater = () -> {
                // Init Process and InputStream
                try {
                    process = Runtime.getRuntime().exec("ping google.com");
                    inStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // reading output stream of the command
                String s;
                try {
                    s = inStream.readLine();
                    if (s != null && !s.contains("timeout")) {
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
            };
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.getStackTrace();
                }
                // UI update is run on the Application thread
                Platform.runLater(updater);
            }
        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();
    }
    public static void stopPing(){
        thread.interrupt();
    }
}

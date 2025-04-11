import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
   This class check your internet connection.
   {@link #ping} method pinging google.com, using system methods.
   Result showing in Label, on app right-bottom.
   @author Setaniel
   @version 1.1.4
   */
class NetChecker {
    private static final Label netLabel = Utility.getInstance().getNetLabel();
    private BufferedReader inStream;
    private Process process;
    private Thread thread;
    private static NetChecker instance;

    private NetChecker(){
    }

    public static NetChecker getInstance() {
        if (instance == null) instance = new NetChecker();
        return instance;
    }

    /**
     * Run command, set style on netLabel by command response
     * */
    @Deprecated private void runSystemPing(String command) {
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
            System.out.println(s);
            if (s != null && !s.contains("google.com")) {
                // Set green iNet background
                netLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf("#20db39"),
                        new CornerRadii(16), Insets.EMPTY)));
            }else {
                System.out.println(s);
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
     * Check google.com, set style on netLabel by check response
     * */
    private static void check(){
        try{
            InetAddress.getByName("www.google.com");
            // Set green iNet background
            netLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf("#20db39"),
                    new CornerRadii(16), Insets.EMPTY)));
        }catch(UnknownHostException e){
            // Set red iNet background
            netLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf("#e05c3b"),
                    new CornerRadii(16), Insets.EMPTY)));
        }
    }

    /**
     * Run net check method on every 2 seconds
     * */
    void ping() {
        // Long running operation runs on different thread
        thread = new Thread(() -> {
            Runnable updater = NetChecker::check;
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
    void stopPing(){
        thread.interrupt();
    }
}

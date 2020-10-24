import java.io.BufferedReader;
import java.io.InputStreamReader;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

public class NetChecker {
    public static void runSystemCommand(String command) {

        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String s = "";
            // reading output stream of the command
            while ((s = inputStream.readLine()) != null) {
                System.out.println(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket s = new Socket("216.58.209.206", 5000);
        s.getOutputStream().write((byte) '\n');
        int ch = s.getInputStream().read();
        s.close();
        if (ch == '\n') {
            System.out.println("Stable"); // its all good.
        }else {
            System.out.println("X");
        }
    }*/


    public static void main(String[] args) {
        String ip = "google.com";
        runSystemCommand("ping " + ip);
        Task task = new Task<Void>() {
            @Override public Void call() {
                final int max = 1000000;
                for (int i=1; i<=max; i++) {
                    if (isCancelled()) {
                        break;
                    }
                    updateProgress(i, max);
                }
                return null;
            }
        };
        ProgressBar bar = new ProgressBar();
        bar.progressProperty().bind(task.progressProperty());

        new Thread(task).start();
    }
}

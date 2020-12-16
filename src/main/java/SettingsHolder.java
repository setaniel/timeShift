import java.io.Serializable;

public class SettingsHolder implements Serializable {
    private static final long serialVersionUID = 202;

    private boolean currentTheme;
    private int pomodoroTime;
    private static SettingsHolder instance;

    static SettingsHolder getInstance(){
        if (instance == null) instance = new SettingsHolder();
        return instance;
    }

    public static void setInstance(SettingsHolder deserializedInstance) {
        instance = new SettingsHolder(deserializedInstance.currentTheme, deserializedInstance.pomodoroTime);
    }

    private SettingsHolder(){
        currentTheme = false;
        pomodoroTime = 25;
    }

    private SettingsHolder(boolean theme, int time){
        currentTheme = theme;
        pomodoroTime = time;
    }

    void setCurrentTheme(boolean appCurrentTheme) {
        currentTheme = appCurrentTheme;
    }

    void setPomodoroTime(int time) {
        pomodoroTime = time;
    }

    boolean getCurrentTheme() {
        return currentTheme;
    }

    int getPomodoroTime(){
        return pomodoroTime;
    }
}

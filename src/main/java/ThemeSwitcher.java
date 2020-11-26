import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


public class ThemeSwitcher {
    private static ThemeSwitcher currentTheme;

    public Color dateStampColor;
    public Color titleColor;
    public Color previewColor;
    public Color backgroundColor;
    public ImageView backgroundNoteImage;

    private ThemeSwitcher(){
    }

    public static ThemeSwitcher getCurrentTheme() {
        if (currentTheme == null) currentTheme = new ThemeSwitcher();
        return currentTheme;
    }


    public static void setCurrentTheme(ThemeSwitcher currentTheme) {
        ThemeSwitcher.currentTheme = currentTheme;
    }

    public void setLightTheme() {
        dateStampColor = Color.valueOf("#20db39");
        titleColor = Color.valueOf("white");
        previewColor = Color.valueOf("white");
        backgroundColor = Color.valueOf("#656b77");
        backgroundNoteImage = new ImageView(new Image(ThemeSwitcher.class.getResourceAsStream("images/notes.png")));
    }

    public void setDarkTheme() {
        dateStampColor = Color.valueOf("#20db39");
        titleColor = Color.valueOf("white");
        previewColor = Color.valueOf("white");
        backgroundColor = Color.valueOf("#656b77");
        backgroundNoteImage = new ImageView(new Image(ThemeSwitcher.class.getResourceAsStream("images/d_notes.png")));
    }
}

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.List;


public class ThemeSwitcher {
    private static ThemeSwitcher currentTheme;
    private static ImageView backgroundAppImage;
    private static Color dateStampColor;
    private static Color titleColor;
    private static Color previewColor;
    private static Color backgroundColor;
    private static boolean isDark;

    private ThemeSwitcher(){
    }

    public static ThemeSwitcher getCurrentTheme() {
        if (currentTheme == null) {
            currentTheme = new ThemeSwitcher();
            currentTheme.setLightTheme();
        }
        return currentTheme;
    }


    public static void setCurrentTheme(boolean currentTheme) {
        isDark = currentTheme;
        if (isDark()){
            setDarkTheme();
        }else {
            setLightTheme();
        }
        applyCurrentTheme();
    }

    public static void setLightTheme() {
        isDark = false;
        dateStampColor = Color.valueOf("#008500");
        titleColor = Color.valueOf("black");
        previewColor = Color.valueOf("black");
        backgroundColor = Color.valueOf("#e8e4db");
        backgroundAppImage = new ImageView(new Image(ThemeSwitcher.class.getResourceAsStream("images/notes.png")));
        applyCurrentTheme();
    }

    public static void setDarkTheme() {
        isDark = true;
        dateStampColor = Color.valueOf("#20db39");
        titleColor = Color.valueOf("#E2E9E4");
        previewColor = Color.valueOf("#E2E9E4");
        backgroundColor = Color.valueOf("#656b77");
        backgroundAppImage = new ImageView(new Image(ThemeSwitcher.class.getResourceAsStream("images/d_notes.png")));
        applyCurrentTheme();
    }
    private static void setNoteColors(){
        List<Node> list = Utility.getContent().getChildren();
        for (Node note  : list) {
            Note n = (Note) note;
            n.setTitleLabelColor();
            n.setPreviewLabelColor();
            n.setDateStampLabelColor();
        }
    }

    private static void applyCurrentTheme(){
        backgroundAppImage.setFitHeight(522.0);
        backgroundAppImage.setFitWidth(380.0);
        setNoteColors();
        Utility.getController().fxThemeBackground();
    }


    public Color getDateStampColor() {
        return dateStampColor;
    }

    public Color getTitleColor() {
        return titleColor;
    }

    public Color getPreviewColor() {
        return previewColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public ImageView getBackgroundAppImage() {
        return backgroundAppImage;
    }

    public static boolean isDark() {
        return isDark;
    }

    public static void setDark(boolean dark) {
        isDark = dark;
    }
}

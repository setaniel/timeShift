import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.List;


class ThemeSwitcher {
    private static ThemeSwitcher currentTheme;
    private static ImageView backgroundAppImage;
    private static Color dateStampColor;
    private static Color titleColor;
    private static Color previewColor;
    private static Color backgroundColor;
    private static Color noteShadow;
    private static boolean isDark;

    private ThemeSwitcher(){
    }

    static ThemeSwitcher getCurrentTheme() {
        if (currentTheme == null) {
            currentTheme = new ThemeSwitcher();
            setLightTheme();
        }
        return currentTheme;
    }

    static void setCurrentTheme(boolean currentTheme) {
        isDark = currentTheme;
        if (isDark()){
            setDarkTheme();
        }else {
            setLightTheme();
        }
        applyCurrentTheme();
    }

    static void setLightTheme() {
        isDark = false;
        dateStampColor = Color.valueOf("#008500");
        titleColor = Color.valueOf("#555249");
        previewColor = Color.valueOf("#555249");
        backgroundColor = Color.valueOf("#e8e4db");
        noteShadow = Color.DARKCYAN;
        backgroundAppImage = new ImageView(new Image(ThemeSwitcher.class.getResourceAsStream("images/notes.png")));
        applyCurrentTheme();
    }

    static void setDarkTheme() {
        isDark = true;
        dateStampColor = Color.valueOf("#20db39");
        titleColor = Color.valueOf("#E2E9E4");
        previewColor = Color.valueOf("#E2E9E4");
        backgroundColor = Color.valueOf("#656b77");
        noteShadow = Color.valueOf("#98ecf2");
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
            n.setNoteShadow();
        }
    }

    private static void applyCurrentTheme(){
        backgroundAppImage.setFitHeight(522.0);
        backgroundAppImage.setFitWidth(380.0);
        setNoteColors();
        Utility.getController().fxThemeBackground();
    }

    Color getDateStampColor() {
        return dateStampColor;
    }

    Color getTitleColor() {
        return titleColor;
    }

    Color getPreviewColor() {
        return previewColor;
    }

    Color getBackgroundColor() {
        return backgroundColor;
    }

    ImageView getBackgroundAppImage() {
        return backgroundAppImage;
    }

    Color getNoteShadow() {
        return noteShadow;
    }

    static boolean isDark() {
        return isDark;
    }

    static void setDark(boolean dark) {
        isDark = dark;
    }
}

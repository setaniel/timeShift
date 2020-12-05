import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.List;


class ThemeSwitcher {
    private static ThemeSwitcher instance;
    private ImageView backgroundAppImage;
    private Color dateStampColor;
    private Color titleColor;
    private Color previewColor;
    private Color backgroundColor;
    private Color noteShadow;
    private boolean isDark;

    private ThemeSwitcher(){
//        setLightTheme();
    }

    static ThemeSwitcher getInstance() {
        if (instance == null) {
            instance = new ThemeSwitcher();
        }
        return instance;
    }

    void setCurrentTheme(boolean setIsDark) {
        isDark = setIsDark;
        if (isDark()){
            setDarkTheme();
        }else {
            setLightTheme();
        }
        applyCurrentTheme();
    }

    private void setLightTheme() {
        isDark = false;
        dateStampColor = Color.valueOf("#008500");
        titleColor = Color.valueOf("#555249");
        previewColor = Color.valueOf("#555249");
        backgroundColor = Color.valueOf("#e8e4db");
        noteShadow = Color.DARKCYAN;
        backgroundAppImage = new ImageView(new Image(ThemeSwitcher.class.getResourceAsStream("images/notes.png")));
        applyCurrentTheme();
    }

    private void setDarkTheme() {
        isDark = true;
        dateStampColor = Color.valueOf("#20db39");
        titleColor = Color.valueOf("#E2E9E4");
        previewColor = Color.valueOf("#E2E9E4");
        backgroundColor = Color.valueOf("#656b77");
        noteShadow = Color.valueOf("#98ecf2");
        backgroundAppImage = new ImageView(new Image(ThemeSwitcher.class.getResourceAsStream("images/d_notes.png")));
        applyCurrentTheme();
    }

    private void setNoteColors(){
        List<Node> list = Utility.getContent().getChildren();
        for (Node note  : list) {
            Note n = (Note) note;
            n.setTitleLabelColor();
            n.setPreviewLabelColor();
            n.setDateStampLabelColor();
            n.setNoteShadow();
        }
    }

    private void applyCurrentTheme(){
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

    boolean isDark() {
        return isDark;
    }
}

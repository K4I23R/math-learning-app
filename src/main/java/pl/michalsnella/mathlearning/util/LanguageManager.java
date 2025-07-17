package pl.michalsnella.mathlearning.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {

    private static Locale currentLocale = new Locale("pl", "PL");
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("lang.messages", currentLocale);

    public static void setLanguage(String lang) {
        currentLocale = new Locale("pl", lang);
        resourceBundle = ResourceBundle.getBundle("lang.messages", currentLocale);
    }

    public static String getString(String key) {
        return resourceBundle.getString(key);
    }
}

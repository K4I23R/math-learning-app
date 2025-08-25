package pl.michalsnella.mathlearning.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class LanguageManager {

    private static Locale currentLocale = Locale.forLanguageTag("pl");
    private static ResourceBundle bundle = loadBundle();

    private static ResourceBundle loadBundle() {
        return ResourceBundle.getBundle("lang.messages", currentLocale);
    }

    public static void setLanguage(String langCode) {
        currentLocale = Locale.forLanguageTag(langCode);
        bundle = loadBundle();
    }

    public static String getString(String key) {
        return bundle.getString(key);
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static class UTF8Control extends ResourceBundle.Control {
        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format,
                                        ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {

            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");

            try (var stream = loader.getResourceAsStream(resourceName)) {
                if (stream == null) return null;
                return new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
            }
        }
    }

}

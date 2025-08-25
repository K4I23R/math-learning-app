package pl.michalsnella.mathlearning.config;

public class Settings {

    private static Settings instance;

    private String language = "pl";

    private Settings() {}

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

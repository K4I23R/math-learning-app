package pl.michalsnella.mathlearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.michalsnella.mathlearning.config.Settings;
import pl.michalsnella.mathlearning.utils.LanguageManager;
import pl.michalsnella.mathlearning.utils.SceneManager;

public class LanguagesController {

    @FXML
    public Button backButton;

    @FXML
    public void initialize() {
        backButton.setText(LanguageManager.getString("language.back"));
    }

    public void onPolishClicked(ActionEvent actionEvent) {
        Settings.getInstance().setLanguage("pl");
        LanguageManager.setLanguage("pl");
        reloadToSettings();
    }

    public void onEnglishClicked(ActionEvent actionEvent) {
        Settings.getInstance().setLanguage("en");
        LanguageManager.setLanguage("en");
        reloadToSettings();
    }

    private void reloadToSettings() {
        try {
            SceneManager.switchTo("/fxml/settings.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBackClicked() {
        reloadToSettings();
    }
}

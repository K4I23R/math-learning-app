package pl.michalsnella.mathlearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pl.michalsnella.mathlearning.util.LanguageManager;
import pl.michalsnella.mathlearning.util.SceneManager;

public class SettingsController {

    @FXML
    public Button languageButton;

    @FXML
    private Button backButton;

    @FXML
    private Label titleLabel;

    @FXML
    public void initialize() {
        languageButton.setText(LanguageManager.getString("settings.language"));
        backButton.setText(LanguageManager.getString("settings.back"));
        titleLabel.setText(LanguageManager.getString("settings.title"));
    }


    @FXML
    private void onBackClicked() {
        try {
            SceneManager.switchTo("/fxml/main_menu.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void onLanguageClicked(ActionEvent actionEvent) {
        try {
            SceneManager.switchTo("/fxml/languages.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

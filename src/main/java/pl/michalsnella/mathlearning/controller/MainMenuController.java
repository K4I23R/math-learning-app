package pl.michalsnella.mathlearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.michalsnella.mathlearning.util.LanguageManager;
import pl.michalsnella.mathlearning.util.SceneManager;

public class MainMenuController {

    @FXML
    public Button startButton;
    @FXML
    public Button exitButton;
    @FXML
    public Button settingsButton;

    @FXML
    public void initialize() {
        startButton.setText(LanguageManager.getString("menu.start"));
        settingsButton.setText(LanguageManager.getString("menu.settings"));
        exitButton.setText(LanguageManager.getString("menu.exit"));
    }

    @FXML
    public void onStartClicked(ActionEvent actionEvent) {
        try {
            SceneManager.switchTo("/fxml/settings.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSettingsClicked() {
        try {
            SceneManager.switchTo("/fxml/settings.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onExitClicked(ActionEvent actionEvent) {
        javafx.application.Platform.exit();
    }


}

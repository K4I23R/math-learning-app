package pl.michalsnella.mathlearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pl.michalsnella.mathlearning.util.LanguageManager;
import pl.michalsnella.mathlearning.util.SceneManager;

public class ChallengeModeController {

    @FXML
    public Button additionChallengeButton;
    @FXML
    public Button backButton;

    @FXML
    private Label titleLabel;

    @FXML
    public void initialize() {
        additionChallengeButton.setText(LanguageManager.getString("challenges.addition"));
        backButton.setText(LanguageManager.getString("challenges.back"));

        titleLabel.setText(LanguageManager.getString("challenges.title"));
    }

    public void onAdditionClicked(ActionEvent actionEvent) {
        try {
            SceneManager.switchTo("/fxml/challenge_addition.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackClicked(ActionEvent actionEvent) {
        try {
            SceneManager.switchTo("/fxml/start.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

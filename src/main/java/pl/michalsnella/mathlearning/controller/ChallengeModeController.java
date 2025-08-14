package pl.michalsnella.mathlearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pl.michalsnella.mathlearning.util.LanguageManager;
import pl.michalsnella.mathlearning.util.SceneManager;

public class ChallengeModeController {

    @FXML
    public Button additionChallengeButton, subtractionChallengeButton, multiplicationChallengeButton, divisionChallengeButton, backButton;

    @FXML
    private Label titleLabel;

    @FXML
    public void initialize() {
        additionChallengeButton.setText(LanguageManager.getString("challenges.addition"));
        subtractionChallengeButton.setText(LanguageManager.getString("challenges.subtraction"));
        multiplicationChallengeButton.setText(LanguageManager.getString("challenges.multiplication"));
        divisionChallengeButton.setText(LanguageManager.getString("challenges.division"));
        backButton.setText(LanguageManager.getString("challenges.back"));

        titleLabel.setText(LanguageManager.getString("challenges.title"));
    }


    public void onAdditionClicked(ActionEvent actionEvent) {
        try {
            SceneManager.switchTo("/fxml/exercise_addition.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onSubtractionClicked(ActionEvent actionEvent) {
        try {
            SceneManager.switchTo("/fxml/exercise_subtraction.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onMultiplicationClicked(ActionEvent actionEvent) {
        try {
            SceneManager.switchTo("/fxml/exercise_multiplication.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onDivisionClicked(ActionEvent actionEvent) {
        try {
            SceneManager.switchTo("/fxml/exercise_division.fxml");
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

package pl.michalsnella.mathlearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pl.michalsnella.mathlearning.utils.LanguageManager;
import pl.michalsnella.mathlearning.utils.SceneManager;

public class StartController {

    @FXML
    public Button lessonsButton, exercisesButton, challengesButton, backButton;

    @FXML
    private Label titleLabel;

    @FXML
    public void initialize() {
        lessonsButton.setText(LanguageManager.getString("start.lessons"));
        exercisesButton.setText(LanguageManager.getString("start.exercises"));
        challengesButton.setText(LanguageManager.getString("start.challenges"));
        backButton.setText(LanguageManager.getString("start.back"));

        titleLabel.setText(LanguageManager.getString("start.title"));
    }


    public void onLessonsClicked(ActionEvent actionEvent) {
        try {
            SceneManager.switchTo("/fxml/lesson_mode.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onExercisesClicked(ActionEvent actionEvent) {
        try {
            SceneManager.switchTo("/fxml/exercises_mode.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onChallengesClicked(ActionEvent actionEvent) {
        try {
            SceneManager.switchTo("/fxml/challenge_mode.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onBackClicked(ActionEvent actionEvent) {
        try {
            SceneManager.switchTo("/fxml/main_menu.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package pl.michalsnella.mathlearning.controller.challenges;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.michalsnella.mathlearning.config.Settings;
import pl.michalsnella.mathlearning.model.AdditionExercise;
import pl.michalsnella.mathlearning.util.LanguageManager;
import pl.michalsnella.mathlearning.util.SceneManager;

public class AdditionChallengeController {

    @FXML
    private Label titleLabel, topLabel, bottomLabel;

    @FXML
    private TextField digit0, digit1, digit2, digit3;

    private AdditionExercise currentExercise;

    @FXML
    public void initialize() {
        titleLabel.setText(LanguageManager.getString("addition_challenge.title"));
        generateNewExercise();
    }

    private void generateNewExercise() {

//        int difficulty = Settings.getInstance().getDifficulty(); // zakładamy np. 1-3
//        if (difficulty == 0) difficulty = 2;
//
//        currentExercise = new AdditionExercise(difficulty);

        currentExercise = new AdditionExercise(2);

        topLabel.setText(String.valueOf(currentExercise.getTopNumber()));
        bottomLabel.setText(String.valueOf(currentExercise.getBottomNumber()));

        digit0.clear(); digit1.clear(); digit2.clear(); digit3.clear();
        digit0.setStyle(""); digit1.setStyle(""); digit2.setStyle(""); digit3.setStyle("");
    }

    @FXML
    private void onNewClicked() {
        generateNewExercise();
    }

    @FXML
    private void onCheckClicked() {
        String userInput = digit0.getText() + digit1.getText() + digit2.getText() + digit3.getText();
        String correct = currentExercise.getResultAsString();

        // Wyrównujemy długości
        userInput = String.format("%4s", userInput).replace(' ', '0');
        correct = String.format("%4s", correct).replace(' ', '0');

        TextField[] fields = {digit0, digit1, digit2, digit3};

        for (int i = 0; i < 4; i++) {
            if (userInput.charAt(i) == correct.charAt(i)) {
                fields[i].setStyle("-fx-background-color: #b2ffb2;"); // zielony
            } else {
                fields[i].setStyle("-fx-background-color: #ffb2b2;"); // czerwony
            }
        }
    }

    @FXML
    private void onBackClicked() {
        try {
            SceneManager.switchTo("/fxml/main_menu.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


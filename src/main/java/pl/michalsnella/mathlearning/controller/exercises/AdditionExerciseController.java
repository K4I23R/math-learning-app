package pl.michalsnella.mathlearning.controller.exercises;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pl.michalsnella.mathlearning.component.DigitField;
import pl.michalsnella.mathlearning.model.AdditionExercise;
import pl.michalsnella.mathlearning.util.LanguageManager;
import pl.michalsnella.mathlearning.util.SceneManager;

import java.util.ArrayList;
import java.util.List;

public class AdditionExerciseController {

    @FXML private Label titleLabel;
    @FXML private Button newOperationButton, checkButton, backButton;
    @FXML private HBox regroupingDigitContainer, topDigitsBox, bottomDigitsBox, digitContainer;

    private AdditionExercise currentExercise;
    private final List<DigitField> digitFields = new ArrayList<>();
    private final List<DigitField> regroupingDigitFields = new ArrayList<>();

    @FXML
    public void initialize() {
        titleLabel.setText(LanguageManager.getString("addition_exercise.title"));
        newOperationButton.setText(LanguageManager.getString("addition_exercise.new_operation"));
        checkButton.setText(LanguageManager.getString("addition_exercise.check"));
        backButton.setText(LanguageManager.getString("addition_exercise.back"));
        generateNewExercise();
    }


    private void generateNewExercise() {
//        int difficulty = Settings.getInstance().getDifficulty();
//        currentExercise = new AdditionExercise(difficulty);

        currentExercise = new AdditionExercise(4);

        String resultStr = currentExercise.getResultAsString();
        int maxLength = resultStr.length();

        regroupingDigitFields.clear();
        digitFields.clear();

        regroupingDigitContainer.getChildren().clear();
        topDigitsBox.getChildren().clear();
        bottomDigitsBox.getChildren().clear();
        digitContainer.getChildren().clear();

        String topStr = String.format("%" + maxLength + "s", currentExercise.getTopNumber());
        String bottomStr = String.format("%" + maxLength + "s", currentExercise.getBottomNumber());

        for (int i = 0; i < maxLength; i++) {
            DigitField regroupingField = new DigitField();
            regroupingField.setPrefWidth(30);
            regroupingDigitFields.add(regroupingField);
            regroupingDigitContainer.getChildren().add(regroupingField);

            Label topDigit = new Label(String.valueOf(topStr.charAt(i)));
            topDigit.setMinWidth(30);
            topDigit.setStyle("-fx-font-size: 16px;");
            topDigitsBox.getChildren().add(topDigit);

            Label bottomDigit = new Label(String.valueOf(bottomStr.charAt(i)));
            bottomDigit.setMinWidth(30);
            bottomDigit.setStyle("-fx-font-size: 16px;");
            bottomDigitsBox.getChildren().add(bottomDigit);

            DigitField resultField = new DigitField();
            resultField.setPrefWidth(30);
            digitFields.add(resultField);
            digitContainer.getChildren().add(resultField);
        }
    }


    @FXML
    private void onNewClicked() {
        generateNewExercise();
    }


    @FXML
    private void onCheckClicked() {
        StringBuilder userInput = new StringBuilder();
        for (DigitField field : digitFields) {
            String val = field.getText().strip();
            userInput.append(val.isEmpty() ? " " : val);
        }

        String correct = String.format("%" + userInput.length() + "s", currentExercise.getResultAsString());

        for (int i = 0; i < digitFields.size(); i++) {
            if (i < correct.length() && userInput.charAt(i) == correct.charAt(i)) {
                digitFields.get(i).setStyle("-fx-background-color: #b2ffb2;");
            } else {
                digitFields.get(i).setStyle("-fx-background-color: #ffb2b2;");
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

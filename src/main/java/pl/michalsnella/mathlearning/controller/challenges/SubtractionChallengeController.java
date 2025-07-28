package pl.michalsnella.mathlearning.controller.challenges;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import pl.michalsnella.mathlearning.component.DigitField;
import pl.michalsnella.mathlearning.model.SubtractionExercise;
import pl.michalsnella.mathlearning.util.SceneManager;

import java.util.ArrayList;
import java.util.List;

public class SubtractionChallengeController {

    @FXML private Label titleLabel;
    @FXML private Button newOperationButton, checkButton, backButton;
    @FXML private HBox regroupingDigitContainer, topDigitsBox, bottomDigitsBox, digitContainer;

    private SubtractionExercise currentExercise;
    private final List<DigitField> digitFields = new ArrayList<>();
    private final List<DigitField> regroupingDigitFields = new ArrayList<>();
    private final List<Label> topDigitLabels = new ArrayList<>();

    @FXML
    public void initialize() {
        titleLabel.setText("Odejmowanie");
        newOperationButton.setText("Nowe działanie");
        checkButton.setText("Sprawdź");
        backButton.setText("Wróć");
        generateNewExercise();
    }

    private void generateNewExercise() {
        currentExercise = new SubtractionExercise(3);

        int maxLength = String.valueOf(currentExercise.getResult()).length();

        regroupingDigitFields.clear();
        digitFields.clear();
        topDigitLabels.clear();

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
            int finalI = i;
            topDigit.setOnMouseClicked(e -> onBorrowClicked(finalI));
            topDigitsBox.getChildren().add(topDigit);
            topDigitLabels.add(topDigit);

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

    private void onBorrowClicked(int index) {
        for (int i = index - 1; i >= 0; i--) {
            Label label = topDigitLabels.get(i);
            String text = label.getText().trim();
            if (!text.isEmpty()) {
                int val = Integer.parseInt(text);
                if (val > 0) {
                    label.setText(String.valueOf(val - 1));
                    label.setStyle("-fx-background-color: yellow; -fx-font-size: 16px;");

                    Label target = topDigitLabels.get(index);
                    int newVal = Integer.parseInt(target.getText().trim()) + 10;
                    target.setText(String.valueOf(newVal));
                    target.setStyle("-fx-background-color: lightblue; -fx-font-size: 16px;");
                    break;
                }
            }
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

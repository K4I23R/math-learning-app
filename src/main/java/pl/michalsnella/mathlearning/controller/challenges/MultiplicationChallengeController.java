package pl.michalsnella.mathlearning.controller.challenges;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.michalsnella.mathlearning.component.DigitField;
import pl.michalsnella.mathlearning.model.MultiplicationExercise;
import pl.michalsnella.mathlearning.util.LanguageManager;
import pl.michalsnella.mathlearning.util.SceneManager;

import java.util.ArrayList;
import java.util.List;

public class MultiplicationChallengeController {

    @FXML private Label titleLabel;
    @FXML private Button newOperationButton, checkButton, backButton;
    @FXML private HBox topDigitsBox, bottomDigitsBox;
    @FXML private VBox resultsContainer;

    private final List<DigitField> roughNoteFields = new ArrayList<>();
    private final List<List<DigitField>> partialResultsFields = new ArrayList<>();
    private final List<DigitField> finalSumFields = new ArrayList<>();

    private MultiplicationExercise currentExercise;
    private int currentStep = 0;

    @FXML
    public void initialize() {
        titleLabel.setText(LanguageManager.getString("multiplication_challenge.title"));
        newOperationButton.setText(LanguageManager.getString("multiplication_challenge.new_operation"));
        checkButton.setText(LanguageManager.getString("multiplication_challenge.check"));
        backButton.setText(LanguageManager.getString("multiplication_challenge.back"));
        generateNewExercise();
    }

    private void generateNewExercise() {
        currentExercise = new MultiplicationExercise(4);

        int topLength = String.valueOf(currentExercise.getTopNumber()).length();
        int bottomLength = String.valueOf(currentExercise.getBottomNumber()).length();
        int maxLength = topLength + bottomLength;

        topDigitsBox.getChildren().clear();
        bottomDigitsBox.getChildren().clear();
        resultsContainer.getChildren().clear();
        roughNoteFields.clear();
        partialResultsFields.clear();
        finalSumFields.clear();

        // Top digits
        String topStr = String.format("%" + maxLength + "s", currentExercise.getTopNumber());
        for (char c : topStr.toCharArray()) {
            Label digit = new Label(String.valueOf(c));
            digit.setMinWidth(30);
            digit.setStyle("-fx-font-size: 16px;");
            topDigitsBox.getChildren().add(digit);
        }

        // Bottom digits
        String bottomStr = String.format("%" + maxLength + "s", currentExercise.getBottomNumber());
        for (char c : bottomStr.toCharArray()) {
            Label digit = new Label(String.valueOf(c));
            digit.setMinWidth(30);
            digit.setStyle("-fx-font-size: 16px;");
            bottomDigitsBox.getChildren().add(digit);
        }

        // Rough note row
        HBox roughRow = new HBox();
        for (int i = 0; i < maxLength; i++) {
            DigitField field = new DigitField();
            field.setPrefWidth(30);
            roughNoteFields.add(field);
            roughRow.getChildren().add(field);
        }
        resultsContainer.getChildren().add(roughRow);

        // Partial result rows
        for (int i = 0; i < currentExercise.getPartialResults().size(); i++) {
            HBox partialRow = new HBox();
            List<DigitField> rowFields = new ArrayList<>();
            for (int j = 0; j < maxLength; j++) {
                DigitField field = new DigitField();
                field.setPrefWidth(30);
                rowFields.add(field);
                partialRow.getChildren().add(field);
            }
            partialResultsFields.add(rowFields);
            resultsContainer.getChildren().add(partialRow);
        }

        // Final sum row
        HBox finalRow = new HBox();
        for (int i = 0; i < maxLength; i++) {
            DigitField field = new DigitField();
            field.setPrefWidth(30);
            finalSumFields.add(field);
            finalRow.getChildren().add(field);
        }
        resultsContainer.getChildren().add(finalRow);

        // Aktywuj tryb krokowy
        currentStep = 0;
        updateStepMode();
    }

    @FXML
    private void onCheckClicked() {
        if (currentStep < partialResultsFields.size()) {
            checkPartialResultsStep();
        } else {
            checkFinalResult();
        }
    }

    private void checkPartialResultsStep() {
        List<String> expectedPartials = currentExercise.getPartialResults();
        int maxLength = finalSumFields.size();

        if (currentStep >= expectedPartials.size()) return;

        String expected = String.format("%" + maxLength + "s", expectedPartials.get(currentStep));
        List<DigitField> userRow = partialResultsFields.get(currentStep);

        boolean correct = true;

        for (int col = 0; col < userRow.size(); col++) {
            DigitField field = userRow.get(col);
            String userVal = field.getText().strip();
            char expectedChar = expected.charAt(col);

            if (userVal.isEmpty() && expectedChar == ' ') {
                field.setStyle("-fx-background-color: #b2ffb2;");
            } else if (!userVal.isEmpty() && userVal.charAt(0) == expectedChar) {
                field.setStyle("-fx-background-color: #b2ffb2;");
            } else {
                field.setStyle("-fx-background-color: #ffb2b2;");
                correct = false;
            }
        }

        if (correct) {
            currentStep++;
            updateStepMode();
        }
    }

    private void checkFinalResult() {
        String correctResult = String.valueOf(currentExercise.getResult());
        String userInput = finalSumFields.stream()
                .map(DigitField::getText)
                .reduce("", (a, b) -> a + (b.isBlank() ? " " : b));

        String paddedCorrect = String.format("%" + userInput.length() + "s", correctResult);

        for (int i = 0; i < userInput.length(); i++) {
            if (userInput.charAt(i) == paddedCorrect.charAt(i)) {
                finalSumFields.get(i).setStyle("-fx-background-color: #b2ffb2;");
            } else {
                finalSumFields.get(i).setStyle("-fx-background-color: #ffb2b2;");
            }
        }
    }

    private void updateStepMode() {
        for (int i = 0; i < partialResultsFields.size(); i++) {
            boolean editable = i == currentStep;
            for (DigitField field : partialResultsFields.get(i)) {
                field.setEditable(editable);
                field.setDisable(!editable);
            }
        }

        // Final result row aktywowany dopiero na końcu
        boolean enableFinal = currentStep >= partialResultsFields.size();
        for (DigitField field : finalSumFields) {
            field.setEditable(enableFinal);
            field.setDisable(!enableFinal);
        }
    }

    @FXML
    private void onNewClicked() {
        generateNewExercise();
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

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

        // Top
        String topStr = String.format("%" + maxLength + "s", currentExercise.getTopNumber());
        for (char c : topStr.toCharArray()) {
            Label digit = new Label(String.valueOf(c));
            digit.setMinWidth(30);
            digit.setStyle("-fx-font-size: 16px;");
            topDigitsBox.getChildren().add(digit);
        }

        // Bottom
        String bottomStr = String.format("%" + maxLength + "s", currentExercise.getBottomNumber());
        for (char c : bottomStr.toCharArray()) {
            Label digit = new Label(String.valueOf(c));
            digit.setMinWidth(30);
            digit.setStyle("-fx-font-size: 16px;");
            bottomDigitsBox.getChildren().add(digit);
        }

        // Rough
        HBox roughRow = new HBox();
        for (int i = 0; i < maxLength; i++) {
            DigitField field = new DigitField();
            field.setPrefWidth(30);
            roughNoteFields.add(field);
            roughRow.getChildren().add(field);
        }
        resultsContainer.getChildren().add(roughRow);

        // Partial
        for (int i = bottomLength - 1; i >= 0; i--) {
            HBox partialRow = new HBox();
            List<DigitField> rowFields = new ArrayList<>();
            for (int j = 0; j < maxLength; j++) {
                DigitField field = new DigitField();
                field.setPrefWidth(30);
                rowFields.add(field);
                partialRow.getChildren().add(field);
            }
            partialResultsFields.add(0, rowFields); // Reverse order: top to bottom
            resultsContainer.getChildren().add(partialRow);
        }

        // Final
        HBox finalRow = new HBox();
        for (int i = 0; i < maxLength; i++) {
            DigitField field = new DigitField();
            field.setPrefWidth(30);
            finalSumFields.add(field);
            finalRow.getChildren().add(field);
        }
        resultsContainer.getChildren().add(finalRow);
    }

    @FXML
    private void onCheckClicked() {
        checkFinalResult();
        checkPartialResults();
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

    private void checkPartialResults() {
        List<String> expectedPartials = currentExercise.getPartialResults();
        int maxLength = finalSumFields.size(); // czyli max długość wierszy

        for (int row = 0; row < expectedPartials.size(); row++) {
            String expected = String.format("%" + maxLength + "s", expectedPartials.get(row)); // wyrównaj do prawej
            List<DigitField> userRow = partialResultsFields.get(row);

            for (int col = 0; col < userRow.size(); col++) {
                DigitField field = userRow.get(col);
                String userVal = field.getText().strip();
                char expectedChar = expected.charAt(col);

                if (userVal.isEmpty() && expectedChar == ' ') {
                    // Poprawna pusta komórka
                    field.setStyle("-fx-background-color: #b2ffb2;");
                } else if (!userVal.isEmpty() && userVal.charAt(0) == expectedChar) {
                    field.setStyle("-fx-background-color: #b2ffb2;");
                } else {
                    field.setStyle("-fx-background-color: #ffb2b2;");
                }
            }
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

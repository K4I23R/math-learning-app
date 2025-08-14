package pl.michalsnella.mathlearning.controller.exercises;

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

public class MultiplicationExerciseController {

    @FXML private Label titleLabel;
    @FXML private Button newOperationButton, checkButton, backButton;
    @FXML private HBox topDigitsBox, bottomDigitsBox;
    @FXML private VBox resultsContainer;

    private final List<DigitField> roughNoteFields = new ArrayList<>();
    private final List<List<DigitField>> partialResultsFields = new ArrayList<>();
    private final List<Button> partialCheckButtons = new ArrayList<>();
    private final List<DigitField> finalSumFields = new ArrayList<>();

    private MultiplicationExercise currentExercise;

    @FXML
    public void initialize() {
        titleLabel.setText(LanguageManager.getString("multiplication_exercise.title"));
        newOperationButton.setText(LanguageManager.getString("multiplication_exercise.new_operation"));
        checkButton.setText(LanguageManager.getString("multiplication_exercise.check"));
        checkButton.setDisable(true);
        backButton.setText(LanguageManager.getString("multiplication_exercise.back"));
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
        partialCheckButtons.clear();
        finalSumFields.clear();
        checkButton.setDisable(true);

        String topStr = String.format("%" + maxLength + "s", currentExercise.getTopNumber());
        for (char c : topStr.toCharArray()) {
            Label digit = new Label(String.valueOf(c));
            digit.setMinWidth(30);
            digit.setStyle("-fx-font-size: 16px;");
            topDigitsBox.getChildren().add(digit);
        }

        String bottomStr = String.format("%" + maxLength + "s", currentExercise.getBottomNumber());
        for (char c : bottomStr.toCharArray()) {
            Label digit = new Label(String.valueOf(c));
            digit.setMinWidth(30);
            digit.setStyle("-fx-font-size: 16px;");
            bottomDigitsBox.getChildren().add(digit);
        }

        HBox roughRow = new HBox();
        for (int i = 0; i < maxLength; i++) {
            DigitField field = new DigitField();
            field.setPrefWidth(30);
            roughNoteFields.add(field);
            roughRow.getChildren().add(field);
        }
        resultsContainer.getChildren().add(roughRow);

        List<String> expectedPartials = currentExercise.getPartialResults();
        for (int i = 0; i < expectedPartials.size(); i++) {
            HBox row = new HBox();
            List<DigitField> fields = new ArrayList<>();

            for (int j = 0; j < maxLength; j++) {
                DigitField field = new DigitField();
                field.setPrefWidth(30);
                field.setDisable(i > 0);
                fields.add(field);
                row.getChildren().add(field);
            }

            Button check = getButton(i);
            row.getChildren().add(check);

            partialResultsFields.add(fields);
            partialCheckButtons.add(check);
            resultsContainer.getChildren().add(row);
        }

        HBox finalRow = new HBox();
        for (int i = 0; i < maxLength; i++) {
            DigitField field = new DigitField();
            field.setPrefWidth(30);
            field.setDisable(true);
            finalSumFields.add(field);
            finalRow.getChildren().add(field);
        }
        resultsContainer.getChildren().add(finalRow);
    }


    private Button getButton(int i) {
        Button check = new Button(LanguageManager.getString("multiplication_exercise.check"));
        final int index = i;
        check.setOnAction(e -> {
            if (checkPartialRow(index)) {
                check.setDisable(true);
                if (index + 1 < partialResultsFields.size()) {
                    partialResultsFields.get(index + 1).forEach(f -> f.setDisable(false));
                    partialCheckButtons.get(index + 1).setDisable(false);
                } else {
                    finalSumFields.forEach(f -> f.setDisable(false));
                    checkButton.setDisable(false);
                }
            }
        });
        check.setDisable(i > 0);
        return check;
    }


    private boolean checkPartialRow(int rowIndex) {
        List<String> expectedPartials = currentExercise.getPartialResults();
        int maxLength = finalSumFields.size();

        String expected = String.format("%" + maxLength + "s", expectedPartials.get(rowIndex));
        List<DigitField> userRow = partialResultsFields.get(rowIndex);
        boolean allCorrect = true;

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
                allCorrect = false;
            }
        }

        return allCorrect;
    }


    @FXML
    private void onCheckClicked() {
        checkFinalResult();
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

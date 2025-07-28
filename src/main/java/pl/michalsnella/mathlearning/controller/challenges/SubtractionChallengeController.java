package pl.michalsnella.mathlearning.controller.challenges;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pl.michalsnella.mathlearning.component.DigitField;
import pl.michalsnella.mathlearning.model.SubtractionExercise;
import pl.michalsnella.mathlearning.util.LanguageManager;
import pl.michalsnella.mathlearning.util.SceneManager;

import java.util.*;

public class SubtractionChallengeController {

    @FXML private Label titleLabel, messageLabel;
    @FXML private Button newOperationButton, checkButton, backButton;
    @FXML private HBox regroupingDigitContainer, topDigitsBox, bottomDigitsBox, digitContainer;

    private SubtractionExercise currentExercise;
    private final List<DigitField> digitFields = new ArrayList<>();
    private final List<DigitField> regroupingDigitFields = new ArrayList<>();
    private final List<Label> topDigitLabels = new ArrayList<>();
    private final List<Label> bottomDigitLabels = new ArrayList<>();

    private final Set<Integer> borrowedIndices = new HashSet<>();


    @FXML
    public void initialize() {
        titleLabel.setText(LanguageManager.getString("subtraction_challenge.title"));
        newOperationButton.setText(LanguageManager.getString("subtraction_challenge.new_operation"));
        checkButton.setText(LanguageManager.getString("subtraction_challenge.check"));
        backButton.setText(LanguageManager.getString("subtraction_challenge.back"));
        generateNewExercise();
    }


    private void generateNewExercise() {
        currentExercise = new SubtractionExercise(3);

        int maxLength = String.valueOf(currentExercise.getResult()).length();

        regroupingDigitFields.clear();
        digitFields.clear();
        topDigitLabels.clear();
        bottomDigitLabels.clear();
        borrowedIndices.clear();

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
            bottomDigitLabels.add(bottomDigit);

            DigitField resultField = new DigitField();
            resultField.setPrefWidth(30);
            digitFields.add(resultField);
            digitContainer.getChildren().add(resultField);
        }
    }


    private void onBorrowClicked(int index) {
        messageLabel.setText("");

        if (index <= 0 || index >= topDigitLabels.size()) return;

        Label to = topDigitLabels.get(index);
        Label from = topDigitLabels.get(index - 1);
        Label bottomAtIndex = bottomDigitLabels.get(index);

        int toVal = parseLabel(to);
        int fromVal = parseLabel(from);
        int bottomVal = parseLabel(bottomAtIndex);

        if (borrowedIndices.contains(index - 1)) {
            if (toVal - 10 < 0) {
                showMessage(LanguageManager.getString("subtraction_challenge.message1"));
                return;
            }
            to.setText(String.valueOf(toVal - 10));
            to.setStyle("-fx-font-size: 16px;");
            from.setText(String.valueOf(fromVal + 1));
            from.setStyle("-fx-font-size: 16px;");
            borrowedIndices.remove(index - 1);
            return;
        }

        if (toVal >= bottomVal) {
            showMessage(LanguageManager.getString("subtraction_challenge.message2"));
            return;
        }

        int bottomUnderFrom = bottomDigitValue(index - 1);

        if (fromVal > 0) {
            boolean isLeading = isLeadingDigit(index - 1);

            if (fromVal > bottomUnderFrom || (fromVal == bottomUnderFrom && !isLeading)) {
                from.setText(String.valueOf(fromVal - 1));
                from.setStyle("-fx-background-color: yellow; -fx-font-size: 16px;");
                to.setText(String.valueOf(toVal + 10));
                to.setStyle("-fx-background-color: lightblue; -fx-font-size: 16px;");
                borrowedIndices.add(index - 1);
            } else {
                showMessage(LanguageManager.getString("subtraction_challenge.message3"));
            }
        } else {
            showMessage(LanguageManager.getString("subtraction_challenge.message3"));
        }
    }


    private boolean isLeadingDigit(int index) {
        for (int i = 0; i < bottomDigitLabels.size(); i++) {
            String text = bottomDigitLabels.get(i).getText().trim();
            if (!text.isEmpty() && !text.equals("0")) {
                return i == index;
            }
        }
        return false;
    }


    private int bottomDigitValue(int index) {
        if (index < 0 || index >= bottomDigitLabels.size()) return -1;
        return parseLabel(bottomDigitLabels.get(index));
    }


    private int parseLabel(Label label) {
        try {
            String text = label.getText().trim();
            return text.isEmpty() ? 0 : Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
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


    private void showMessage(String message) {
        messageLabel.setText(message);
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                javafx.application.Platform.runLater(() -> messageLabel.setText(""));
            } catch (InterruptedException ignored) {}
        }).start();
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

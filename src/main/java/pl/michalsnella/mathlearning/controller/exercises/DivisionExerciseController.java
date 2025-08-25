package pl.michalsnella.mathlearning.controller.exercises;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import pl.michalsnella.mathlearning.component.DigitField;
import pl.michalsnella.mathlearning.model.exercise.DivisionExercise;
import pl.michalsnella.mathlearning.model.exercise.DivisionStep;
import pl.michalsnella.mathlearning.utils.LanguageManager;
import pl.michalsnella.mathlearning.utils.SceneManager;

import java.util.ArrayList;
import java.util.List;

public class DivisionExerciseController {

    @FXML private Label titleLabel;
    @FXML private HBox topAnswerBox;
    @FXML private HBox divisorBox;
    @FXML private HBox dividendBox;
    @FXML private VBox stepsContainer;
    @FXML private Button newOperationButton, backButton;

    private final List<DigitField> resultFields = new ArrayList<>();
    private final List<List<DigitField>> multipleFields = new ArrayList<>();
    private final List<List<DigitField>> subtractionFields = new ArrayList<>();
    private final List<Button> checkStepButtons = new ArrayList<>();
    private DivisionExercise exercise;

    private int nextResultIndex = 0;


    @FXML
    public void initialize() {
        titleLabel.setText(LanguageManager.getString("division_exercise.title"));
        newOperationButton.setText(LanguageManager.getString("division_exercise.new_operation"));
        backButton.setText(LanguageManager.getString("division_exercise.back"));
        generateNewExercise();
    }


    private void generateNewExercise() {
        exercise = new DivisionExercise(5);
        resultFields.clear();
        multipleFields.clear();
        subtractionFields.clear();
        checkStepButtons.clear();

        topAnswerBox.getChildren().clear();
        divisorBox.getChildren().clear();
        dividendBox.getChildren().clear();
        stepsContainer.getChildren().clear();

        String dividendStr = String.valueOf(exercise.getDividend());
        String divisorStr = String.valueOf(exercise.getDivisor());
        String resultStr = String.valueOf(exercise.getResult());

        for (int i = 0; i < resultStr.length(); i++) {
            DigitField field = new DigitField();
            field.setPrefWidth(30);
            field.setAlignment(Pos.CENTER);
            field.setDisable(true);
            resultFields.add(field);
            topAnswerBox.getChildren().add(field);
        }

        nextResultIndex = 0;
        resultFields.get(nextResultIndex).setDisable(false);
        while (nextResultIndex + 1 < resultStr.length() && resultStr.charAt(nextResultIndex + 1) == '0') {
            nextResultIndex++;
            resultFields.get(nextResultIndex).setDisable(false);
        }
        nextResultIndex++;

        for (char c : dividendStr.toCharArray()) {
            Label label = new Label(String.valueOf(c));
            label.setMinWidth(30);
            label.setStyle("-fx-font-size: 18px;");
            label.setAlignment(Pos.CENTER);
            dividendBox.getChildren().add(label);
        }

        for (char c : divisorStr.toCharArray()) {
            Label label = new Label(String.valueOf(c));
            label.setMinWidth(30);
            label.setStyle("-fx-font-size: 18px;");
            label.setAlignment(Pos.CENTER);
            divisorBox.getChildren().add(label);
        }

        int currentOffset = 0;
        for (int stepIndex = 0; stepIndex < exercise.getSteps().size(); stepIndex++) {
            DivisionStep step = exercise.getSteps().get(stepIndex);
            VBox stepBox = new VBox(2);
            HBox multipleBox = new HBox(2);
            HBox subtractionBox = new HBox(2);
            Button checkStepButton = new Button(LanguageManager.getString("division_exercise.check_step"));
            checkStepButton.setDisable(stepIndex != 0);
            checkStepButtons.add(checkStepButton);
            int finalStepIndex = stepIndex;

            List<DigitField> multipleRow = new ArrayList<>();
            List<DigitField> subtractionRow = new ArrayList<>();

            for (int i = 0; i < currentOffset; i++) {
                multipleBox.getChildren().add(spacer());
            }

            Label minusLabel = new Label("-");
            minusLabel.setMinWidth(10);
            minusLabel.setStyle("-fx-font-size: 16px;");
            multipleBox.getChildren().add(minusLabel);

            for (char ignored : step.getMultiple().toCharArray()) {
                DigitField field = new DigitField();
                field.setPrefWidth(30);
                field.setAlignment(Pos.CENTER);
                field.setDisable(stepIndex != 0);
                multipleBox.getChildren().add(field);
                multipleRow.add(field);
            }

            Separator separator = new Separator();
            separator.setPrefWidth(30.0 * Math.max(step.getSegment().length(), step.getMultiple().length()));
            separator.setStyle("-fx-border-color: black;");

            for (int i = 0; i < currentOffset + 1; i++) {
                subtractionBox.getChildren().add(spacer());
            }

            int subtractionFieldCount = (stepIndex < exercise.getSteps().size() - 1)
                    ? exercise.getSteps().get(stepIndex + 1).getSegment().length()
                    : 1;

            for (int i = 0; i < subtractionFieldCount; i++) {
                DigitField field = new DigitField();
                field.setPrefWidth(30);
                field.setAlignment(Pos.CENTER);
                field.setDisable(stepIndex != 0);
                subtractionBox.getChildren().add(field);
                subtractionRow.add(field);
            }

            multipleFields.add(multipleRow);
            subtractionFields.add(subtractionRow);

            checkStepButton.setOnAction(e -> handleCheckStep(finalStepIndex));
            stepBox.getChildren().addAll(multipleBox, separator, subtractionBox, checkStepButton);
            stepsContainer.getChildren().add(stepBox);
            currentOffset++;
        }
    }


    private void handleCheckStep(int stepIndex) {
        DivisionStep step = exercise.getSteps().get(stepIndex);
        List<DigitField> multipleRow = multipleFields.get(stepIndex);
        List<DigitField> subtractionRow = subtractionFields.get(stepIndex);
        String resultStr = String.valueOf(exercise.getResult());

        boolean allCorrect = true;

        for (DigitField field : resultFields) {
            if (!field.isDisable()) {
                int index = resultFields.indexOf(field);
                String expectedChar = String.valueOf(resultStr.charAt(index));
                String actualChar = field.getText();
                if (actualChar.equals(expectedChar)) {
                    field.setStyle("-fx-background-color: #b2ffb2;");
                    field.setDisable(true);
                } else {
                    field.setStyle("-fx-background-color: #ffb2b2;");
                    allCorrect = false;
                }
            }
        }

        String correctMultiple = step.getMultiple();
        allCorrect = isAllCorrect(multipleRow, allCorrect, correctMultiple);

        if (stepIndex < exercise.getSteps().size() - 1) {
            String nextSegment = exercise.getSteps().get(stepIndex + 1).getSegment();
            allCorrect = isAllCorrect(subtractionRow, allCorrect, nextSegment);
        } else {
            String text = subtractionRow.get(0).getText();
            if (text.equals("0")) {
                subtractionRow.get(0).setStyle("-fx-background-color: #b2ffb2;");
                subtractionRow.get(0).setDisable(true);
            } else {
                subtractionRow.get(0).setStyle("-fx-background-color: #ffb2b2;");
                allCorrect = false;
            }
        }

        if (allCorrect) {
            checkStepButtons.get(stepIndex).setDisable(true);

            if (stepIndex + 1 < checkStepButtons.size()) {
                multipleFields.get(stepIndex + 1).forEach(f -> f.setDisable(false));
                subtractionFields.get(stepIndex + 1).forEach(f -> f.setDisable(false));
                checkStepButtons.get(stepIndex + 1).setDisable(false);
            }

            if (nextResultIndex < resultFields.size()) {
                resultFields.get(nextResultIndex).setDisable(false);
                while (nextResultIndex + 1 < resultFields.size() && resultStr.charAt(nextResultIndex + 1) == '0') {
                    nextResultIndex++;
                    resultFields.get(nextResultIndex).setDisable(false);
                }
                nextResultIndex++;
            }
        }
    }


    private boolean isAllCorrect(List<DigitField> multipleRow, boolean allCorrect, String correctMultiple) {
        for (int i = 0; i < multipleRow.size(); i++) {
            String expectedChar = i < correctMultiple.length() ? String.valueOf(correctMultiple.charAt(i)) : " ";
            String text = multipleRow.get(i).getText();
            if (text.equals(expectedChar)) {
                multipleRow.get(i).setStyle("-fx-background-color: #b2ffb2;");
                multipleRow.get(i).setDisable(true);
            } else {
                multipleRow.get(i).setStyle("-fx-background-color: #ffb2b2;");
                allCorrect = false;
            }
        }
        return allCorrect;
    }


    private Region spacer() {
        Region region = new Region();
        region.setMinWidth(30);
        return region;
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

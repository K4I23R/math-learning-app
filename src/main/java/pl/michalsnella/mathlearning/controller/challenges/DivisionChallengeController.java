package pl.michalsnella.mathlearning.controller.challenges;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import pl.michalsnella.mathlearning.component.DigitField;
import pl.michalsnella.mathlearning.model.DivisionExercise;
import pl.michalsnella.mathlearning.util.LanguageManager;
import pl.michalsnella.mathlearning.util.SceneManager;

import java.util.ArrayList;
import java.util.List;

public class DivisionChallengeController {

    @FXML private Label titleLabel;
    @FXML private HBox topAnswerBox;
    @FXML private HBox divisorBox;
    @FXML private HBox dividendBox;
    @FXML private Separator divisionLine;
    @FXML private Button checkButton, newOperationButton, backButton;

    private final List<DigitField> answerFields = new ArrayList<>();
    private DivisionExercise exercise;

    @FXML
    public void initialize() {
        titleLabel.setText(LanguageManager.getString("division_challenge.title"));
        checkButton.setText(LanguageManager.getString("division_challenge.check"));
        newOperationButton.setText(LanguageManager.getString("division_challenge.new_operation"));
        backButton.setText(LanguageManager.getString("division_challenge.back"));
        generateNewExercise();
    }

    private void generateNewExercise() {
        exercise = new DivisionExercise(4);

        answerFields.clear();
        topAnswerBox.getChildren().clear();
        divisorBox.getChildren().clear();
        dividendBox.getChildren().clear();

        String dividendStr = String.valueOf(exercise.getDividend());
        String divisorStr = String.valueOf(exercise.getDivisor());
        String resultStr = String.valueOf(exercise.getResult());

        int padding = dividendStr.length() - resultStr.length();
        for (int i = 0; i < padding; i++) {
            Region spacer = new Region();
            spacer.setMinWidth(30);
            topAnswerBox.getChildren().add(spacer);
        }

        for (int i = 0; i < resultStr.length(); i++) {
            DigitField field = new DigitField();
            field.setPrefWidth(30);
            field.setAlignment(Pos.CENTER);
            answerFields.add(field);
            topAnswerBox.getChildren().add(field);
        }

        divisionLine.setPrefWidth(30.0 * dividendStr.length());

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
    }

    @FXML
    private void onCheckClicked() {
        String userAnswer = answerFields.stream()
                .map(DigitField::getText)
                .reduce("", (a, b) -> a + (b.isBlank() ? " " : b));

        String correctAnswer = String.valueOf(exercise.getResult());
        String paddedCorrect = String.format("%" + userAnswer.length() + "s", correctAnswer);

        for (int i = 0; i < userAnswer.length(); i++) {
            if (userAnswer.charAt(i) == paddedCorrect.charAt(i)) {
                answerFields.get(i).setStyle("-fx-background-color: #b2ffb2;");
            } else {
                answerFields.get(i).setStyle("-fx-background-color: #ffb2b2;");
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

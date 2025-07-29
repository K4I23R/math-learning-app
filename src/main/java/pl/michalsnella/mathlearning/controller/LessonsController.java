package pl.michalsnella.mathlearning.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pl.michalsnella.mathlearning.util.LanguageManager;
import pl.michalsnella.mathlearning.util.SceneManager;

public class LessonsController {

    @FXML
    public Button backButton;

    @FXML
    private Label titleLabel;

    @FXML
    public void initialize() {
        backButton.setText(LanguageManager.getString("lessons.back"));

        titleLabel.setText(LanguageManager.getString("lessons.title"));
    }


    public void onBackClicked(ActionEvent actionEvent) {
        try {
            SceneManager.switchTo("/fxml/start.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

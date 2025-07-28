package pl.michalsnella.mathlearning.component;

import javafx.scene.control.TextField;

public class DigitField extends TextField {

    public DigitField() {
        setPrefWidth(30);

        textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d?")) {
                setText(oldText);
            }
        });
    }
}


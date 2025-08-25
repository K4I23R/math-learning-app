package pl.michalsnella.mathlearning.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.michalsnella.mathlearning.model.user.Avatar;
import pl.michalsnella.mathlearning.service.user.UserService;

import java.io.IOException;

public class NewProfileDialogController {

    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private ComboBox<Avatar> avatarBox;

    private UserService userService;

    @FXML
    public void initialize() {
        avatarBox.getItems().setAll(Avatar.values());
        avatarBox.getSelectionModel().select(Avatar.FOX);
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @FXML
    private void onCancel() {
        close();
    }


    @FXML
    private void onCreate() {
        String name = nameField.getText() != null ? nameField.getText().trim() : "";
        Integer age = null;
        if (!ageField.getText().isBlank()) {
            try {
                int a = Integer.parseInt(ageField.getText().trim());
                if (a >= 0 && a <= 18) age = a;
            } catch (NumberFormatException ignored) {}
        }
        Avatar avatar = avatarBox.getValue();

        if (name.isBlank()) {
            new Alert(Alert.AlertType.WARNING, "Podaj imię.", ButtonType.OK).showAndWait();
            return;
        }

        try {
            userService.createUser(name, age, avatar);
            close();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Nie udało się utworzyć profilu: " + e.getMessage(), ButtonType.OK).showAndWait();
        }
    }


    private void close() {
        Stage st = (Stage) nameField.getScene().getWindow();
        st.close();
    }
}


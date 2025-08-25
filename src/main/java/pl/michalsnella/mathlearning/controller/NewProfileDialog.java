package pl.michalsnella.mathlearning.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.stage.Stage;
import pl.michalsnella.mathlearning.service.user.UserService;

import java.io.IOException;

public class NewProfileDialog {
    public static void show(UserService userService, Window owner) throws IOException {
        FXMLLoader loader = new FXMLLoader(NewProfileDialog.class.getResource("/fxml/new_profile_dialog.fxml"));
        Parent root = loader.load();
        NewProfileDialogController ctrl = loader.getController();
        ctrl.setUserService(userService);

        Stage stage = new Stage();
        stage.setTitle("Nowy profil");
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}


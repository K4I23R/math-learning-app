package pl.michalsnella.mathlearning.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Stage stage;

    public static void setStage(Stage s) {
        stage = s;
    }

    public static void switchTo(String fxml) throws IOException {
        Parent root = FXMLLoader.load(SceneManager.class.getResource(fxml));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void start(Stage primaryStage) throws IOException {
        SceneManager.setStage(primaryStage);
        SceneManager.switchTo("/fxml/main_menu.fxml");
    }
}

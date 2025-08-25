package pl.michalsnella.mathlearning;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.michalsnella.mathlearning.config.Settings;
import pl.michalsnella.mathlearning.utils.LanguageManager;
import pl.michalsnella.mathlearning.utils.SceneManager;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        LanguageManager.setLanguage(Settings.getInstance().getLanguage());
        SceneManager.setStage(primaryStage);
        //SceneManager.switchTo("/fxml/main_menu.fxml");
        SceneManager.switchTo("/fxml/profile_selector.fxml");

    }


    public static void main(String[] args) {
        launch(args);
    }
}

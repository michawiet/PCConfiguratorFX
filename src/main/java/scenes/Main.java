package scenes;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class Main extends Application {
    public static void main(String[] args) throws JsonProcessingException {
        launch(args);
    }

    private static Scene primaryScene;

    public static Scene getPrimaryScene() {
        return primaryScene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainSceneController.fxml"));
        primaryScene = new Scene(root, 1280, 800);
        primaryScene.setRoot(root);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
}

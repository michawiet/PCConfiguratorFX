import com.fasterxml.jackson.core.JsonProcessingException;
import helpers.SceneHubSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import scenes.SelectedPartsSummaryController;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("BasicSceneController.fxml"));
        Scene primaryScene = new Scene(root, 1280, 800);
        primaryScene.setRoot(root);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
        primaryStage.setResizable(false);

        primaryStage.getIcons().setAll(new Image(getClass().getResourceAsStream("/images/icons/main_program.png")));
        SceneHubSingleton.getInstance().setPrimaryStage(primaryStage)
            .setTitle(SelectedPartsSummaryController.getTitle());
    }
}

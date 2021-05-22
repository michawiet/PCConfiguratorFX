package scenes;

import helpers.SceneHubSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

//TODO: rename it (to like scene skeleton) so it makes sense
public class BasicSceneController {

    @FXML
    private AnchorPane sceneContentPane;

    @FXML
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("SelectedPartsSummaryController.fxml"));
        Parent root = loader.load();
        SelectedPartsSummaryController controller = loader.getController();
        sceneContentPane.getChildren().setAll(root);
        SceneHubSingleton.getInstance()
                .setBasicSceneController(this)
                .setSelectedPartsSummaryController(controller)
                .setSelectedPartsSummaryRoot(root);
    }

    private void loadHelpController(String helpHtmlFileName, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HelpSceneController.fxml"));
        Parent root = loader.load();
        HelpSceneController controller = loader.getController();
        controller.load(helpHtmlFileName);
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons/help.png")));
        stage.setResizable(false);
        stage.setTitle("PC Configurator - " + title);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    public void exitAction(ActionEvent actionEvent) {
    }

    public void howToAction(ActionEvent actionEvent) throws IOException {
        loadHelpController("/help/help.html", "How to use");
    }

    public void faqAction(ActionEvent actionEvent) throws IOException {
        loadHelpController("/help/index.html", "FAQ & Terminology");
    }

    public void aboutAction(ActionEvent actionEvent) throws IOException {
        //loadHelpController("/help/index.html");
    }

    public void switchToScene(Parent newScene) {
        if(sceneContentPane != null) {
            if(sceneContentPane.getChildren() != null) {
                //sceneContentPane.getChildren().clear();
                sceneContentPane.getChildren().setAll(newScene);
            }
        }
    }
}

package helpers;

import components.Product;
import javafx.scene.Parent;
import javafx.stage.Stage;
import scenes.BasicSceneController;
import scenes.SelectedPartsSummaryController;

public class SceneHubSingleton {

    private static SceneHubSingleton singleton;

    public static SceneHubSingleton getInstance() {
        if(singleton == null) {
            singleton = new SceneHubSingleton();
        }

        return singleton;
    }

    private Stage primaryStage;
    private BasicSceneController basicSceneController;
    private SelectedPartsSummaryController selectedPartsSummaryController;
    private Parent selectedPartsSummaryRoot;

    private SceneHubSingleton() {

    }

    public SceneHubSingleton setSelectedPartsSummaryRoot(Parent root) {
        this.selectedPartsSummaryRoot = root;
        return this;
    }

    public SceneHubSingleton setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        return this;
    }

    public SceneHubSingleton setTitle(String title) {
        this.primaryStage.setTitle("PC Configurator - " + title);
        return this;
    }

    public SceneHubSingleton setBasicSceneController(BasicSceneController basicSceneController) {
        this.basicSceneController = basicSceneController;
        return this;
    }

    public SceneHubSingleton setSelectedPartsSummaryController(SelectedPartsSummaryController selectedPartsSummaryController) {
        this.selectedPartsSummaryController = selectedPartsSummaryController;
        return this;
    }

    public SceneHubSingleton addSelectedProductAndSwitch(Product product) {
        selectedPartsSummaryController.addSelectedProduct(product);
        try {
            basicSceneController.switchToScene(selectedPartsSummaryRoot);
            this.setTitle(SelectedPartsSummaryController.getTitle());
        } catch (NullPointerException e) {
            System.err.println("selectedPartsSummaryRoot was most likely not set");
        }
        return this;
    }

    public SceneHubSingleton switchToScene(Parent root) {
        basicSceneController.switchToScene(root);
        return this;
    }
}

package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class HelpSceneController {

    @FXML
    private MenuItem howToUseOnAction;

    @FXML
    private MenuItem terminologiesOnAction;

    @FXML
    private MenuItem aboutOnAction;

    @FXML
    private Button goBackAction;

    @FXML
    private WebView cpuWebView;


    @FXML
    void goBackAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        cpuWebView.contextMenuEnabledProperty().setValue(false);
        cpuWebView.getEngine().load(getClass().getResource("/help/index.html").toExternalForm());
    }

}

package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class HelpSceneController {
    @FXML
    private WebView webView;

    public void load(String helpFileName) {
        webView.getEngine().load(getClass().getResource(helpFileName).toExternalForm());
        //webView.getEngine().load(getClass().getResource("/help/index.html").toExternalForm());
    }

    @FXML
    void initialize() {
        webView.contextMenuEnabledProperty().setValue(false);
        webView.fontSmoothingTypeProperty().setValue(FontSmoothingType.GRAY);
        webView.setFontScale(1.15);
        //webView.getEngine().load(getClass().getResource("/help/index.html").toExternalForm());
    }

}

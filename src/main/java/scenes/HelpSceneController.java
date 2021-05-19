package scenes;

import javafx.fxml.FXML;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.WebView;

public class HelpSceneController {
    @FXML
    private WebView webView;

    public void load(String helpFileName) {
        webView.getEngine().load(getClass().getResource(helpFileName).toExternalForm());
    }

    @FXML
    void initialize() {
        webView.contextMenuEnabledProperty().setValue(false);
        webView.fontSmoothingTypeProperty().setValue(FontSmoothingType.GRAY);
    }

}

package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainSceneController {

    @FXML
    private Button cpuSelectButton;

    @FXML
    private Button coolerSelectButton;

    @FXML
    private Button motherboardSelectButton;

    @FXML
    private Button ramSelectButton;

    @FXML
    private Button storageSelectButton;

    @FXML
    private Button gpuSelectButton;

    @FXML
    private Button caseSelectButton;

    @FXML
    private Button psuSelectButton;

    @FXML
    void loadCaseSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HelpSceneController.fxml"));
        Main.getPrimaryScene().setRoot(root);
    }

    @FXML
    void loadCoolerSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("CoolerSceneController.fxml"));
        Main.getPrimaryScene().setRoot(root);
    }

    @FXML
    void loadCpuSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("CpuSceneController.fxml"));
        Main.getPrimaryScene().setRoot(root);
    }

    @FXML
    void loadGpuSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GpuSceneController.fxml"));
        Main.getPrimaryScene().setRoot(root);
    }

    @FXML
    void loadMotherboardSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MotherboardSceneController.fxml"));
        Main.getPrimaryScene().setRoot(root);
    }

    @FXML
    void loadPsuSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("PsuSceneController.fxml"));
        Main.getPrimaryScene().setRoot(root);
    }

    @FXML
    void loadRamSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("RamSceneController.fxml"));
        Main.getPrimaryScene().setRoot(root);
    }

    @FXML
    void loadStorageSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("StorageSceneController.fxml"));
        Main.getPrimaryScene().setRoot(root);
    }

}

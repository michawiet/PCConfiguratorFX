import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) throws JsonProcessingException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("CpuSceneController.fxml"));

        //CpuScene page = new CpuScene();

        //TableView cpuTable = new TableView();
        //cpuTable.getColumns().addAll(Cpu.getColumns());
        //DatabaseData data = new DatabaseData();
        //ObservableList<Cpu> observableList = FXCollections.observableList(data.getCpuList());
        //FilteredList<Cpu> filteredList = new FilteredList<>(observableList, cpu -> cpu.getIgpu() != "No");
        //cpuTable.getItems().addAll(filteredList);
        ////TableFilter filter = new TableFilter(cpuTable);
//
        //VBox vBox = new VBox(cpuTable, new ScrollPane());

        Scene scene = new Scene(root, 1280, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

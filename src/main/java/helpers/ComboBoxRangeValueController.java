package helpers;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;

import java.util.List;

public class ComboBoxRangeValueController {
    private boolean minUpdated = false;
    private boolean maxUpdated = false;

    private FilteredList<Integer> minList;
    private FilteredList<Integer> maxList;

    private ComboBox<Integer> minComboBox;
    private ComboBox<Integer> maxComboBox;

    public ComboBoxRangeValueController(List<Integer> valueList, ComboBox<Integer> minComboBox, ComboBox<Integer> maxComboBox) {
        this.minComboBox = minComboBox;
        this.maxComboBox = maxComboBox;

        this.minList = new FilteredList<>(FXCollections.observableList(valueList));
        this.maxList = new FilteredList<>(FXCollections.observableList(valueList));

        minComboBox.setItems(minList);
        maxComboBox.setItems(maxList);

        this.minComboBox.setValue(valueList.stream().min(Integer::compareTo).get());
        this.maxComboBox.setValue(valueList.stream().max(Integer::compareTo).get());
    }

    public void minComboBoxUpdated() {
        if(!maxUpdated) {
            minUpdated = true;
            var maxVal = maxComboBox.getValue();
            var minVal = minComboBox.getValue();
            maxList.setPredicate((val) -> val >= minVal);
            maxComboBox.setValue(maxVal);
            minUpdated = false;
        }
    }

    public void maxComboBoxUpdated() {
        if(!minUpdated) {
            maxUpdated = true;
            var maxVal = maxComboBox.getValue();
            var minVal = minComboBox.getValue();
            minList.setPredicate((val) -> val <= maxVal);
            minComboBox.setValue(minVal);
            maxUpdated = false;
        }
    }
}

package helpers;

import javafx.scene.control.CheckBoxTreeItem;

import java.util.List;

public class CheckBoxRoot {

    CheckBoxTreeItem<String> root;

    public CheckBoxRoot(List<String> distinctValues) {
        this.root = new CheckBoxTreeItem<>("All");
        root.setExpanded(true);

        for(var val : distinctValues) {
            this.root.getChildren().add(new CheckBoxTreeItem(val));
        }

        this.root.setSelected(true);
    }

    public CheckBoxTreeItem<String> getRoot() {
        return root;
    }
}

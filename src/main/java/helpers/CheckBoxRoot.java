package helpers;

import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxRoot {

    CheckBoxTreeItem<String> root;
    //TreeView tree;

    public CheckBoxRoot(List<String> distinctValues) {
        this.root = new CheckBoxTreeItem<>("All");
        root.setExpanded(true);
        //this.tree = new TreeView(this.root);
        //this.tree.setEditable(false);
        //this.tree.setCellFactory(CheckBoxTreeCell.forTreeView());
        //this.children = new ArrayList<>();

        for(var val : distinctValues) {
            //this.children.add(new CheckBoxTreeItem(val));
            this.root.getChildren().add(new CheckBoxTreeItem(val));
        }

        this.root.setSelected(true);
        //this.tree.setShowRoot(true);
        //this.tree.setMaxSize(400, distinctValues.size() > 2 ? 117 : 71);
    }

    public CheckBoxTreeItem<String> getRoot() {
        return root;
    }
}

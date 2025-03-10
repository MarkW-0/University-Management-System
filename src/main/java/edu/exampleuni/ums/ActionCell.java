package edu.exampleuni.ums;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

public class ActionCell<S> extends TableCell<S,Void> {
    Button editBtn = new Button("Edit");
    Button deleteBtn = new Button("Delete");
    HBox actionButtons = new HBox(5, this.editBtn, this.deleteBtn);

    {
        this.editBtn.getStyleClass().add("editButton");
        this.deleteBtn.getStyleClass().add("deleteButton");
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            this.setGraphic(null);
        } else {
            this.setGraphic(this.actionButtons);
        }
    }
}
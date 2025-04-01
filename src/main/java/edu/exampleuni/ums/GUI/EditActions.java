package edu.exampleuni.ums.GUI;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class EditActions<S> extends TableCell<S, Void> {
	protected Button editBtn = new Button("Edit");
	protected Button deleteBtn = new Button("Delete");
	protected HBox actionButtons = new HBox(5, this.editBtn, this.deleteBtn);

	EditActions() {
		super();
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
package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class EventManagement extends VBox {
	@FXML public TableView<Event> table;
	@FXML public Button addButton;

	public EventManagement(MainApp mainApp) {
		FXMLLoader fxmlLoader = new FXMLLoader(EventManagement.class.getResource("EventManagement.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (mainApp.user.getRole() != Role.ADMIN) {
			this.addButton.setDisable(true);
		}
		// Add action column if ADMIN
		if (mainApp.user.getRole() == Role.ADMIN) {
			TableColumn<Event, Void> actionCol = new TableColumn<>("Actions");
			actionCol.setPrefWidth(150);
			actionCol.setCellFactory(param -> new EventEditActions(mainApp));
			this.table.getColumns().add(actionCol);
		}
		// Add Event button functionality
		this.addButton.setOnAction(e -> {
			Dialog<Event> dialog = EventEditActions.createEditDialog(null);
			dialog.showAndWait().ifPresent(newEvent -> {
				mainApp.eventService.add(newEvent);
				this.table.getItems().add(newEvent);
			});
		});

		// Add data
		this.table.getItems().addAll(mainApp.eventService.getAll());
	}
}
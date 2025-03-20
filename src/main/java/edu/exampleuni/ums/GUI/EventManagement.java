package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.Event;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class EventManagement extends VBox {
	@FXML public TableView<Event> eventTable;
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
		if (!mainApp.user.getRole().equals("ADMIN")) {
			this.addButton.setDisable(true);
		}
		// todo createEventEditDialog
		// Add action column if ADMIN
		//if (this.mainApp.user.getRole().equals("ADMIN")) {
		//	TableColumn<Event, Void> actionCol = new TableColumn<>("Actions");
		//	actionCol.setPrefWidth(150);
		//	actionCol.setCellFactory(param -> new EventManagementAdminActionCell(this.mainApp));
		//	content.eventTable.getColumns().add(actionCol);
		//}
		// Add Event button functionality
		//content.addButton.setOnAction(e -> {
		//	Dialog<Event> dialog = MainApp.createEventEditDialog(null);
		//	dialog.showAndWait().ifPresent(newEvent -> {
		//		this.mainApp.eventService.addEvent(newEvent);
		//		content.eventTable.getItems().add(newEvent);
		//	});
		//});

		// Add data
		this.eventTable.getItems().addAll(mainApp.eventService.getAllEvents());
	}
}
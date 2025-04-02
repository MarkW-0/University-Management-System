package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.Event;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class EventEditActions extends EditActions<Event> {
	EventEditActions(MainApp mainApp) {
		super();
		this.editBtn.setOnAction(_event -> {
			Event event = this.getTableView().getItems().get(getIndex());
			Dialog<Event> dialog = EventEditActions.createEditDialog(event);
			dialog.showAndWait().ifPresent(editedEvent -> {
				mainApp.eventService.update(editedEvent);
				this.getTableView().refresh();
			});
		});

		this.deleteBtn.setOnAction(_event -> {
			Event event = this.getTableView().getItems().get(getIndex());
			// Show confirmation dialog
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Delete Event");
			alert.setHeaderText("Delete Event: " + event.getEventName());
			alert.setContentText("Are you sure you want to delete this event?");

			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					mainApp.eventService.delete(event);
					this.getTableView().getItems().remove(getIndex());
				}
			});
		});
	}
	public static Dialog<Event> createEditDialog(Event existingEvent) { // todo move to own class?
		Dialog<Event> dialog = new Dialog<>();
		dialog.setTitle(existingEvent == null ? "Add Event" : "Edit Event");

		ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField codeField = new TextField();			codeField.setPromptText("Event Code");
		TextField nameField = new TextField();			nameField.setPromptText("Event Name");
		TextField capacityField = new TextField();		capacityField.setPromptText("Capacity");
		TextField costField = new TextField();			costField.setPromptText("Cost");
		TextField descriptionField = new TextField();	descriptionField.setPromptText("Description");

		// Pre-fill fields if editing an existing event
		if (existingEvent != null) {
			codeField.setText(existingEvent.getCode());
			nameField.setText(existingEvent.getEventName());
			capacityField.setText(existingEvent.getCapacity());
			costField.setText(existingEvent.getCost());
			descriptionField.setText(existingEvent.getDescription());
		} else {
			// Default values for new event
			capacityField.setText("30");
			costField.setText("Free");
		}

		grid.add(new Label("Event Code:"), 0, 0);	grid.add(codeField, 1, 0);
		grid.add(new Label("Event Name:"), 0, 1);	grid.add(nameField, 1, 1);
		grid.add(new Label("Capacity:"), 0, 2);		grid.add(capacityField, 1, 2);
		grid.add(new Label("Cost:"), 0, 3);			grid.add(costField, 1, 3);
		grid.add(new Label("Description:"), 0, 4);	grid.add(descriptionField, 1, 4);

		dialog.getDialogPane().setContent(grid);

		// Convert the result to a Event when the save button is clicked
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == saveButtonType) {
				String code = codeField.getText().trim();
				String name = nameField.getText().trim();
				String capacity = capacityField.getText().trim();
				String cost = costField.getText().trim();
				String description = descriptionField.getText().trim();

				// Validate input
				if (code.isEmpty() || name.isEmpty() || capacity.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Invalid Input");
					alert.setHeaderText(null);
					alert.setContentText("Event Code, Name and Capacity cannot be empty!");
					alert.showAndWait();
					return null;
				}

				// If editing existing event, update values; otherwise create new Event
				Event output = existingEvent != null ? existingEvent : new Event();
				output.setCode(code);
				output.setEventName(name);
				output.setCapacity(capacity);
				output.setCost(cost);
				output.setDescription(description);
				return output;
			}
			return null;
		});

		return dialog;
	}

}
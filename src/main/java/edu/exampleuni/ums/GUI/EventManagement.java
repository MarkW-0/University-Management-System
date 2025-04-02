package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.*;

public class EventManagement extends Management<Event> {
	public EventManagement(MainApp mainApp) {
		super(mainApp, mainApp.eventService, EventEditActions::new, EventEditActions::createEditDialog, "EventManagement.fxml");
	}
}
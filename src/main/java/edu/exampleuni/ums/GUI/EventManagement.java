package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.models.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class EventManagement extends VBox {
	@FXML public TableView<Event> eventTable;
	@FXML public Button addButton;

	public EventManagement() {
		FXMLLoader fxmlLoader = new FXMLLoader(EventManagement.class.getResource("EventManagement.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
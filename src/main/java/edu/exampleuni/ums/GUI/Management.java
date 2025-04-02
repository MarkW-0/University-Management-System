package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.*;
import edu.exampleuni.ums.services.Service;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.function.Function;

public class Management<T extends Model> extends VBox {
	@FXML public TableView<T> table;
	@FXML public Button addButton;

	public Management(MainApp mainApp, Service<T> service, Function<MainApp, EditActions<T>> EditActions, Function<T, Dialog<T>> createEditDialog, String filePath) {
		FXMLLoader fxmlLoader = new FXMLLoader(Management.class.getResource(filePath));
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
			TableColumn<T, Void> actionCol = new TableColumn<>("Actions");
			actionCol.setPrefWidth(150);
			actionCol.setCellFactory(param -> EditActions.apply(mainApp));
			this.table.getColumns().add(actionCol);
		}
		// Add button functionality
		this.addButton.setOnAction(e -> {
			Dialog<T> dialog = createEditDialog.apply(null);
			dialog.showAndWait().ifPresent(newThing -> {
				service.add(newThing);
				this.table.getItems().add(newThing);
			});
		});

		// Add data
		this.table.getItems().addAll(service.getAll());
	}
}
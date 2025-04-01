package edu.exampleuni.ums.GUI;

import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

public class MenuItem extends VBox {
	@FXML private Label name;

	public MenuItem(String name, StackPane contentPane, Node content) {
		FXMLLoader fxmlLoader = new FXMLLoader(MenuItem.class.getResource("MenuItem.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.name.setText(name);
		// Click action
		this.setOnMouseClicked(e -> contentPane.getChildren().setAll(content));
	}
}
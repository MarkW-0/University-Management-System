package edu.exampleuni.ums.utils;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class NavigationManager {
	private final StackPane contentPane;

	public NavigationManager(StackPane contentPane) {
		this.contentPane = contentPane;
	}

	public void navigateTo(Node content) {
		contentPane.getChildren().clear();
		contentPane.getChildren().add(content);
	}
}
package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

public class Header extends HBox {
	@FXML private Label userLabel;
	@FXML public Button logoutButton;

	public Header(MainApp mainApp) {
		FXMLLoader fxmlLoader = new FXMLLoader(Header.class.getResource("Header.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.userLabel.setText("Logged in as: " + mainApp.user.getFullName());
		this.logoutButton.setOnAction(e -> mainApp._setScene(new LoginScreen(mainApp), 800, 600));
	}

}
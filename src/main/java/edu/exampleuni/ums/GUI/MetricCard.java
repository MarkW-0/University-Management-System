package edu.exampleuni.ums.GUI;

import java.io.IOException;

import javafx.beans.property.*;
import javafx.fxml.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;

public class MetricCard extends VBox {
	@FXML private Label title;
	@FXML private Label value;

	public MetricCard() {
		FXMLLoader fxmlLoader = new FXMLLoader(MetricCard.class.getResource("MetricCard.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public String getTitle() {
		return this.titleProperty().get();
	}

	public void setTitle(String value) {
		this.titleProperty().set(value);
	}

	public StringProperty titleProperty() {
		return this.title.textProperty();
	}
	public String getValue() {
		return this.valueProperty().get();
	}

	public void setValue(String value) {
		this.valueProperty().set(value);
	}

	public StringProperty valueProperty() {
		return this.value.textProperty();
	}

	public Paint getColor() {
		return this.colorProperty().get();
	}

	public void setColor(Paint value) {
		this.colorProperty().set(value);
	}

	public ObjectProperty<Paint> colorProperty() {
		return this.value.textFillProperty();
	}
}
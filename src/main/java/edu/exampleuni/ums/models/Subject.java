package edu.exampleuni.ums.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Subject model with matching getters for code & name
 * to satisfy PropertyValueFactory("code") and ("name").
 */
public class Subject {
	private final StringProperty code;
	private final StringProperty name;

	public Subject(String code, String name) {
		this.code = new SimpleStringProperty(code);
		this.name = new SimpleStringProperty(name);
	}

	// -- JavaFX Properties (optional if you use them elsewhere) --
	public StringProperty codeProperty() { return code; }
	public StringProperty nameProperty() { return name; }

	// -- Getters that PropertyValueFactory("code"/"name") looks for --
	public String getCode() { return code.get(); }
	public String getName() { return name.get(); }

	// -- Setters if you want to modify code/name later --
	public void setCode(String code) { this.code.set(code); }
	public void setName(String name) { this.name.set(name); }
}

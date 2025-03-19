package edu.exampleuni.ums.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subject {
	private final StringProperty code;
	private final StringProperty name;

	public Subject(String code, String name) {
		this.code = new SimpleStringProperty(code);
		this.name = new SimpleStringProperty(name);
	}

	// Getters for properties
	public StringProperty codeProperty() { return code; }
	public StringProperty nameProperty() { return name; }

	// Getters and Setters
	public String getCode() { return code.get(); }
	public void setCode(String code) { this.code.set(code); }

	public String getName() { return name.get(); }
	public void setName(String name) { this.name.set(name); }
}
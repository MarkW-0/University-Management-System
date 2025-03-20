package edu.exampleuni.ums.models;

import javafx.beans.property.*;

public class Subject {
	private final StringProperty code;
	private final StringProperty subjectName;

	public Subject(String code, String subjectName) {
		this.code = new SimpleStringProperty(code);
		this.subjectName = new SimpleStringProperty(subjectName);
	}
	public String getCode() { return code.get(); } public void setCode(String value) { code.set(value); }
	public String getSubjectName() { return subjectName.get(); } public void setSubjectName(String value) { subjectName.set(value); }
}
package edu.exampleuni.ums.models;

import javafx.beans.property.*;
import org.apache.poi.ss.usermodel.Row;

public class Subject {
	private final StringProperty code = new SimpleStringProperty();
	private final StringProperty subjectName = new SimpleStringProperty();

	public Subject() {}

	public Subject(Row row) {
		this.code.set(row.getCell(0).getStringCellValue());
		this.subjectName.set(row.getCell(1).getStringCellValue());
	}

	public String getCode() { return code.get(); } public void setCode(String value) { code.set(value); }
	public String getSubjectName() { return subjectName.get(); } public void setSubjectName(String value) { subjectName.set(value); }
}
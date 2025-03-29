package edu.exampleuni.ums.models;

import javafx.beans.property.*;
import org.apache.poi.ss.usermodel.*;

public class Course extends Model {
	private final StringProperty code = new SimpleStringProperty("");
	private final StringProperty courseName = new SimpleStringProperty("");
	private final StringProperty subject = new SimpleStringProperty("");
	private final StringProperty section = new SimpleStringProperty("");
	private final StringProperty capacity = new SimpleStringProperty("");
	// todo Course Lecture Time
	// todo Course Final Exam Date/Time
	private final StringProperty location = new SimpleStringProperty("");
	private final StringProperty teacher = new SimpleStringProperty("");

	public Course() {}

	@Override
	public boolean isEqual(Model updated) {
		if (!(updated instanceof Course updatedCourse)) return false;
		return this.getCode().equals(updatedCourse.getCode()) && this.getSection().equals(updatedCourse.getSection());
	}

	public Course(Row row) {
		this.code.set(String.valueOf(row.getCell(0).getNumericCellValue()));
		this.courseName.set(row.getCell(1).getStringCellValue());
		this.subject.set(row.getCell(2).getStringCellValue());
		this.section.set(row.getCell(3).getStringCellValue());
		this.capacity.set(String.valueOf(row.getCell(4).getNumericCellValue()));
		//	String	Lecture Time
		//	Date	Exam Date/Time
		this.location.set(row.getCell(7).getStringCellValue());
		this.teacher.set(row.getCell(8).getStringCellValue());
	}



	public String getCode() { return code.get(); } public void setCode(String value) { code.set(value); }
	public String getCourseName() { return courseName.get(); } public void setCourseName(String value) { courseName.set(value); }
	public String getSubject() { return subject.get(); } public void setSubject(String value) { subject.set(value); }
	public String getSection() { return section.get(); } public void setSection(String value) { section.set(value); }
	public String getCapacity() { return capacity.get(); } public void setCapacity(String value) { capacity.set(value); }
	//	Lecture Time getters/setters
	//	Exam Date/Time getters/setters
	public String getLocation() { return location.get(); } public void setLocation(String value) { location.set(value); }
	public String getTeacher() { return teacher.get(); } public void setTeacher(String value) { teacher.set(value); }
}
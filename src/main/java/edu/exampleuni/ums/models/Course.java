package edu.exampleuni.ums.models;

public class Course {
	final javafx.beans.property.StringProperty code;
	final javafx.beans.property.StringProperty courseName;
	final javafx.beans.property.StringProperty subject;
	final javafx.beans.property.StringProperty section;
	final javafx.beans.property.StringProperty teacher;
	final javafx.beans.property.StringProperty capacity;
	final javafx.beans.property.StringProperty location;

	public Course() {
		this("", "", "", "", "", "", "");
	}
	public Course(String code, String courseName, String subject, String section,
				  String teacher, String capacity, String location) {
		this.code = new javafx.beans.property.SimpleStringProperty(code);
		this.courseName = new javafx.beans.property.SimpleStringProperty(courseName);
		this.subject = new javafx.beans.property.SimpleStringProperty(subject);
		this.section = new javafx.beans.property.SimpleStringProperty(section);
		this.teacher = new javafx.beans.property.SimpleStringProperty(teacher);
		this.capacity = new javafx.beans.property.SimpleStringProperty(capacity);
		this.location = new javafx.beans.property.SimpleStringProperty(location);
	}
	public String getCode() { return code.get(); } public void setCode(String value) { code.set(value); }
	public String getCourseName() { return courseName.get(); } public void setCourseName(String value) { courseName.set(value); }
	public String getSubject() { return subject.get(); } public void setSubject(String value) { subject.set(value); }
	public String getSection() { return section.get(); } public void setSection(String value) { section.set(value); }
	public String getTeacher() { return teacher.get(); } public void setTeacher(String value) { teacher.set(value); }
	public String getCapacity() { return capacity.get(); } public void setCapacity(String value) { capacity.set(value); }
	public String getLocation() { return location.get(); } public void setLocation(String value) { location.set(value); }
}
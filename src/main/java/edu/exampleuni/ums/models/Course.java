package edu.exampleuni.ums.models;

import javafx.beans.property.*;

public class Course {
	private final StringProperty code;
	private final StringProperty courseName;
	private final StringProperty subject;
	private final StringProperty section;
	private final StringProperty teacher;
	private final StringProperty capacity;
	private final StringProperty location;
	/*	todo
		Lecture Times
		Final Exam Date/Time
	*/
	public Course() {
		this("", "", "", "", "", "", "");
	}
	public Course(String code, String courseName, String subject, String section,
				  String teacher, String capacity, String location) {
		this.code = new SimpleStringProperty(code);
		this.courseName = new SimpleStringProperty(courseName);
		this.subject = new SimpleStringProperty(subject);
		this.section = new SimpleStringProperty(section);
		this.teacher = new SimpleStringProperty(teacher);
		this.capacity = new SimpleStringProperty(capacity);
		this.location = new SimpleStringProperty(location);
	}
	public String getCode() { return code.get(); } public void setCode(String value) { code.set(value); }
	public String getCourseName() { return courseName.get(); } public void setCourseName(String value) { courseName.set(value); }
	public String getSubject() { return subject.get(); } public void setSubject(String value) { subject.set(value); }
	public String getSection() { return section.get(); } public void setSection(String value) { section.set(value); }
	public String getTeacher() { return teacher.get(); } public void setTeacher(String value) { teacher.set(value); }
	public String getCapacity() { return capacity.get(); } public void setCapacity(String value) { capacity.set(value); }
	public String getLocation() { return location.get(); } public void setLocation(String value) { location.set(value); }
}
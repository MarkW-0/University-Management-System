package edu.exampleuni.ums.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
	private final StringProperty code;
	private final StringProperty name;
	private final StringProperty subject;
	private final StringProperty section;
	private final StringProperty teacher;
	private final StringProperty capacity;
	private final StringProperty location;

	public Course(String code, String name, String subject, String section,
				  String teacher, String capacity, String location) {
		this.code = new SimpleStringProperty(code);
		this.name = new SimpleStringProperty(name);
		this.subject = new SimpleStringProperty(subject);
		this.section = new SimpleStringProperty(section);
		this.teacher = new SimpleStringProperty(teacher);
		this.capacity = new SimpleStringProperty(capacity);
		this.location = new SimpleStringProperty(location);
	}

	// Getters for properties
	public StringProperty codeProperty() { return code; }
	public StringProperty nameProperty() { return name; }
	public StringProperty subjectProperty() { return subject; }
	public StringProperty sectionProperty() { return section; }
	public StringProperty teacherProperty() { return teacher; }
	public StringProperty capacityProperty() { return capacity; }
	public StringProperty locationProperty() { return location; }

	// Standard getters
	public String getCode() { return code.get(); }
	public String getName() { return name.get(); }
	public String getSubject() { return subject.get(); }
	public String getSection() { return section.get(); }
	public String getTeacher() { return teacher.get(); }
	public String getCapacity() { return capacity.get(); }
	public String getLocation() { return location.get(); }

	// Standard setters
	public void setCode(String code) { this.code.set(code); }
	public void setName(String name) { this.name.set(name); }
	public void setSubject(String subject) { this.subject.set(subject); }
	public void setSection(String section) { this.section.set(section); }
	public void setTeacher(String teacher) { this.teacher.set(teacher); }
	public void setCapacity(String capacity) { this.capacity.set(capacity); }
	public void setLocation(String location) { this.location.set(location); }
}
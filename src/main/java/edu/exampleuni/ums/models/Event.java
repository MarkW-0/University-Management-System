package edu.exampleuni.ums.models;

import javafx.beans.property.*;
import org.apache.poi.ss.usermodel.Row;

public class Event extends Model {
	private final StringProperty code = new SimpleStringProperty("");
	private final StringProperty eventName = new SimpleStringProperty("");
	private final StringProperty description = new SimpleStringProperty("");
	// todo Event location
	// todo Event time
	private final StringProperty capacity = new SimpleStringProperty(""); // todo convert to int?
	private final StringProperty cost = new SimpleStringProperty(""); // todo convert to int?
	// todo Event headerImage;
	// todo Event attendees;

	public Event() {}

	public Event(Row row) {
		this.code.set(row.getCell(0).getStringCellValue());
		this.eventName.set(row.getCell(1).getStringCellValue());
		this.description.set(row.getCell(2).getStringCellValue());
		//	String	location
		//	Date	Exam Date/Time
		this.capacity.set(String.valueOf(row.getCell(5).getNumericCellValue()));
		this.cost.set(row.getCell(6).getStringCellValue());
		//	headerImage
		//	attendees
	}

	@Override
	public boolean isEqual(Model updated) {
		if (!(updated instanceof Event updatedEvent)) return false;
		return this.getCode().equals(updatedEvent.getCode());
	}

	// Getters and Setters
	public String getCode() { return code.get(); } public void setCode(String code) { this.code.set(code); }
	public String getEventName() { return eventName.get(); } public void setEventName(String eventName) { this.eventName.set(eventName); }
	public String getCapacity() { return capacity.get(); } public void setCapacity(String capacity) { this.capacity.set(capacity); }
	public String getCost() { return cost.get(); } public void setCost(String cost) { this.cost.set(cost); }
	public String getDescription() { return description.get(); } public void setDescription(String description) { this.description.set(description); }
}
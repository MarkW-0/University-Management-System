package edu.exampleuni.ums.models;

import javafx.beans.property.*;

import java.util.List;

public class Event {
	private final StringProperty code;
	private final StringProperty eventName;
	private final StringProperty capacity; // todo convert to int?
	private final StringProperty cost; // todo convert to int?
	private final StringProperty description; // todo convert to int?
	/*	todo
		Image headerImage;
		location;
		time;
	*/
	private List<UserAuth> attendees;

	public Event(String code, String eventName, String capacity, String cost, String description) {
		this.code = new SimpleStringProperty(code);
		this.eventName = new SimpleStringProperty(eventName);
		this.capacity = new SimpleStringProperty(capacity);
		this.cost = new SimpleStringProperty(cost);
		this.description = new SimpleStringProperty(description);
	}

	// Getters and Setters
	public String getCode() { return code.get(); } public void setCode(String code) { this.code.set(code); }
	public String getEventName() { return eventName.get(); } public void setEventName(String eventName) { this.eventName.set(eventName); }
	public String getCapacity() { return capacity.get(); } public void setCapacity(String capacity) { this.capacity.set(capacity); }
	public String getCost() { return cost.get(); } public void setCost(String cost) { this.cost.set(cost); }
	public String getDescription() { return description.get(); } public void setDescription(String description) { this.description.set(description); }
}
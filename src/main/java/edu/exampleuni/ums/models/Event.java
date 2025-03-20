package edu.exampleuni.ums.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class Event {
	private final StringProperty code;
	private int capacity; // todo
	private int cost; // todo
	private final StringProperty eventName;
	private String description; // todo
	/*	todo
		Image headerImage;
		location;
		time;
	*/
	private List<User> attendees;

	public Event(String code, int capacity, int cost, String eventName, String description) {
		this.code = new SimpleStringProperty(code);
		this.capacity = capacity;
		this.cost = cost;
		this.eventName = new SimpleStringProperty(eventName);
		this.description = description;
	}

	// Getters and Setters
	public String getCode() { return code.get(); } public void setCode(String code) { this.code.set(code); }
	public int getCapacity() { return capacity; } public void setCapacity(int capacity) { this.capacity = capacity; }
	public int getCost() { return cost; } public void setCost(int cost) { this.cost = cost; }
	public String getEventName() { return eventName.get(); } public void setEventName(String eventName) { this.eventName.set(eventName); }
	public String getDescription() { return description; } public void setDescription(String description) { this.description = description; }
}
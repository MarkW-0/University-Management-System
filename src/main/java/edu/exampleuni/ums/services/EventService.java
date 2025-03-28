package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.Event;
import java.util.*;

public class EventService {
	private final List<Event> events;

	public EventService(ExcelService excelService) {
		String[] expectedFirstRow = {"Event Code", "Event Name", "Description", "Location", "Date and Time", "Capacity", "Cost", "Header Image", "Registered Students"};
		this.events = excelService.getData(excelService.events, expectedFirstRow, Event::new);
	}

	public List<Event> getAllEvents() { return new ArrayList<>(events); }
	public void addEvent(Event event) { events.add(event); }
	public void deleteEvent(Event event) { events.remove(event); }

	// Method to update an course
	public void updateEvent(Event updatedEvent) {
		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			if (event.getCode().equals(updatedEvent.getCode())) {
				events.set(i, updatedEvent);
				break;
			}
		}
	}
}
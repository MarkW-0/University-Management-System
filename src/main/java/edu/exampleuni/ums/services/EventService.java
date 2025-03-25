package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventService {
	private final List<Event> events = new ArrayList<>(Arrays.asList(
		new Event("FO101",  "Freshman Orientation", "500", "0", "A welcome event for new students, with campus tours, icebreakers, and informational sessions."),
		new Event("CF202",  "Career Fair", "1000", "10", "An event where students can meet potential employers and explore internship and job opportunities."),
		new Event("GL303",  "Guest Lecture: AI in the Modern World", "200", "15", "A lecture by a renowned expert in Artificial Intelligence, discussing its impact on various industries."),
		new Event("SD404",  "Annual Sports Day", "300", "5", "A day full of fun and competitive sports events for students to participate in, from relay races to tug-of-war."),
		new Event("VCE505", "Volunteer Cleanup Event", "100", "0", "Students join forces to help clean up local parks and community spaces, giving back to the environment.")
	));

	public List<Event> getAllEvents() {
		return new ArrayList<>(events);
	}

	public void addEvent(Event event) {
		events.add(event);
	}

	public void deleteEvent(Event event) {
		events.remove(event);
	}

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
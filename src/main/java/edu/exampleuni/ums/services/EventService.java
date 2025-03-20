package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventService {
	private final List<Event> events = new ArrayList<>(Arrays.asList(
		new Event("FO101",  500,  0, "Freshman Orientation", "A welcome event for new students, with campus tours, icebreakers, and informational sessions."),
		new Event("CF202", 1000, 10, "Career Fair", "An event where students can meet potential employers and explore internship and job opportunities."),
		new Event("GL303",  200, 15, "Guest Lecture: AI in the Modern World", "A lecture by a renowned expert in Artificial Intelligence, discussing its impact on various industries."),
		new Event("SD404",  300,  5, "Annual Sports Day", "A day full of fun and competitive sports events for students to participate in, from relay races to tug-of-war."),
		new Event("VCE505", 100,  0, "Volunteer Cleanup Event", "Students join forces to help clean up local parks and community spaces, giving back to the environment.")
	));

	public List<Event> getAllEvents() {
		return new ArrayList<>(events);
	}

	public void addEvent(Event subject) {
		events.add(subject);
	}

	public void deleteEvent(Event subject) {
		events.remove(subject);
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
package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.Event;

public class EventService extends Service<Event> {
	public EventService(ExcelService excelService) {
		super(excelService, excelService.events, Event::new, new String[]{"Event Code", "Event Name", "Description", "Location", "Date and Time", "Capacity", "Cost", "Header Image", "Registered Students"});
	}
}
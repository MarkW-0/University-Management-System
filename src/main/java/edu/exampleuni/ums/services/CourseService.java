package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.Course;

public class CourseService extends Service<Course> {
	public CourseService(ExcelService excelService) {
		super(excelService, excelService.courses, Course::new, new String[]{"Course Code", "Course Name", "Subject Code", "Section Number", "Capacity", "Lecture Time", "Final Exam Date/Time", "Location", "Teacher Name"});
	}
}
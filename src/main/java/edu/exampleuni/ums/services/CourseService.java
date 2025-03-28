package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.Course;
import java.util.*;

public class CourseService {
	private final List<Course> courses;

	public CourseService(ExcelService excelService) {
		String[] expectedFirstRow = {"Course Code", "Course Name", "Subject Code", "Section Number", "Capacity", "Lecture Time", "Final Exam Date/Time", "Location", "Teacher Name"};
		this.courses = excelService.getData(excelService.courses, expectedFirstRow, Course::new);
	}

	public List<Course> getAllCourses() { return new ArrayList<>(courses); }
	public void addCourse(Course course) { courses.add(course); }
	public void deleteCourse(Course course) { courses.remove(course); }

	// Method to update a course
	public void updateCourse(Course updatedCourse) {
		for (int i = 0; i < courses.size(); i++) {
			Course course = courses.get(i);
			if (course.getCode().equals(updatedCourse.getCode())) {
				courses.set(i, updatedCourse);
				break;
			}
		}
	}
}
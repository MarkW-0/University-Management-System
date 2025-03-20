package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.Course;

import java.util.*;

public class CourseService {
	private final List<Course> courses = new ArrayList<>(Arrays.asList(
			new Course("CS101", "Introduction to Programming",	"Computer Science",	"Section 1", "Dr. Smith", "30", "Room 101"),
			new Course("MATH101", "Calculus I", "Mathematics", "Section 1", "Dr. Johnson", "35", "Room 202"),
			new Course("ENG201", "Advanced Composition", "English", "Section 2", "Prof. Williams", "25", "Room 303"),
			new Course("BIO101", "Introduction to Biology", "Biology", "Section 1", "Dr. Brown", "40", "Lab 1"),
			new Course("PHYS101", "Physics I", "Physics", "Section 3", "Dr. Davis", "30", "Room 405")
		)
	);

	public List<Course> getAllCourses() {
		return new ArrayList<>(courses);
	}

	public void addCourse(Course course) {
		courses.add(course);
	}

	public void deleteCourse(Course course) {
		courses.remove(course);
	}

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
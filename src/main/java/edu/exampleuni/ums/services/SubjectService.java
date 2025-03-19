package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.Subject;
import java.util.List;

public class SubjectService {
	public List<Subject> getAllSubjects() {
		// Fetch subjects from database
		return List.of(
				new Subject("MATH001", "Mathematics 101"),
				new Subject("CS101", "Introduction to Computer Science")
		);
	}

	public void addSubject(Subject subject) {
		// Add subject to database
	}

	public void deleteSubject(String subjectCode) {
		// Delete subject from database
	}
}
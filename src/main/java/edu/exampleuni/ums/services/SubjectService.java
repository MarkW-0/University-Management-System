package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.Subject;
import java.util.*;

public class SubjectService {
	private final List<Subject> subjects = new ArrayList<>(Arrays.asList(
			new Subject("MATH001", "Mathematics 101"),
			new Subject("CS101", "Introduction to Computer Science"),
			new Subject("ENG201", "Advanced English Composition"),
			new Subject("BIO101", "Fundamentals of Biology"),
			new Subject("PHYS101", "Physics I")
	));

	public List<Subject> getAllSubjects() {
		return new ArrayList<>(subjects);
	}

	public void addSubject(Subject subject) {
		subjects.add(subject);
	}

	public void deleteSubject(Subject subject) {
		subjects.remove(subject);
	}

	// NEW: Method to update a subject
	public void updateSubject(Subject updatedSubject) {
		for (int i = 0; i < subjects.size(); i++) {
			Subject subject = subjects.get(i);
			if (subject.getCode().equals(updatedSubject.getCode())) {
				subjects.set(i, updatedSubject);
				break;
			}
		}
	}
}
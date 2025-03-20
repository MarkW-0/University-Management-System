package edu.exampleuni.ums.services;

import edu.exampleuni.ums.Subject;
import java.util.*;

public class SubjectService {
	private final List<Subject> subjects = new ArrayList<>();

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
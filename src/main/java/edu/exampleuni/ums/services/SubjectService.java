package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.Subject;

public class SubjectService extends Service<Subject> {
	public SubjectService(ExcelService excelService) {
		super(excelService, excelService.subjects, Subject::new, new String[]{"Subject Code", "Subject Name"});
	}
}
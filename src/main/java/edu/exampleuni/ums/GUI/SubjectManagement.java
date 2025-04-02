package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.*;

public class SubjectManagement extends Management<Subject> {
	public SubjectManagement(MainApp mainApp) {
		super(mainApp, mainApp.subjectService, SubjectEditActions::new, SubjectEditActions::createEditDialog, "SubjectManagement.fxml");
	}
}
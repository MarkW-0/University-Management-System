package edu.exampleuni.ums.views;

import edu.exampleuni.ums.models.Course;
import edu.exampleuni.ums.models.Subject;
import edu.exampleuni.ums.services.CourseService;
import edu.exampleuni.ums.services.SubjectService;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ManagementViews {
	private static final String PRIMARY_COLOR = "#3498db";
	private static final String SECONDARY_COLOR = "#2c3e50";
	private static final String ACCENT_COLOR = "#e74c3c";

	private static final SubjectService subjectService = new SubjectService();
	private static final CourseService courseService = new CourseService();

	// ---------------- SUBJECT MANAGEMENT ----------------
	public static VBox createSubjectManagement(String userRole) {
		VBox content = new VBox(20);
		content.setPadding(new Insets(20));

		Label titleLabel = new Label("Subject Management");
		titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

		// Controls
		HBox controls = new HBox(10);
		controls.setAlignment(Pos.CENTER_LEFT);

		TextField searchField = new TextField();
		searchField.setPromptText("Search subjects...");
		searchField.setPrefWidth(300);

		Button addButton = new Button("Add Subject");
		addButton.setStyle("-fx-background-color: " + PRIMARY_COLOR + "; -fx-text-fill: white;");
		if (!"ADMIN".equals(userRole)) {
			addButton.setDisable(true);
		}

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		controls.getChildren().addAll(searchField, spacer, addButton);

		// Table for subjects
		TableView<Subject> subjectsTable = new TableView<>();
		subjectsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// Subject Code column using lambda to get property
		TableColumn<Subject, String> codeCol = new TableColumn<>("Subject Code");
		codeCol.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
		codeCol.setPrefWidth(150);
		// Set cell factory to force black text
		codeCol.setCellFactory(column -> new TableCell<Subject, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item);
					setTextFill(Color.BLACK);
				}
			}
		});

		// Subject Name column using lambda to get property
		TableColumn<Subject, String> nameCol = new TableColumn<>("Subject Name");
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		nameCol.setPrefWidth(400);
		// Set cell factory to force black text
		nameCol.setCellFactory(column -> new TableCell<Subject, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item);
					setTextFill(Color.BLACK);
				}
			}
		});

		subjectsTable.getColumns().addAll(codeCol, nameCol);
		subjectsTable.getItems().addAll(subjectService.getAllSubjects());

		if ("ADMIN".equals(userRole)) {
			subjectsTable.getColumns().add(createSubjectActionColumn(subjectsTable));
		}

		addButton.setOnAction(e -> {
			Dialog<Subject> dialog = createSubjectEditDialog(null);
			dialog.showAndWait().ifPresent(newSubject -> {
				subjectService.addSubject(newSubject);
				subjectsTable.getItems().add(newSubject);
			});
		});

		VBox.setVgrow(subjectsTable, Priority.ALWAYS);
		content.getChildren().addAll(titleLabel, controls, subjectsTable);
		return content;
	}

	private static TableColumn<Subject, Void> createSubjectActionColumn(TableView<Subject> table) {
		TableColumn<Subject, Void> actionCol = new TableColumn<>("Actions");
		actionCol.setPrefWidth(150);
		actionCol.setCellFactory(param -> new TableCell<Subject, Void>() {
			private final Button editBtn = new Button("Edit");
			private final Button deleteBtn = new Button("Delete");
			private final HBox actionButtons = new HBox(5, editBtn, deleteBtn);

			{
				editBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
				deleteBtn.setStyle("-fx-background-color: " + ACCENT_COLOR + "; -fx-text-fill: white;");

				editBtn.setOnAction(event -> {
					Subject subject = getTableView().getItems().get(getIndex());
					Dialog<Subject> dialog = createSubjectEditDialog(subject);
					dialog.showAndWait().ifPresent(editedSubject -> {
						subjectService.updateSubject(editedSubject);
						table.refresh();
					});
				});

				deleteBtn.setOnAction(event -> {
					Subject subject = getTableView().getItems().get(getIndex());
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Delete Subject");
					alert.setHeaderText("Delete Subject: " + subject.getName());
					alert.setContentText("Are you sure you want to delete this subject?");
					alert.showAndWait().ifPresent(response -> {
						if (response == ButtonType.OK) {
							subjectService.deleteSubject(subject);
							table.getItems().remove(subject);
						}
					});
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				setGraphic(empty ? null : actionButtons);
			}
		});
		return actionCol;
	}

	private static Dialog<Subject> createSubjectEditDialog(Subject existingSubject) {
		Dialog<Subject> dialog = new Dialog<>();
		dialog.setTitle(existingSubject == null ? "Add Subject" : "Edit Subject");

		ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField codeField = new TextField();
		codeField.setPromptText("Subject Code");
		TextField nameField = new TextField();
		nameField.setPromptText("Subject Name");

		if (existingSubject != null) {
			codeField.setText(existingSubject.getCode());
			nameField.setText(existingSubject.getName());
		}

		grid.add(new Label("Subject Code:"), 0, 0);
		grid.add(codeField, 1, 0);
		grid.add(new Label("Subject Name:"), 0, 1);
		grid.add(nameField, 1, 1);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == saveButtonType) {
				String code = codeField.getText().trim();
				String name = nameField.getText().trim();
				if (code.isEmpty() || name.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Invalid Input");
					alert.setHeaderText(null);
					alert.setContentText("Subject Code and Name cannot be empty!");
					alert.showAndWait();
					return null;
				}
				if (existingSubject != null) {
					existingSubject.setCode(code);
					existingSubject.setName(name);
					return existingSubject;
				} else {
					return new Subject(code, name);
				}
			}
			return null;
		});

		return dialog;
	}

	// ---------------- COURSE MANAGEMENT ----------------
	public static VBox createCourseManagement(String userRole) {
		// Your existing course management code remains here.
		return null; // Replace with your actual implementation.
	}

	// ---------------- STUDENT MANAGEMENT (Under Construction) ----------------
	public static VBox createStudentManagement(String userRole) {
		VBox content = new VBox(20);
		content.setPadding(new Insets(20));
		Label titleLabel = new Label("Student Management");
		titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		Label placeholder = new Label("Student Management is under construction.");
		content.getChildren().addAll(titleLabel, placeholder);
		return content;
	}

	// ---------------- FACULTY MANAGEMENT (Under Construction) ----------------
	public static VBox createFacultyManagement(String userRole) {
		VBox content = new VBox(20);
		content.setPadding(new Insets(20));
		Label titleLabel = new Label("Faculty Management");
		titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		Label placeholder = new Label("Faculty Management is under construction.");
		content.getChildren().addAll(titleLabel, placeholder);
		return content;
	}

	// ---------------- EVENT MANAGEMENT (Under Construction) ----------------
	public static VBox createEventManagement(String userRole) {
		VBox content = new VBox(20);
		content.setPadding(new Insets(20));
		Label titleLabel = new Label("Event Management");
		titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		Label placeholder = new Label("Event Management is under construction.");
		content.getChildren().addAll(titleLabel, placeholder);
		return content;
	}
}

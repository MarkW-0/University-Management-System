package edu.exampleuni.ums.views;
import edu.exampleuni.ums.views.ManagementViews;
import edu.exampleuni.ums.models.User;
import edu.exampleuni.ums.utils.NavigationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class MainApplicationLayout {
	private final Stage stage;
	private final User user;
	private VBox menuPane;
	private StackPane contentPane;
	private NavigationManager navigationManager;

	public MainApplicationLayout(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
		this.contentPane = new StackPane();
		this.navigationManager = new NavigationManager(contentPane);
	}

	public Scene getScene() {
		BorderPane mainLayout = new BorderPane();

		// Header
		HBox header = createHeader();
		mainLayout.setTop(header);

		// Menu
		this.menuPane = createMenu();
		ScrollPane menuScrollPane = new ScrollPane(menuPane);
		menuScrollPane.setFitToWidth(true);
		menuScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		menuScrollPane.setPrefWidth(250);
		menuScrollPane.setStyle("-fx-background-color: #2c3e50;");
		mainLayout.setLeft(menuScrollPane);

		// Content
		this.contentPane = new StackPane();
		this.contentPane.setPadding(new Insets(20));
		this.contentPane.setStyle("-fx-background-color: #f5f5f5;");
		mainLayout.setCenter(contentPane);

		// Show dashboard by default
		contentPane.getChildren().add(createDashboard());

		return new Scene(mainLayout, 1024, 768);
	}

	private HBox createHeader() {
		HBox header = new HBox();
		header.setPadding(new Insets(10, 15, 10, 15));
		header.setStyle("-fx-background-color: #3498db;");
		header.setAlignment(Pos.CENTER_LEFT);

		Label title = new Label("University Management System");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		title.setTextFill(Color.WHITE);

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		Label userLabel = new Label("Logged in as: " + this.user.getRole());
		userLabel.setTextFill(Color.WHITE);

		Button logoutBtn = new Button("Logout");
		logoutBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
		logoutBtn.setOnAction(e -> {
			LoginScreen loginScreen = new LoginScreen(stage);
			stage.setScene(loginScreen.getScene());
			stage.setMaximized(false);
		});

		header.getChildren().addAll(title, spacer, userLabel, new Separator(javafx.geometry.Orientation.VERTICAL), logoutBtn);
		return header;
	}

	private VBox createMenu() {
		VBox menu = new VBox();
		menu.setPrefWidth(250);
		menu.setStyle("-fx-background-color: #2c3e50;");

		// Dashboard menu item
		menu.getChildren().add(createMenuItem("Dashboard", e -> setContent(createDashboard())));

		// For ADMIN users, show all options; for USER, include Subject Management too.
		if (this.user.getRole().equals("ADMIN")) {
			menu.getChildren().addAll(
					createMenuItem("Subject Management", e -> setContent(ManagementViews.createSubjectManagement(user.getRole()))),
					createMenuItem("Course Management", e -> setContent(ManagementViews.createCourseManagement(user.getRole()))),
					createMenuItem("Student Management", e -> setContent(ManagementViews.createStudentManagement(user.getRole()))),
					createMenuItem("Faculty Management", e -> setContent(ManagementViews.createFacultyManagement(user.getRole()))),
					createMenuItem("Event Management", e -> setContent(ManagementViews.createEventManagement(user.getRole())))
			);
		} else {
			// For USER role, include Subject Management as read-only along with other options.
			menu.getChildren().addAll(
					createMenuItem("Subject Management", e -> setContent(ManagementViews.createSubjectManagement(user.getRole()))),
					createMenuItem("Course Management", e -> setContent(ManagementViews.createCourseManagement(user.getRole()))),
					createMenuItem("Event Management", e -> setContent(ManagementViews.createEventManagement(user.getRole()))),
					createMenuItem("Profile Management", e -> setContent(createProfileManagement()))
			);
		}

		return menu;
	}

	private VBox createMenuItem(String name, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
		VBox item = new VBox();
		item.setPadding(new Insets(10, 15, 10, 15));
		item.setStyle("-fx-cursor: hand;");

		Label label = new Label(name);
		label.setFont(Font.font("Arial", 14));
		label.setTextFill(Color.WHITE);

		item.getChildren().add(label);

		item.setOnMouseEntered(e -> item.setStyle("-fx-background-color: #3498db; -fx-cursor: hand;"));
		item.setOnMouseExited(e -> item.setStyle("-fx-cursor: hand;"));
		item.setOnMouseClicked(e -> {
			if (action != null) {
				action.handle(new javafx.event.ActionEvent());
			}
			for (javafx.scene.Node node : this.menuPane.getChildren()) {
				node.setStyle("-fx-cursor: hand;");
			}
			item.setStyle("-fx-background-color: #3498db; -fx-cursor: hand;");
		});

		return item;
	}

	private void setContent(javafx.scene.Node content) {
		this.contentPane.getChildren().clear();
		this.contentPane.getChildren().add(content);
	}

	private VBox createDashboard() {
		VBox dashboardContent = new VBox(20);
		dashboardContent.setPadding(new Insets(20));

		Label titleLabel = new Label("Dashboard");
		titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

		HBox metricsRow = new HBox(20);
		metricsRow.setAlignment(Pos.CENTER);
		metricsRow.getChildren().addAll(
				createMetricCard("Students", "1,250", "#3498db"),
				createMetricCard("Courses", "87", "#e74c3c"),
				createMetricCard("Faculty", "64", "#2ecc71"),
				createMetricCard("Events", "12", "#f39c12")
		);

		VBox recentActivities = new VBox(10);
		Label activitiesLabel = new Label("Recent Activities");
		activitiesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		ListView<String> activitiesList = new ListView<>();
		activitiesList.getItems().addAll(
				"New student registration: John Doe (10 minutes ago)",
				"Course CS101 updated by Dr. Smith (1 hour ago)",
				"Event 'Tech Symposium' created (3 hours ago)",
				"Faculty member Dr. Johnson edited profile (Yesterday)"
		);
		recentActivities.getChildren().addAll(activitiesLabel, activitiesList);
		VBox.setVgrow(activitiesList, Priority.ALWAYS);

		VBox upcomingEvents = new VBox(10);
		Label eventsLabel = new Label("Upcoming Events");
		eventsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		ListView<String> eventsList = new ListView<>();
		eventsList.getItems().addAll(
				"Tech Symposium (March 10, 2025)",
				"Final Exams Begin (April 15, 2025)",
				"Graduation Ceremony (May 20, 2025)"
		);
		upcomingEvents.getChildren().addAll(eventsLabel, eventsList);
		VBox.setVgrow(eventsList, Priority.ALWAYS);

		dashboardContent.getChildren().addAll(titleLabel, metricsRow, recentActivities, upcomingEvents);
		return dashboardContent;
	}

	private VBox createMetricCard(String title, String value, String color) {
		VBox card = new VBox(5);
		card.setPadding(new Insets(20));
		card.setPrefWidth(200);
		card.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0); -fx-background-radius: 5;");

		Label titleLabel = new Label(title);
		titleLabel.setFont(Font.font("Arial", 14));

		Label valueLabel = new Label(value);
		valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
		valueLabel.setTextFill(Color.web(color));

		card.getChildren().addAll(titleLabel, valueLabel);
		return card;
	}

	// For placeholders – you may update these later
	private VBox createStudentManagement() {
		VBox content = new VBox(20);
		content.setPadding(new Insets(20));
		content.getChildren().add(new Label("Student Management (Under Construction)"));
		return content;
	}

	private VBox createFacultyManagement() {
		VBox content = new VBox(20);
		content.setPadding(new Insets(20));
		content.getChildren().add(new Label("Faculty Management (Under Construction)"));
		return content;
	}

	private VBox createEventManagement() {
		VBox content = new VBox(20);
		content.setPadding(new Insets(20));
		content.getChildren().add(new Label("Event Management (Under Construction)"));
		return content;
	}

	private VBox createProfileManagement() {
		VBox content = new VBox(20);
		content.setPadding(new Insets(20));
		content.getChildren().add(new Label("Profile Management (Under Construction)"));
		return content;
	}
}

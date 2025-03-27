module edu.exampleuni.ums {
	requires javafx.controls;
	requires javafx.fxml;

	requires org.controlsfx.controls;
	requires org.kordamp.bootstrapfx.core;

	opens edu.exampleuni.ums to javafx.fxml;
	exports edu.exampleuni.ums;
	exports edu.exampleuni.ums.models;
	opens edu.exampleuni.ums.models to javafx.fxml;
}
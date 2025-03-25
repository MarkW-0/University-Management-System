module edu.exampleuni.ums {
	requires javafx.controls;
	requires javafx.fxml;

	requires org.controlsfx.controls;
	requires org.kordamp.bootstrapfx.core;
	requires org.apache.poi.ooxml;

	opens edu.exampleuni.ums to javafx.fxml;
	exports edu.exampleuni.ums;
	exports edu.exampleuni.ums.GUI;
	opens edu.exampleuni.ums.GUI to javafx.fxml;
	exports edu.exampleuni.ums.models;
	opens edu.exampleuni.ums.models to javafx.fxml;
}
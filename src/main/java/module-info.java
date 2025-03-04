module edu.exampleuni.aryanns.ums {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens edu.exampleuni.aryanns.ums to javafx.fxml;
    exports edu.exampleuni.aryanns.ums;
}
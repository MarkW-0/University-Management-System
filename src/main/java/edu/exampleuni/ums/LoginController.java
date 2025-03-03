package edu.exampleuni.ums;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginController {
    @FXML private TextField UserID;
    @FXML private PasswordField Password;
    @FXML protected void login() {
        System.out.println(UserID.getText());
        System.out.println(Password.getText());
    }
}
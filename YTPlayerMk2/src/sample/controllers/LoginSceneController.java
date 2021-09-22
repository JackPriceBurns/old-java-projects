package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Main;

public class LoginSceneController {

    @FXML
    private TextField usernameField = new TextField();
    @FXML
    private PasswordField passwordField = new PasswordField();
    private double xOffset;
    private double yOffset;

    public void close(){
        Main.getInstance().getStage().close();
    }

    public void login() {
        System.out.println("Username: " + usernameField.getText());
        System.out.println("Password: " + passwordField.getText());
    }
}

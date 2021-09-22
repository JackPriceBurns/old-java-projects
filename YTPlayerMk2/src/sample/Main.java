package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.formHandlers.LoginFormHandler;

public class Main extends Application {

    private static Main instance;
    private LoginFormHandler loginFormHandler;
    private Stage stage;
    private Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        stage = primaryStage;

        stage.initStyle(StageStyle.UNDECORATED);

        loginFormHandler = new LoginFormHandler();

        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Main getInstance() {
        return instance;
    }

    public LoginFormHandler getLoginFormHandler() {
        return loginFormHandler;
    }

    public Stage getStage() {
        return stage;
    }

    public Parent getRoot() {
        return root;
    }

    public void setScene(Scene scene) {
        stage.setScene(scene);
    }
}

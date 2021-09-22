package sample.formHandlers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import sample.Main;

import java.io.IOException;

public class LoginFormHandler {

    private boolean mouseOnBar;
    private Parent root;
    private Scene scene;
    private double xOffset;
    private double yOffset;

    public LoginFormHandler() throws IOException {
        root = FXMLLoader.load(getClass().getResource("../forms/LoginForm.fxml"));
        scene = new Scene(root, 600, 400);

        root.setOnMousePressed(event -> {
            if(event.getSceneY() < 60){
                mouseOnBar = true;
            }
            xOffset = Main.getInstance().getStage().getX() - event.getScreenX();
            yOffset = Main.getInstance().getStage().getY() - event.getScreenY();
        });

        root.setOnMouseReleased(event -> {
            mouseOnBar = false;
        });

        root.setOnMouseDragged(event -> {
            if (mouseOnBar) {
                Main.getInstance().getStage().setX(event.getScreenX() + xOffset);
                Main.getInstance().getStage().setY(event.getScreenY() + yOffset);
            }
        });

            Main.getInstance().setScene(scene);
        }
    }

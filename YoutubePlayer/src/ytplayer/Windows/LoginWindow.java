package ytplayer.Windows;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ytplayer.Listeners.WindowControlMouseListener;
import ytplayer.Main;
import ytplayer.Window;

public class LoginWindow extends Window{

    private Parent root;
    private Scene scene;
    private WindowControlMouseListener windowControlMouseListener;
    private Main main;

    public LoginWindow(Main main) {

        this.main = main;

        root = new Group();
        scene = new Scene(root, 600, 400);

        windowControlMouseListener = new WindowControlMouseListener(this);

        Rectangle rectangle = new Rectangle(600, 50);
        rectangle.setLayoutX(0);
        rectangle.setLayoutY(0);
        rectangle.setFill(Color.RED);

        Image exitButtonImage = new Image("file:img/exit.png");
        ImageView exitButton = new ImageView();
        exitButton.setImage(exitButtonImage);
        exitButton.setLayoutY(200);
        exitButton.setLayoutX(scene.getWidth() - 60);
        exitButton.setFitWidth(60);

        scene.setFill(Color.BEIGE);

        ((Group) root).getChildren().add(rectangle);
        ((Group) root).getChildren().add(exitButton);

        rectangle.setOnMousePressed(windowControlMouseListener);
        rectangle.setOnMouseDragged(windowControlMouseListener);
    }

    public Scene getScene() {
        return scene;
    }

    public Main getMain() {
        return main;
    }
}
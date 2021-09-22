package ytplayer;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class Window {

    private Parent root;
    private Scene scene;

    public Window(){
        root = new Group();
        scene = new Scene(root, 0, 0);
    }
}

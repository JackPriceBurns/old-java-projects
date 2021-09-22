package ytplayer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ytplayer.Windows.LoginWindow;

public class Main extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        stage.initStyle(StageStyle.UNDECORATED);

        LoginWindow loginWindow = new LoginWindow(this);

        setScene(loginWindow.getScene());

        stage.setResizable(false);
        stage.show();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }

    public Stage getStage() {
        return stage;
    }

    public void close(){
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

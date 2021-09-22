package ytplayer.Listeners;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import ytplayer.Main;
import ytplayer.Window;
import ytplayer.Windows.LoginWindow;

public class WindowControlMouseListener implements EventHandler<MouseEvent> {

    private boolean buttonDown;
    private LoginWindow loginWindow;
    private double xOffset;
    private double yOffset;

    public WindowControlMouseListener(LoginWindow loginWindow){
        this.loginWindow = loginWindow;
    }

    @Override
    public void handle(MouseEvent event) {
        if(event.getEventType() ==  MouseEvent.MOUSE_PRESSED){
            xOffset = loginWindow.getMain().getStage().getX() - event.getScreenX();
            yOffset = loginWindow.getMain().getStage().getY() - event.getScreenY();
        }

        if(event.getEventType() == MouseEvent.MOUSE_DRAGGED){
            loginWindow.getMain().getStage().setX(event.getScreenX() + xOffset);
            loginWindow.getMain().getStage().setY(event.getScreenY() + yOffset);
        }
    }
}

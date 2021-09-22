package com.jack.chess;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseListener implements EventHandler<MouseEvent> {

    private Main main;

    public MouseListener(Main main){
        this.main = main;
    }

    @Override
    public void handle(MouseEvent event) {
        Location location = new Location((int) Math.floor(event.getX()/100), (int) Math.floor(event.getY()/100));
        main.clickAt(location);
    }
}

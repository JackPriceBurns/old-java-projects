package com.jack.monster;

import com.jack.monster.tiles.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyListener implements EventHandler<Event> {

    private boolean forward, backward, right, left;
    private long timeStamp;
    private Main main;
    private boolean monsterMove = false;

    public KeyListener(Main main){
        timeStamp = System.currentTimeMillis();
        this.main = main;
    }

    @Override
    public void handle(Event event) {
        KeyEvent keyEvent = (KeyEvent) event;

        if (keyEvent.getEventType().equals(KeyEvent.KEY_PRESSED))
            keyPressed(keyEvent);

        if (keyEvent.getEventType().equals(KeyEvent.KEY_RELEASED))
            keyReleased(keyEvent);
    }

    private void keyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.W))
            forward = false;

        if(event.getCode().equals(KeyCode.A))
            left = false;

        if(event.getCode().equals(KeyCode.S))
            backward = false;

        if(event.getCode().equals(KeyCode.D))
            right = false;
    }

    private void keyPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.W)) {
            move(Direction.FORWARD);
            forward = true;
        }

        if (event.getCode().equals(KeyCode.A)) {
            move(Direction.LEFT);
            left = true;
        }

        if (event.getCode().equals(KeyCode.S)) {
            move(Direction.BACK);
            backward = true;
        }

        if (event.getCode().equals(KeyCode.D)){
            move(Direction.RIGHT);
            right = true;
        }
    }

    private void move(String direction){

        timeStamp = System.currentTimeMillis();

        int newX = main.getPlayer().getLocation().getX();
        int newY = main.getPlayer().getLocation().getY();

        if (direction.equals(Direction.FORWARD) && isForward()) {
            newY--;
        }

        if (direction.equals(Direction.BACK) && isBackward()) {
            newY++;
        }

        if (direction.equals(Direction.RIGHT) && isRight()) {
            newX++;
        }

        if (direction.equals(Direction.LEFT) && isLeft()) {
            newX--;
        }

        if (!(-1 < newY && newY > (main.getHeight() - 1))) {
            if(!(-1 < newX && newX > (main.getWidth() - 1))){
                if(!main.getGrid()[newX][newY].isSolid()){
                    GroundTile tile = new GroundTile();
                    if (main.getGrid()[newX][newY] instanceof TreasureTile) {
                        main.messageBox("You Found", null, "You have found some treasure!");
                    } else if (main.getGrid()[newX][newY] instanceof TrapTile) {
                        main.messageBox("You Found", null, "You stepped on a trap! The monster has been awakened!");
                    }

                    tile.setShrouded(false);

                    main.getGrid()[newX][newY] = tile;

                    main.setTile(tile, main.getPlayer().getLocation().getX(), main.getPlayer().getLocation().getY());
                    main.getPlayer().setLocation(new Location(newX, newY));
                    main.setTile(new PlayerTile(), main.getPlayer().getLocation().getX(), main.getPlayer().getLocation().getY());
                    moveMonster();
                } else {
                    WallTile wall = new WallTile();
                    wall.setShrouded(false);
                    main.setTile(wall, newX, newY);
                }
            }
        }
    }

    private void moveMonster() {
        if(false) {
            if(!monsterMove){
                monsterMove = true;
                return;
            } else {
                monsterMove = false;
            }
            double distance = main.getPlayer().getLocation().distance(main.getMonster().getLocation());
            int deltaX = main.getPlayer().getLocation().getX() - main.getMonster().getLocation().getX();
            int deltaY = main.getPlayer().getLocation().getY() - main.getMonster().getLocation().getY();
            int newX = main.getMonster().getLocation().getX();
            int newY = main.getMonster().getLocation().getY();
            if(Math.toDegrees(Math.acos(deltaX/distance)) == 90 || Math.toDegrees(Math.acos(deltaX/distance)) == 270){
            } else if(Math.toDegrees(Math.acos(deltaX/distance)) > 90){
                newX--;
            } else if(Math.toDegrees(Math.acos(deltaX/distance)) < 90) {
                newX++;
            }
            if(Math.toDegrees(Math.asin(deltaY/distance)) == 0 || Math.toDegrees(Math.asin(deltaY/distance)) == 0){
            } else if(Math.toDegrees(Math.asin(deltaY / distance)) < 0){
                newY--;
            } else if(Math.toDegrees(Math.asin(deltaY / distance)) > 0) {
                newY++;
            }

            System.out.println(Math.toDegrees(Math.asin(deltaY / distance)));

            main.setTile(new GroundTile(), main.getMonster().getLocation().getX(), main.getMonster().getLocation().getY());
            main.getMonster().setLocation(new Location(newX, newY));
            main.setTile(new MonsterTile(), newX, newY);
        }
    }

    public boolean isForward() {
        return !forward && !backward;
    }

    public boolean isBackward() {
        return !backward && !forward;
    }

    public boolean isRight() {
        return !right && !left;
    }

    public boolean isLeft() {
        return !left && !right;
    }
}

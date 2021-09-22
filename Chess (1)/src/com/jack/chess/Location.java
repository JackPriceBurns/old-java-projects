package com.jack.chess;

public class Location {

    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setLocation(Location location){
        this.x = location.getX();
        this.y = location.getY();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}

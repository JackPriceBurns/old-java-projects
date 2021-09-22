package com.jack.monster;

public class Location {

    private int X;
    private int Y;

    public Location(int X, int Y){
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setX(int X){
        this.X = X;
    }

    public void setY(int Y){
        this.Y = Y;
    }

    public double distance(Location loc){
        int diffX = loc.getX() - this.X;
        int diffY = loc.getY() - this.Y;
        return Math.sqrt((diffX * diffX) + (diffY * diffY));
    }
}

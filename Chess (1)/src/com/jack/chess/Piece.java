package com.jack.chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashSet;

public class Piece {

    private boolean white;
    private Location currentLoc;
    private Image image;
    private Main main;

    public  Piece(boolean white, Location loc, Main main) {
        this.white = white;
        this.currentLoc = loc;
    }

    public boolean validMove(Location newLocation){
        return false;
    }

    public boolean validAttack(Location newLocation){
        return validMove(newLocation);
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public Location getCurrentLoc() {
        return currentLoc;
    }

    public void setCurrentLoc(Location currentLoc) {
        this.currentLoc = currentLoc;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public HashSet<Location> getValidMoves() {
        return new HashSet<Location>();
    }

    public Main getMain(){
        return main;
    }

    public void setHasMoved(boolean hasMoved){

    }
}

package com.jack.chess.pieces;

import com.jack.chess.Location;
import com.jack.chess.Main;
import com.jack.chess.Piece;
import javafx.scene.image.Image;

import java.util.HashSet;

public class Knight extends Piece {

    private Main main;

    public Knight(boolean white, Location loc, Main main) {
        super(white, loc, main);
        this.main = main;
        if(white){
            super.setImage(new Image(this.getClass().getResource("../src/whiteKnightTile.png").toExternalForm()));
        } else {
            super.setImage(new Image(this.getClass().getResource("../src/blackKnightTile.png").toExternalForm()));
        }
    }

    public boolean validMove(Location newLoc){
        if((Math.pow(getCurrentLoc().getX() - newLoc.getX(), 2) + Math.pow(getCurrentLoc().getY() - newLoc.getY(), 2)) == 5){
            if(main.isWhiteTurn() == super.isWhite()){
                if(main.getPiece(newLoc) != null){
                    if(main.getPiece(newLoc).isWhite() != super.isWhite()){
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public HashSet<Location> getValidMoves(){
        HashSet<Location> locations = new HashSet<Location>();
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                Location location = new Location(x,y);
                if(validMove(location)){
                    locations.add(location);
                }
            }
        }
        return locations;
    }
}

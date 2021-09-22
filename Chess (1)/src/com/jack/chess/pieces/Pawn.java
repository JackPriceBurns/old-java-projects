package com.jack.chess.pieces;

import com.jack.chess.Location;
import com.jack.chess.Main;
import com.jack.chess.Piece;
import javafx.scene.image.Image;

import java.util.HashSet;

public class Pawn extends Piece {

    private boolean hasMoved = false;
    private Main main;

    public Pawn(boolean white, Location loc, Main main) {
        super(white, loc, main);
        this.main = main;
        if(white){
            super.setImage(new Image(this.getClass().getResource("../src/whitePawnTile.png").toExternalForm()));
        } else {
            super.setImage(new Image(this.getClass().getResource("../src/blackPawnTile.png").toExternalForm()));
        }
    }

    public boolean validMove(Location newLoc){

        if(isNotObstructed(newLoc)){

            int yOffset = 0;

            if(isWhite()){

                yOffset = -1;

            } else {

                yOffset = 1;

            }

            if(!hasMoved){
                if(getCurrentLoc().getY() + yOffset*2 == newLoc.getY() && getCurrentLoc().getX() == newLoc.getX() && main.getPiece(newLoc) == null){
                    return true;
                }
            }

            if(getCurrentLoc().getY() + yOffset == newLoc.getY() && getCurrentLoc().getX() == newLoc.getX() && main.getPiece(newLoc) == null){
                return true;
            }

            if(isEnemy(newLoc)){
                if(getCurrentLoc().getY() + yOffset == newLoc.getY() && (getCurrentLoc().getX() + 1 == newLoc.getX() || getCurrentLoc().getX() - 1 == newLoc.getX())){
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

    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }

    public boolean isEnemy(Location newLoc) {

        if(main.getPiece(newLoc) != null){

            if((main.getPiece(newLoc).isWhite() && !isWhite()) || (!main.getPiece(newLoc).isWhite() && isWhite())){
                return true;
            }
        }

        return false;
    }

    public boolean isNotObstructed(Location newLoc) {

        if(main.getPiece(newLoc) == null){
            return true;
        } else {
            if((main.getPiece(newLoc).isWhite() && !isWhite()) || (!main.getPiece(newLoc).isWhite() && isWhite())){
                return true;
            }
        }
        return false;
    }
}

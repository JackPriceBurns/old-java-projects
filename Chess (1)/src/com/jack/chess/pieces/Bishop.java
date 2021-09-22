package com.jack.chess.pieces;

import com.jack.chess.Location;
import com.jack.chess.Main;
import com.jack.chess.Piece;
import javafx.scene.image.Image;

public class Bishop extends Piece {

    public Bishop(boolean white, Location loc, Main main) {
        super(white, loc, main);
        if(white){
            super.setImage(new Image(this.getClass().getResource("../src/whiteBishopTile.png").toExternalForm()));
        } else {
            super.setImage(new Image(this.getClass().getResource("../src/blackBishopTile.png").toExternalForm()));
        }
    }

    public boolean validMove(Location currentLocation, Location newLocation) {
        return false;
    }
}

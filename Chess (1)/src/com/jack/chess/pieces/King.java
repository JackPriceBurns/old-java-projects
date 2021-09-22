package com.jack.chess.pieces;

import com.jack.chess.Location;
import com.jack.chess.Main;
import com.jack.chess.Piece;
import javafx.scene.image.Image;


public class King extends Piece {

    public King(boolean white, Location loc, Main main) {
        super(white, loc, main);
        if(white){
            super.setImage(new Image(this.getClass().getResource("../src/whiteKingTile.png").toExternalForm()));
        } else {
            super.setImage(new Image(this.getClass().getResource("../src/blackKingTile.png").toExternalForm()));
        }

    }
}

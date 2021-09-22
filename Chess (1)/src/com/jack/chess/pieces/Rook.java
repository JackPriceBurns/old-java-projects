package com.jack.chess.pieces;

import com.jack.chess.Location;
import com.jack.chess.Main;
import com.jack.chess.Piece;
import javafx.scene.image.Image;

public class Rook extends Piece {

    public Rook(boolean white, Location loc, Main main) {
        super(white, loc, main);
        if(white){
            super.setImage(new Image(this.getClass().getResource("../src/whiteRookTile.png").toExternalForm()));
        } else {
            super.setImage(new Image(this.getClass().getResource("../src/blackRookTile.png").toExternalForm()));
        }
    }
}

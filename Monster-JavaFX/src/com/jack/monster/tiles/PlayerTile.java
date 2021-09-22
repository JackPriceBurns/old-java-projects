package com.jack.monster.tiles;

import com.jack.monster.Tile;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerTile extends Tile {

    private boolean isSolid = false;
    private ImageView imageView;
    private boolean shrouded = false;

    public PlayerTile(){
        ImageView imageView = new ImageView();
        imageView.setImage(new Image("file:" + this.getClass().getResource("../src/player.jpg").getPath()));
        this.imageView = imageView;

    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public boolean isShrouded() {
        return shrouded;
    }

    @Override
    public void setShrouded(boolean shrouded){
        this.shrouded = shrouded;
    }
}

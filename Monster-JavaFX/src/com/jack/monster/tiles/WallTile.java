package com.jack.monster.tiles;

import com.jack.monster.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WallTile extends Tile {

    private boolean isSolid = true;
    private ImageView imageView;
    private boolean shrouded = true;

    public WallTile(){
        ImageView imageView = new ImageView();
        imageView.setImage(new Image("file:" + this.getClass().getResource("../src/wall.jpg").getPath()));
        this.imageView = imageView;
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public boolean isSolid(){
        return true;
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

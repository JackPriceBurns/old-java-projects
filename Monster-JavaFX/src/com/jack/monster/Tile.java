package com.jack.monster;

        import javafx.scene.image.ImageView;

public abstract class Tile {

    private ImageView imageView;
    private boolean isSolid;
    private boolean shrouded;

    public abstract ImageView getImageView();

    public boolean isSolid(){
        return this.isSolid;
    }
    public boolean isShrouded() { return this.shrouded; };
    public void setShrouded(boolean shrouded) { this.shrouded = shrouded; }
}

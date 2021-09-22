package com.jack.monster.entities;

import com.jack.monster.Entity;
import com.jack.monster.Location;

public class Monster extends Entity {

    private Location location;
    private boolean awake;

    public Monster(Location location){
        this.location = location;
        setAwake(true);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isAwake() {
        return awake;
    }

    public void setAwake(boolean awake) {
        this.awake = awake;
    }
}

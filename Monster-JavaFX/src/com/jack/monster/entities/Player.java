package com.jack.monster.entities;

import com.jack.monster.Entity;
import com.jack.monster.Location;

public class Player extends Entity {

    private Location location;

    public Player(Location location){
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

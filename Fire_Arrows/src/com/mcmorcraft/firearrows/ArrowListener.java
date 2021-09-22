package com.mcmorcraft.firearrows;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ArrowListener implements Listener{
	@EventHandler
	public void projectileHit(ProjectileHitEvent event){
		if(event.getEntity() instanceof Arrow){
			Arrow arrow = (Arrow) event.getEntity();
			Location location = arrow.getLocation();
			Block block = location.getBlock();
			if(arrow.getShooter() instanceof Player){
				if(arrow.getFireTicks() > 1){
					if(block.getType().equals(Material.AIR)){
						block.setType(Material.FIRE);
					}
				}
			}
		}
	}
}

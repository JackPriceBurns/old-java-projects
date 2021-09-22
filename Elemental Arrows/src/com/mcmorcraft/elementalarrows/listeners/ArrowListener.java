package com.mcmorcraft.elementalarrows.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.mcmorcraft.elementalarrows.ElementalArrow;
import com.mcmorcraft.elementalarrows.ElementalArrows;
import com.mcmorcraft.elementalarrows.utils.MetaUtils;

public class ArrowListener implements Listener {
	
	private ElementalArrows plugin;
	private MetaUtils MU;

	public ArrowListener(ElementalArrows p){
		this.plugin = p;
		this.MU = plugin.getMetaUtils();
	}

	@EventHandler
	public void prijectileHit(ProjectileHitEvent event){
		if(event.getEntity() instanceof Arrow && event.getEntity().getShooter() instanceof Player){
			Arrow arrow = (Arrow) event.getEntity();
			Player player = (Player) arrow.getShooter();
			for(ElementalArrow elearrow : plugin.getArrowList()){
				if(MU.hasMetadata(arrow, elearrow.getName())){
					plugin.getArrowEffector().effectBlock(elearrow, player, arrow);
				}
			}
		}
	}
}
package com.jack.newplugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Listeners implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			event.getPlayer().sendMessage("You right clicked on " + event.getClickedBlock().getType());
			event.getClickedBlock().setType(Material.DIAMOND_BLOCK);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Player player = event.getPlayer();
		player.damage(1.0);
	}
	
	@EventHandler
	public void onPlayerDead(PlayerDeathEvent event){
		event.setDeathMessage(event.getEntity().getName() + " U DUN GOOFED");
	}
}

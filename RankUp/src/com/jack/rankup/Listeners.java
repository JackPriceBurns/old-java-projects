package com.jack.rankup;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Listeners implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		if(event.getBlock().getType().equals(Material.SIGN_POST) || event.getBlock().getType().equals(Material.WALL_SIGN)){
			Sign sign = (Sign) event.getBlock().getState();
			String line2 = sign.getLine(1);
			if(line2.equalsIgnoreCase("[rankup]")){
				event.getPlayer().sendMessage("Nope.");
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock() != null){
			if(event.getClickedBlock().getType().equals(Material.SIGN_POST) || event.getClickedBlock().getType().equals(Material.WALL_SIGN)){
				Sign sign = (Sign)event.getClickedBlock().getState();
				String line2 = sign.getLine(1);
				if(line2.equalsIgnoreCase("[rankup]")){
					Rankup.getInstance().RankupUser(event.getPlayer());
				}
			}
		}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		Player player = event.getPlayer();
		if(event.getLine(1).equalsIgnoreCase("[rankup]")); {
			if(player.hasPermission("rankup.create")){
				player.sendMessage("Sign created.");
			} else {
				player.sendMessage("Nope.");
				event.setCancelled(true);
			}
		}
	}
}

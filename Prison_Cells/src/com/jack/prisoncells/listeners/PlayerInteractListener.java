package com.jack.prisoncells.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.jack.prisoncells.PrisonCells;
import com.jack.prisoncells.utils.MetaUtils;
import com.jack.prisoncells.utils.Utils;

public class PlayerInteractListener implements Listener{
	
	private PrisonCells plugin;
	private MetaUtils MU;
	private Utils U;
	
	public PlayerInteractListener(PrisonCells p){
		plugin = p;
		MU = plugin.getMU();
		U = plugin.getUtils();
	}

	@EventHandler
	public void playerInteract(PlayerInteractEvent event){
		if(event.getPlayer().hasPermission("PrisonCells.usewand") && event.getPlayer().getItemInHand().getType().equals(Material.STICK)){
			if(event.getAction() == Action.LEFT_CLICK_BLOCK){
				Location location = event.getClickedBlock().getLocation();
				MU.setMetaData(event.getPlayer(), "pos2", location);
				event.getPlayer().sendMessage("Pos2 Selected at " + location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ());
				event.setCancelled(true);
			} else if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
				Location location = event.getClickedBlock().getLocation();
				MU.setMetaData(event.getPlayer(), "pos1", location);
				event.getPlayer().sendMessage("Pos1 Selected at " + location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ());
				event.setCancelled(true);
			}
		}
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(event.getClickedBlock().getType().equals(Material.CHEST)){
				if(!event.getPlayer().hasPermission("PrisonCells.bypass")){
					Location blockloc = event.getClickedBlock().getLocation();
					if(U.getCell(blockloc) == null){
						if(U.getPrison(blockloc) != null){
							sendMessage(event.getPlayer(), "&cYou cant open chests inside a prison");
							event.setCancelled(true);
						}
					} else {
						if(!U.canBuild((int) U.getCell(blockloc), event.getPlayer())){
							sendMessage(event.getPlayer(), "&cYou cant open someone else's chest");
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
	
	private void sendMessage(CommandSender sender, String string) {
		string = string.replaceAll("&", "§");
		sender.sendMessage(string);
	}
}

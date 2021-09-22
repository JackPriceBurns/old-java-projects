package com.jack.prisoncells.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.jack.prisoncells.PrisonCells;
import com.jack.prisoncells.utils.Utils;

public class BlockListener implements Listener {
	
	private PrisonCells plugin;
	private Utils U;
	
	public BlockListener(PrisonCells plugin){
		this.plugin = plugin;
		U = this.plugin.getUtils();
	}
	
	@EventHandler
	public void blockPlace(BlockPlaceEvent event){
		if(!event.getPlayer().hasPermission("PrisonCells.bypass")){
			Location blockloc = event.getBlock().getLocation();
			if(U.getCell(blockloc) == null){
				if(U.getPrison(blockloc) != null){
					sendMessage(event.getPlayer(), "&cYou cant build inside a prison");
					event.setCancelled(true);
				}
			} else {
				if(!U.canBuild((int) U.getCell(blockloc), event.getPlayer())){
					sendMessage(event.getPlayer(), "&cYou cant build inside a cell you do not own");
					event.setCancelled(true);
				} else {
					if(event.getBlock().getType().equals(Material.STONE_BUTTON)){
						sendMessage(event.getPlayer(), "&cYou cant place or break buttons");
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void blockBreak(BlockBreakEvent event){
		if(!event.getPlayer().hasPermission("PrisonCells.bypass")){
			Location blockloc = event.getBlock().getLocation();
			if(U.getCell(blockloc) == null){
				if(U.getPrison(blockloc) != null){
					sendMessage(event.getPlayer(), "&cYou cant build inside a prison");
					event.setCancelled(true);
				}
			} else {
				if(!U.canBuild((int) U.getCell(blockloc), event.getPlayer())){
					sendMessage(event.getPlayer(), "&cYou cant build inside a cell you do not own");
					event.setCancelled(true);
				} else {
					if(event.getBlock().getType().equals(Material.STONE_BUTTON)){
						sendMessage(event.getPlayer(), "&cYou cant place or break buttons");
						event.setCancelled(true);
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

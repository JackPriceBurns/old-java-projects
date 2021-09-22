package com.jack.prosigns.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.jack.prosigns.Prosigns;
import com.jack.prosigns.utils.Utils;

public class PlayerInteractListener implements Listener{

	private Prosigns plugin;
	private Utils U;
	
	public PlayerInteractListener(Prosigns p) {
		plugin = Prosigns.getInstance();
		U = plugin.getUtils();
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(event.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) event.getClickedBlock().getState();
				if(U.isProSign(sign)){
					String rank = sign.getLine(1);
					String price = sign.getLine(2);
					rank = rank.replaceAll(ChatColor.COLOR_CHAR + "[a-fA-F0-9]", "");
					price = price.replaceAll("\\$", "");
					if(!price.equals("Free") || !price.equals("free")){
						int tmp;
						try {
							tmp = Integer.parseInt(price);
						} catch (NumberFormatException e) {
							tmp = 0;
						}
						if(tmp == 0){
							U.sendMessage(event.getPlayer(), "&cThe sign you clicked has an invalid price.");
						} else {
							if(U.subtract(event.getPlayer(), tmp)){
								U.sendMessage(event.getPlayer(), "&aYou have been ranked up to &b" + rank + ".");
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuadd " + event.getPlayer().getName() + " " + rank);
							} else {
								U.sendMessage(event.getPlayer(), "&cYou do not have enough money for that rank.");
							}
						}
					} else {
						U.sendMessage(event.getPlayer(), "&aYou have been ranked up to " + rank + ".");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuadd " + event.getPlayer().getName() + " " + rank);
					}
				}
			}
		}
	}
}

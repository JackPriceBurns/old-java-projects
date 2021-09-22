package com.jack.alojm;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		event.setJoinMessage("");
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event){
		event.setQuitMessage("");
	}
}

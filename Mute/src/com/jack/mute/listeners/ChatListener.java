package com.jack.mute.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.jack.mute.Mute;
import com.jack.mute.utils.MetaUtils;
import com.jack.mute.utils.Utils;

public class ChatListener implements Listener{

	private Mute plugin;
	private MetaUtils MU;
	private Utils U;
	
	public ChatListener(Mute p) {
		plugin = p;
		MU = plugin.getMetaUtils();
		U = plugin.getUtils();
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		if(MU.hasMetaData(player, "muted")){
			U.sendMessage(player, "&6You are muted and cannot speak!");
			event.setCancelled(true);
		}
	}
}

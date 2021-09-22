package com.jack.essentials;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.jack.essentials.managers.ConfigManager;
import com.jack.essentials.managers.UserManager;

public class Listeners implements Listener {
	
	private UserManager UM;
	
	public Listeners(){
		UM = Essentials.getInstance().getUserManager();
	}

	// Player Join Listener
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		new User(event.getPlayer().getUniqueId());
	}
	 
	// Async Player Chat Listener
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) throws FileNotFoundException, IOException, InvalidConfigurationException{
		event.setFormat("%2$s");
		Player player = event.getPlayer();
		String message = event.getMessage();
		ConfigManager cm = Essentials.getInstance().getConfigManager();
		User user = UM.getUser(player.getUniqueId());
		String name = user.getDisplayName();
		PermissionUser pexuser = PermissionsEx.getUser(player);
		String prefix = pexuser.getPrefix();
		String suffix = pexuser.getSuffix();
		
		if(!cm.getChatFormat().equals("")){
			String chatFormat = (String) cm.getChatFormat();
			if(player.isOp()){
				if(!cm.getOpColor().equals("")){
					name = cm.getOpColor() + name;
				}
			}
			
			chatFormat = chatFormat.replaceAll("\\{prefix\\}", prefix);
			chatFormat = chatFormat.replaceAll("\\{suffix\\}", suffix);
			chatFormat = chatFormat.replaceAll("\\{player\\}", name);
			chatFormat = chatFormat.replaceAll("&", "§");
			chatFormat = chatFormat.replaceAll("\\{message\\}", message);
			
			event.setMessage(chatFormat);
		} else {
			event.setFormat("<" + name + "> %2$s");
		}
	}
}

package com.jack.mute.utils;

import org.bukkit.command.CommandSender;

public class Utils {
	
	public void sendMessage(CommandSender player, String str){
		str = str.replaceAll("&", "§");
		player.sendMessage(str);
	}
}

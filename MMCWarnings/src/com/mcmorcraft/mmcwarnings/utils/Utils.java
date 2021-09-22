package com.mcmorcraft.mmcwarnings.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {
	
	public String colorise(String str){
		str = str.replaceAll("&", "§");
		return str;
	}
	
	public void help(Player player) {
		player.sendMessage(colorise("&9-[&6MMC Warnings&9]- &6Help"));
		player.sendMessage(colorise("&9- &6Reporting Player &9/report <username> <points> <reason>"));
		player.sendMessage(colorise("&9- &6Lookup Player &9/userinfo <username>"));		
	}
	
	public void message(CommandSender player, String str){
		player.sendMessage(colorise(str));
	}
}

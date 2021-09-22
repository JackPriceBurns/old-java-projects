package com.jack.mages.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlayerUtils {
	
	public static boolean isMage(String name){
		Player player = null;
		for(World w : Bukkit.getWorlds()){
			for(Player p : w.getPlayers()){
				if(p.getName().equals(name)){
					player = p;
				}
			}
		}
		if(player == null){
			return false;
		} else {
			return true;
		}
	}
}

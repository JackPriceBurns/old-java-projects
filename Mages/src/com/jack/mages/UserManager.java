package com.jack.mages;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import com.jack.mages.utils.Utils;

public class UserManager {
	
	private Database DB;
	private Utils U;
	
	public UserManager(){
		DB = Mages.getPlugin().getDB();
		U = Mages.getPlugin().getUtils();
	}
	
	public boolean isMage(String name){
		if(U.isOnline(name)){
			Player player = U.getPlayer(name);
			if(player.hasPermission("Mage")){
				return true;
			}
		}
		return false;
	}
	
	public String getType(String name){
		if(isMage(name)){
			Player player = U.getPlayer(name);
			if(player.hasPermission("Mage.Novice")){
				return "Novice";
			} else {
				if(player.hasPermission("Mage.Adept")){
					return "Adept";
				} else {
					if(player.hasPermission("Mage.Master")){
						return "Master";
					} else {
						return null;
					}
				}
			}
		} else {
			return null;
		}
	}
	
	public int getLevel(String name){
		if(isMage(name)){
			ResultSet result = DB.getUser(name);
			try {
				while(result.first()){
					return result.getInt("level");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
}

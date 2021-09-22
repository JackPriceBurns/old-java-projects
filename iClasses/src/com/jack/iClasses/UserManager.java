package com.jack.iclasses;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class UserManager {

	private Set<User> usersOnline;
	
	public UserManager(){
		usersOnline = new HashSet<User>();
	}
	
	public User getUser(UUID uuid){
		checkUsers();
		for(User user : usersOnline){
			if(user.getUUID().equals(uuid)){
				return user;
			}
		}
		return null;
	}
	
	public void checkUsers(){
		usersOnline.clear();
		for(World w : Bukkit.getWorlds()){
			for(Player p : w.getPlayers()){
				usersOnline.add(new User(p.getUniqueId()));
			}
		}
	}
}

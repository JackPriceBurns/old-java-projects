package com.jack.essentials.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.jack.essentials.User;

public class UserManager {
	
	Map<UUID, User> Users;
	
	public UserManager(){
		Users = new HashMap<UUID, User>();
	}
	
	public User getUser(UUID uuid){
		if(!Users.containsKey(uuid)){
			Users.put(uuid, new User(uuid));
		}
		return Users.get(uuid);
	}
	
	public void reloadUsers(){
		Users.clear();
	}
}

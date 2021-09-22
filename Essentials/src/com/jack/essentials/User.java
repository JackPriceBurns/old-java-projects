package com.jack.essentials;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jack.essentials.managers.FileManager;
import com.tonian.director.dm.json.JSonWriter;

public class User {

	private FileManager FM;
	private File userFile;
	private JSONObject user;
	private String name;
	
	@SuppressWarnings("unchecked")
	public User(UUID uuid){
		FM = Essentials.getInstance().getFileManager();
		userFile = new File(FM.getUserDataFolder(), uuid.toString() + ".yml");
		name = Bukkit.getServer().getPlayer(uuid).getName();
		
		if(!userFile.exists()){
			JSONObject defaultUser = new JSONObject();
			defaultUser.put("Name", name);
			defaultUser.put("Home", "-1");
			defaultUser.put("Nick", "-1");
			Writer writer = new JSonWriter();
			try {
				defaultUser.writeJSONString(writer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			FM.createFile(userFile);
			FM.fileAppend(userFile, writer.toString());
			
			String msg = (String) Essentials.getInstance().getConfigManager().getWelcomeMessage();
			
			msg = msg.replaceAll("\\{player\\}", name);
			msg = msg.replaceAll("&", "§");
			
			Bukkit.broadcastMessage(msg);
		}
		
		reloadUser();
	}

	private void reloadUser(){
		String configFromFile = FM.readFile(userFile);
		JSONParser parser = new JSONParser();
		Object object;
		try {
			object = parser.parse(configFromFile);
			JSONObject jsonobject = (JSONObject)object;
			user = jsonobject;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void saveUser(){
		Writer writer = new JSonWriter();
		try {
			user.writeJSONString(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		FM.fileReplaceContents(userFile, writer.toString());
	}

	public String getRealName(){
		return name;
	}
	
	public String getDisplayName(){
		String name = "";
		if(!user.get("Nick").equals("-1") && !user.get("Nick").equals("")){
			name = (String) user.get("Nick");
		} else {
			name = this.name;
		}
		return name;
	}
	
	public String getHome(){
		return (String) user.get("Home");
	}
	
	@SuppressWarnings("unchecked")
	public void setHome(Location home){
		String homeloc = home.getX() + ":" + home.getY() + ":" + home.getZ() + ":" + home.getYaw() + ":" + home.getPitch();
		user.remove("Home");
		user.put("Home", homeloc);
		saveUser();
	}
	
	public boolean isHomeless(){
		if(!user.get("Home").equals("-1") && !user.get("Home").equals("")){
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean setNick(String nick){
		if(!Bukkit.getPlayer(name).isOp()){
			if(!(nick.replaceAll("&[0-9a-f]", "").length() >= 3)){
				return false;
			}
		}
		
		boolean found = false;
		for(World w : Bukkit.getWorlds()){
			for(Player p : w.getPlayers()){
				if(nick.replaceAll("&[0-9a-f]", "").equals(p.getName()) && !p.getName().equals(name)){
					found = true;
				}
			}
		}
		
		if(found){
			return false;
		} else {
			user.remove("Nick");
			user.put("Nick", nick);
			saveUser();
			return true;
		}
	}
}

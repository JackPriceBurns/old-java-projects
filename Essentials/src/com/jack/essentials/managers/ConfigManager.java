package com.jack.essentials.managers;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.bukkit.Location;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tonian.director.dm.json.JSonWriter;

public class ConfigManager {
	
	private FileManager FM = new FileManager();
	private File configFile;
	private JSONObject config;
	
	@SuppressWarnings("unchecked")
	public ConfigManager(){
		configFile = FM.getConfigFile();
		if(!configFile.exists()){
			JSONObject defaultConfig = new JSONObject();
			defaultConfig.put("ChatFormat", "&f<{prefix}&f{player}&f{suffix}&f> {message}");
			defaultConfig.put("WelcomeMessage", "&9Hello {player}, welcome to Inviktuz :D");
			defaultConfig.put("Spawn", "0:100:0:0:0");
			defaultConfig.put("Debug", "false");
			defaultConfig.put("OpColor", "&c");
			Writer writer = new JSonWriter();
			try {
				defaultConfig.writeJSONString(writer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			FM.createFile(configFile);
			FM.fileAppend(configFile, writer.toString());
		}
		reloadConfig();
	}
	
	public void reloadConfig(){
		String configFromFile = FM.readFile(configFile);
		JSONParser parser = new JSONParser();
		Object object;
		try {
			object = parser.parse(configFromFile);
			JSONObject jsonobject = (JSONObject)object;
			config = jsonobject;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void saveConfig(){
		Writer writer = new JSonWriter();
		try {
			config.writeJSONString(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		FM.fileReplaceContents(configFile, writer.toString());
	}

	public String getSpawn() {
		return (String) config.get("Spawn");
	}

	@SuppressWarnings("unchecked")
	public void setSpawn(Location spawn) {
		String spawnloc = spawn.getX() + ":" + spawn.getY() + ":" + spawn.getZ() + ":" + spawn.getYaw() + ":" + spawn.getPitch();
		config.remove("Spawn");
		config.put("Spawn", spawnloc);
		saveConfig();
	}

	public Object getChatFormat() {
		return config.get("ChatFormat");
	}
	
	public Object getWelcomeMessage(){
		return config.get("WelcomeMessage");
	}

	public Object getOpColor() {
		return config.get("OpColor");
	}
	
	public boolean isInDebug(){
		if(config.get("Debug").equals("true")){
			return true;
		} else {
			return false;
		}
	}
}

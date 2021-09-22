package com.jack.iclasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

public class User {
	
	private FileConfiguration config;
	private File userFile;
	
	private UUID uuid;
	private int type;
	
	public User(UUID uuid){
		this.uuid = uuid;
		
		config = IClasses.getInstance().getConfig();
		userFile = IClasses.getInstance().getFileManager().getUserFile(uuid);
		
		IClasses.getInstance().getFileManager().eraseConfigSets();
		try {
			config.load(userFile);
			type = config.getInt("User.Type");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getPrefix(){
		try {
			IClasses.getInstance().getFileManager().eraseConfigSets();
			config.load(IClasses.getInstance().getFileManager().getConfigFile());
			if(config.getString("Prefixes." + type).equals("")){
				return "&8[&7Unassigned&8]&7";
			} else {
				return config.getString("Prefixes." + type);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "&8[&7Unassigned&8]&7";
		} catch (IOException e) {
			e.printStackTrace();
			return "&8[&7Unassigned&8]&7";
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			return "&8[&7Unassigned&8]&7";
		}
	}
	
	public int getType(){
		return type;
	}
	
	public UUID getUUID(){
		return uuid;
	}

}

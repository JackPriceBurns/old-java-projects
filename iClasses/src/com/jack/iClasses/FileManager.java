package com.jack.iclasses;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class FileManager {

	private File itemData;
	private File dataFolder;
	private File userData;
	private File configFile;
	private Logger logger;
	private FileConfiguration config;
	
	public FileManager(){
		dataFolder = IClasses.getInstance().getDataFolder();
		userData = new File(dataFolder.getPath(), "UserData");
		itemData = new File(dataFolder.getPath(), "ItemData");
		configFile = new File(dataFolder.getPath(), "config.yml");
		logger = IClasses.getInstance().getLogger();
		config = IClasses.getInstance().getConfig();
		
		CheckFiles();
		CheckUsers();
	}

	public void CheckUsers(){
		for(World w : Bukkit.getWorlds()){
			for(Player p : w.getPlayers()){
				CheckUser(p.getUniqueId());
			}
		}
	}
	
	public File getUserFile(UUID uuid){
		File user = new File(userData.getPath() + File.pathSeparator + uuid.toString() + ".yml");
		if(!user.exists()){
			CheckUser(uuid);
		}
		return user;
	}
	
	public boolean CheckItem(UUID uuid){
		File item = new File(itemData.getAbsolutePath() + File.pathSeparator + uuid.toString() + ".yml");
		if(!item.exists()){
			return false;
		}
		return true;
	}
	
	public void CheckUser(UUID uuid){
		File user = new File(userData.getAbsolutePath() + File.pathSeparator + uuid.toString() + ".yml");
		if(!user.exists()){
			logger.info("User doesn't exist in database. Creating new database entry for user.");
			try {
				user.createNewFile();
				try {
					config.load(user);
					config.set("User.Name", Bukkit.getPlayer(uuid).getName());
					config.set("User.Type", 0);
					config.set("User.Level", 1);
					config.set("User.Skills.Sword", 0);
					config.set("User.Skills.Archery", 0);
					config.set("User.Skills.Axe", 0);
					config.set("User.Skills.Defence", 1);
					config.set("User.Skills.Cooking", 1);
					config.set("User.Skills.Stealth", 1);
					config.set("User.Skills.Perception", 1);
					config.set("User.Skills.Brewing", 1);
					config.save(user);
					eraseConfigSets();
				} catch (InvalidConfigurationException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void eraseConfigSets() {
		for(String str : config.getKeys(false)){
			config.set(str, null);
		}
	}

	private void CheckFiles(){
		if(!dataFolder.exists()){
			logger.info("Data Folder for iClasses could not be found.");
			logger.info("Generating new one. (),.,.,()");
			dataFolder.mkdir();
		}
		if(!userData.exists()){
			logger.info("User Data Folder for iClasses could not be found.");
			logger.info("Generating new one. (),.,.,()");
			userData.mkdir();
		}
		if(!itemData.exists()){
			logger.info("Item Data Folder for iClasses could not be found.");
			logger.info("Generating new one. (),.,.,()");
			itemData.mkdir();
		}
		if(!configFile.exists()){
			logger.info("Config File for iClasses could not be found.");
			logger.info("Generating new one. (),.,.,()");
			try {
				configFile.createNewFile();
				try {
					config.load(configFile);
					config.set("Prefixes.4", "&3[&bArcher&3]&b");
					config.set("Prefixes.3", "&3[&bWorrior&3]&b");
					config.set("Prefixes.2", "&3[&bRogue&3]&b");
					config.set("Prefixes.1", "&3[&bMage&3]&b");
					config.set("Prefixes.0", "&8[&7Unassigned&8]&7");
					config.save(configFile);
					eraseConfigSets();
				} catch (InvalidConfigurationException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				logger.info("Failed to generate config file :O");
			}
		}
	}

	public File getConfigFile() {
		return configFile;
	}
}

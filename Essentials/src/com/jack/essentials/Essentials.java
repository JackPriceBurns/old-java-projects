package com.jack.essentials;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.jack.essentials.commands.EssentialsCommand;
import com.jack.essentials.commands.GMCommand;
import com.jack.essentials.commands.HomeCommand;
import com.jack.essentials.commands.NickCommand;
import com.jack.essentials.commands.SpawnCommand;
import com.jack.essentials.commands.TpCommand;
import com.jack.essentials.commands.WarpCommand;
import com.jack.essentials.managers.ConfigManager;
import com.jack.essentials.managers.FileManager;
import com.jack.essentials.managers.UserManager;
import com.jack.essentials.managers.WarpManager;

public class Essentials extends JavaPlugin {
	
	private static Essentials plugin;
	private FileManager fileManager;
	private ConfigManager configManager;
	private UserManager userManager;
	private WarpManager warpManager;
	
	@Override
	public void onEnable(){
		plugin = this;
		
		Bukkit.getPluginManager().registerEvents(new Listeners(), this);
		
		getCommand("spawn").setExecutor(new SpawnCommand());
		getCommand("setspawn").setExecutor(new SpawnCommand());
		getCommand("warp").setExecutor(new WarpCommand());
		getCommand("warps").setExecutor(new WarpCommand());
		getCommand("setwarp").setExecutor(new WarpCommand());
		getCommand("delwarp").setExecutor(new WarpCommand());
		getCommand("home").setExecutor(new HomeCommand());
		getCommand("sethome").setExecutor(new HomeCommand());
		getCommand("tp").setExecutor(new TpCommand());
		getCommand("tphere").setExecutor(new TpCommand());
		getCommand("essentials").setExecutor(new EssentialsCommand());
		getCommand("nick").setExecutor(new NickCommand());
		getCommand("gm").setExecutor(new GMCommand());
		
		fileManager = new FileManager();
		configManager = new ConfigManager();
		userManager = new UserManager();
		warpManager = new WarpManager();
		
		getLogger().info("has been Enabled!");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("has been Disabled!");
	}
	
	public FileManager getFileManager(){
		return fileManager;
	}
	
	public ConfigManager getConfigManager(){
		return configManager;
	}
	
	public UserManager getUserManager() {
		return userManager;
	}
	
	public WarpManager getWarpManager() {
		return warpManager;
	}
	
	public static Essentials getInstance(){
		return plugin;
	}
}
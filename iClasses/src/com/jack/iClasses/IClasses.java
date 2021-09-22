package com.jack.iclasses;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.jack.iclasses.commands.TestCommand;

public class IClasses extends JavaPlugin {
	
	private UserManager UM;
	private FileManager FM;
	private static IClasses plugin;
	
	@Override
	public void onEnable(){
		plugin = this;
		
		Bukkit.getPluginManager().registerEvents(new Listeners(), this);
		
		getFileManager();
		getUserManager();
		getUserManager().checkUsers();
		
		getCommand("Test").setExecutor(new TestCommand());
		
		getLogger().info("has been Enabled!");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("has been Disabled!");		
	}
	
	public static IClasses getInstance(){
		return plugin;
	}
	
	public UserManager getUserManager(){
		if(UM == null){
			UM = new UserManager();
		}
		return UM;
	}

	public FileManager getFileManager() {
		if(FM == null){
			FM = new FileManager();
		}
		return FM;
	}
}

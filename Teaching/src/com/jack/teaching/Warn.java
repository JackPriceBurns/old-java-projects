package com.jack.teaching;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.jack.teaching.commands.AdvCommand;
import com.jack.teaching.listeners.PlayerInteractListener;
import com.jack.teaching.utils.Utils;

public class Warn extends JavaPlugin{

	public void onEnable(){
		
		getCommand("adv").setExecutor(new AdvCommand(this));
		
		Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
		
		getLogger().info("has been enabled!");
	}
	
	public void onDisable(){
		getLogger().info("has been disabled!");
	}

	public Utils getUtils() {
		// TODO Auto-generated method stub
		return null;
	}
}

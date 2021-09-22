package com.jack.alojm;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ALOJM extends JavaPlugin {

	@Override
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(new Listeners(), this);
	}
	
	@Override
	public void onDisable(){
		
	}
}

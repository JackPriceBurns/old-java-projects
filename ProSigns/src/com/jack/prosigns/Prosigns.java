package com.jack.prosigns;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.jack.prosigns.listeners.BlockPlaceListener;
import com.jack.prosigns.listeners.PlayerInteractListener;
import com.jack.prosigns.utils.Utils;

public class Prosigns extends JavaPlugin{
	private static Prosigns plugin;
	private Utils U;
	
	@Override
	public void onEnable(){
		
		plugin = this;
		
		Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		
		getLogger().info("has been enabled!");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("has been disabled!");
	}
	
	public Utils getUtils(){
		if(U == null){
			U = new Utils();
		}
		return U;
	}

	public static Prosigns getInstance() {
		return plugin;
	}
}

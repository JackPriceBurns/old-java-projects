package com.jack.mute;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.jack.mute.commands.MuteCommand;
import com.jack.mute.listeners.ChatListener;
import com.jack.mute.utils.MetaUtils;
import com.jack.mute.utils.Utils;

public class Mute extends JavaPlugin{

	private MetaUtils MU;
	private Utils U;
	
	@Override
	public void onEnable(){
		
		getCommand("mute").setExecutor(new MuteCommand(this));
		
		Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
		
		getLogger().info("has been enabled!");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("has been disabled!");
	}
	
	public MetaUtils getMetaUtils(){
		if(MU == null){
			MU = new MetaUtils(this);
		}
		return MU;
	}
	
	public Utils getUtils(){
		if(U == null){
			U = new Utils();
		}
		return U;
	}
}

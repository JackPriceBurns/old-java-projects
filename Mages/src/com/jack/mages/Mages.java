package com.jack.mages;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.jack.mages.commands.WandCommand;
import com.jack.mages.listeners.Listeners;
import com.jack.mages.utils.MetaUtils;
import com.jack.mages.utils.Utils;
import com.jack.mages.Database;

public class Mages extends JavaPlugin {

	private static Mages plugin;
	private Wands W;
	private Utils U;
	private MetaUtils MU;
	private Database DB;
	private UserManager UM;
	
	@Override
	public void onEnable(){
		plugin = this;
		
		getCommand("wand").setExecutor(new WandCommand());
		getCommand("w").setExecutor(new WandCommand());

		Bukkit.getPluginManager().registerEvents(new Listeners(), this);

		getLogger().info("has been Enabled!");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("has been Disabled!");
	}
	
	public static Mages getPlugin(){
		return plugin;
	}
	
	public Wands getWands(){
		if(W == null){
			W = new Wands();
		}
		return W;
	}
	
	public Utils getUtils(){
		if(U == null){
			U = new Utils();
		}
		return U;
	}
	
	public MetaUtils getMetaUtils(){
		if(MU == null){
			MU = new MetaUtils();
		}
		return MU;
	}
	
	public UserManager getUserManager(){
		if(UM == null){
			UM = new UserManager();
		}
		return UM;
	}
	
	public Database getDB(){
		if(DB == null){
			DB = new Database(null, null, null, null, null);
		}
		return DB;
	}
}

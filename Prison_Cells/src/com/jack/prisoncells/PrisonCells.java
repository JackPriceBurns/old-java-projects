package com.jack.prisoncells;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.jack.prisoncells.commands.PCCommand;
import com.jack.prisoncells.commands.PrisonCellsCommand;
import com.jack.prisoncells.listeners.BlockListener;
import com.jack.prisoncells.listeners.PlayerInteractListener;
import com.jack.prisoncells.utils.MetaUtils;
import com.jack.prisoncells.utils.PlayerUtils;
import com.jack.prisoncells.utils.Utils;

public class PrisonCells extends JavaPlugin{
	
	private Database DB;
	private MetaUtils MU;
	private PlayerUtils PU;
	private Utils U;
	
	@Override
	public void onEnable(){
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		getCommand("prisoncells").setExecutor(new PrisonCellsCommand(this));
		getCommand("pc").setExecutor(new PCCommand(this));
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		getServer().getPluginManager().registerEvents(new BlockListener(this), this);
		
		DB = getDB();
		
		if(Bukkit.getPluginManager().isPluginEnabled(this)){
			getLogger().info("has been Enabled!");
		}
	}
	
	@Override
	public void onDisable(){
		getLogger().info("has been Disabled!");
	}
	
	public Database getDB(){
		if(DB == null){
			String host = getConfig().getString("MySQL.host");
			String user = getConfig().getString("MySQL.user");
			String pass = getConfig().getString("MySQL.pass");
			String db = getConfig().getString("MySQL.db");
			DB = new Database(host, user, pass, db, this);
		}
		return DB;
	}
	
	public MetaUtils getMU(){
		if(MU == null){
			MU = new MetaUtils(this);
		}
		return MU;
	}
	
	public PlayerUtils getPU(){
		if(PU == null){
			PU = new PlayerUtils(this);
		}
		return PU;
	}
	
	public Utils getUtils(){
		if(U == null){
			U = new Utils(this);
		}
		return U;
	}
}

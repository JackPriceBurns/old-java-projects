package com.mcmorcraft.mmcwarnings;

import org.bukkit.plugin.java.JavaPlugin;

import com.mcmorcraft.mmcwarnings.commands.ReportCommand;
import com.mcmorcraft.mmcwarnings.commands.UserinfoCommand;
import com.mcmorcraft.mmcwarnings.utils.Utils;

public class Warnings extends JavaPlugin{
	
	private Utils U;
	private Database DB;
	
	@Override
	public void onEnable(){
		
		getCommand("report").setExecutor(new ReportCommand(this));
		getCommand("userinfo").setExecutor(new UserinfoCommand(this));
		
		getLogger().info("has been enabled!");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("has been disabled!");
	}
	
	public Database getDB(){
		if(DB == null){
			DB = new Database("localhost", "3306", "root", "UtilitarianisingHaemagglutinationsUnfertilizableTradeoff");
		}
		return DB;
	}
	
	public Utils getUtils(){
		if(U == null){
			U = new Utils();
		}
		return U;
	}
}
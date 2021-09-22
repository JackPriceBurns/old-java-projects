package com.jack.rankup;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Rankup extends JavaPlugin{
	
	private static Rankup plugin;
	
	private Economy econ;
	private Permission perms;
	private Object[][] Ranks;
	
	@Override
	public void onEnable(){
		plugin = this;
		
		Ranks = new Object[7][2];
		
		Ranks[0][0] = "Novice";Ranks[0][1] = 0.0;
		Ranks[1][0] = "Apprentice";Ranks[1][1] = 10000.0;
		Ranks[2][0] = "Adept";Ranks[2][1] = 50000.0;
		Ranks[3][0] = "Expert";Ranks[3][1] = 100000.0;
		Ranks[4][0] = "Master";Ranks[4][1] = 250000.0;
		Ranks[5][0] = "Elite";Ranks[5][1] = 1000000.0;
		Ranks[6][0] = "Supreme";Ranks[6][1] = 2500000.0;

		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        econ = rsp.getProvider();
		RegisteredServiceProvider<Permission> rspp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rspp.getProvider();
        
		getServer().getPluginManager().registerEvents(new Listeners(), this);
		getCommand("rankup").setExecutor(new CommandExecutor());
		getLogger().info("has been Enabled!");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("has been Disabled!");
	}
	
	public static Rankup getInstance(){
		return plugin;
	}

	public void RankupUser(Player player) {
		String playersGroup = perms.getPrimaryGroup(player);
		int x = 0;
		for(Object[] array : Ranks){
			String rank = (String) array[0];
			if(playersGroup.equalsIgnoreCase(rank)){
				try {
					String nextGroup = (String) Ranks[x+1][0];
					double price = (double) Ranks[x+1][1];
					if(econ.getBalance(player) >= price){
						econ.bankWithdraw(player.getName(), price);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "pex user " + player.getName() + " group set " + nextGroup);
						player.sendMessage("You have been ranked up too " + nextGroup + ", congratulations!.");
						player.sendMessage("$" + price + " has been deducted from your account.");
						return;
					} else {
						player.sendMessage("You need $" + price + " for " + nextGroup + ".");
						return;
					}
				} catch(ArrayIndexOutOfBoundsException exception) {
					
				}
			}
			x = x + 1;
		}
		player.sendMessage("There are no more ranks.");
	}
}

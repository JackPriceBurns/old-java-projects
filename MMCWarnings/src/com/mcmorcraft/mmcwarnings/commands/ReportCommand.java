package com.mcmorcraft.mmcwarnings.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcmorcraft.mmcwarnings.Database;
import com.mcmorcraft.mmcwarnings.Warnings;
import com.mcmorcraft.mmcwarnings.utils.Utils;

public class ReportCommand implements CommandExecutor {

	private Warnings p;
	private Database DB;
	private Utils U;
	
	public ReportCommand(Warnings p){
		this.p = p;
		this.DB = this.p.getDB();
		this.U = this.p.getUtils();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("report")) {
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 0){
					U.help(player);
				} else {
					if(args.length > 2){
						Player player1 = null;
						for(World w : Bukkit.getServer().getWorlds()){
							for(Player p : w.getPlayers()){
								if(p.getName().equals(args[0])){
									player1 = p;
								}
							}
						}
						if(player1 != null){
							int tmp;
							try {
								tmp = Integer.parseInt(args[1]);
							} catch (NumberFormatException e) {
								tmp = 0;
							}
							if(tmp == 0 || tmp > 100){
								U.message(player, "&cPoints must be a number and between 0 and 101");
							} else {
								String reason = "";
								int tmp1 = 0; 
								for(Object stuff : args){
									tmp1++;
									if(!(tmp1 == 1 || tmp1 == 2)){
										reason = reason + stuff + " ";
									}
								}
								reason.trim();
								Boolean result = DB.insertWarning(args[0], reason, tmp, player.getName());
								if(!result){
									U.message(player, "&aReport successfully added");
									ResultSet warnings = DB.getWarnings(args[0]);
									U.message(player1, "&6You have been reported for '&c" + reason + "&6' by " + player.getName());
									try {
										String banreason = "";
										int points = 0;
										while(warnings.next()){
											points = points + warnings.getInt("points");
											banreason = banreason + warnings.getString("reason") + ", ";
										}
										banreason.trim();
										if(points >= 100){
											Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ban " + player1.getName() + " " + banreason);
										} else if(points >= 75){
											Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "kick " + player1.getName() + " " + banreason);
										} else if(points >= 50){
											Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mute " + player1.getName() + " 120");
										}
									} catch (SQLException e) {
										e.printStackTrace();
									}
								} else {
									U.message(player, "&cToo many arguments.");
								}
							}
						} else {
							U.message(player, "&cToo many arguments.");
						}
					} else {
						U.message(player, "&cToo many arguments.");
					}
				}
			} else {
				U.message(sender, "&cYou must be a player to send this command");
			}
			return true;
		}
		return true;
	}
}

package com.mcmorcraft.mmcwarnings.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcmorcraft.mmcwarnings.Database;
import com.mcmorcraft.mmcwarnings.Warnings;
import com.mcmorcraft.mmcwarnings.utils.Utils;

public class UserinfoCommand implements CommandExecutor {
	
	private Warnings p;
	private Database DB;
	private Utils U;
	
	public UserinfoCommand(Warnings p){
		this.p = p;
		this.DB = this.p.getDB();
		this.U = this.p.getUtils();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {	
		if (cmd.getName().equalsIgnoreCase("userinfo")) {
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 0){
					U.help(player);
				} else {
					if(args.length == 1){
						if(DB.countWarnings(args[0]) == 0){
							U.message(player, "&9-[&6MMC Warnings&9]- &6Reports for " + args[0]);
							U.message(player, "&6" + args[0] + ", has no reports");
						} else {
							ResultSet warnings = DB.getWarnings(args[0]);
							try {
								U.message(player, "&9-[&6MMC Warnings&9]- &6Reports for " + args[0]);
								int id = 0;
								while(warnings.next()){
									id++;
									String reason = warnings.getString("reason").trim();
									String issuer = warnings.getString("issuer").trim();
									int points = warnings.getInt("points");
									String reasoncolor = "";
									if(warnings.getInt("points") >= 50){
										reasoncolor = "&c";
									} else if(warnings.getInt("points") >= 25){
										reasoncolor = "&6";
									} else if(warnings.getInt("points") >= 0){
										reasoncolor = "&e";
									}
									U.message(player, "&6#&9" + id + "&6 Reported for: '" + reasoncolor + reason + "&6' By " + issuer + " &9[" + reasoncolor + points + "&9]");
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					} else {
						U.message(player, "&cToo many arguments");
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

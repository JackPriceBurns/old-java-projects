package com.jack.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpCommand implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tp")){
			if(sender.hasPermission("essentials.tp")){
				if(args.length != 1){
					return false;
				}
				
				if(!(sender instanceof Player)){
					sender.sendMessage("Only players can use Tp");
				} else {
					Player player = (Player)sender;
					Player found = null;
					for(World w : Bukkit.getWorlds()){
						for(Player p : w.getPlayers()){
							if(p.getName().equals(args[0])){
								found = p;
							}
						}
					}
					
					if(found == null){
						player.sendMessage(args[0] + " is not online.");
					} else {
						player.sendMessage("Teleporting to " + args[0]);
						player.teleport(found.getLocation());
					}
				}
			} else {
				sender.sendMessage("You do not have permission.");
			}
		} else if(cmd.getName().equalsIgnoreCase("tphere")){
			if(sender.hasPermission("essentials.tphere")){
				if(args.length != 1){
					return false;
				}
					
				if(!(sender instanceof Player)){
					sender.sendMessage("Only players can use Tphere");
				} else {
					Player player = (Player)sender;
					Player found = null;
					for(World w : Bukkit.getWorlds()){
						for(Player p : w.getPlayers()){
							if(p.getName().equals(args[0])){
								found = p;
							}
						}
					}
					
					if(found == null){
						player.sendMessage(args[0] + " is not online.");
					} else {
						player.sendMessage("Teleporting " + args[0] + " to you");
						found.teleport(player.getLocation());
					}
				}
			} else {
				sender.sendMessage("You do not have permission.");
			}
		}
		return true;
	}
}

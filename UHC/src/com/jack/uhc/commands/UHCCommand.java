package com.jack.uhc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UHCCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("uhc")){
			if(sender instanceof Player){
				//Player player = (Player) sender;
				
			} else {
				sender.sendMessage("Sorry Nope");
			}
		}
		return true;		
	}
}

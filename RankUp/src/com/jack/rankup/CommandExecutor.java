package com.jack.rankup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("rankup")) {
			if(sender instanceof Player){
				Player player = (Player) sender;
				Rankup.getInstance().RankupUser(player);
			} else {
				sender.sendMessage("User only!");
			}
			return true;
		}
		return false;
	}

}

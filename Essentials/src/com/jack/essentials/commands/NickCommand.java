package com.jack.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jack.essentials.Essentials;
import com.jack.essentials.User;

public class NickCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("nick")){
			if(sender.hasPermission("essentials.nick")){
				if(args.length != 1){
					return false;
				}
				
				if(!(sender instanceof Player)){
					sender.sendMessage("Only players can use Nick");
				} else {
					Player player = (Player)sender;
					User user = Essentials.getInstance().getUserManager().getUser(player.getUniqueId());
					if(user.setNick(args[0])){
						player.sendMessage("Nickname successfully changed to " + args[0]);
					} else {
						player.sendMessage("Nickname was not changed, nicknames must be 3 or more characters long and must not be somebody elses name.");
					}
				}
			} else {
				sender.sendMessage("You do not have permission.");
			}
		}
		return true;
	}
}

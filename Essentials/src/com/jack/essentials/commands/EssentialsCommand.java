package com.jack.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.jack.essentials.Essentials;

public class EssentialsCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("essentials")){
			if(sender.hasPermission("essentials.admin")){
				if(args.length == 0){
					showHelp(sender);
					return true;
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("reload")){
						Essentials.getInstance().getConfigManager().reloadConfig();
						sender.sendMessage("Essentials config has been reloaded");
						Essentials.getInstance().getWarpManager().reloadWarps();
						sender.sendMessage("Essentials warps have been reloaded");
						Essentials.getInstance().getUserManager().reloadUsers();
						sender.sendMessage("Essentials users have been reloaded");
						sender.sendMessage("Essentials has been reloaded");
						return true;
					} else {
						showHelp(sender);
						return true;
					}
				} else {
					return false;
				}
			} else {
				sender.sendMessage("You do not have permission.");
				return true;
			}
		}
		return true;
	}

	private void showHelp(CommandSender sender) {
		sender.sendMessage("Essentials Help");
		sender.sendMessage("/essentials reload - Reloads the config warps and users.");
	}
}

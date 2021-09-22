package com.jack.essentials.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GMCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("gm")){
			if(sender.hasPermission("essentials.gm")){
				if(!(sender instanceof Player)){
					sender.sendMessage("Only players can use GM");
				} else {
					Player player = (Player)sender;
					if(args.length == 0){
						showHelp(sender);
						return true;
					} else if(args.length == 1) {
						if(args[0].equals("0")){
							player.setGameMode(GameMode.SURVIVAL);
							player.sendMessage("GameMode set to Survival");
						} else if(args[0].equals("1")) {
							player.setGameMode(GameMode.CREATIVE);
							player.sendMessage("GameMode set to Creative");
						} else if(args[0].equals("2")) {
							player.setGameMode(GameMode.ADVENTURE);
							player.sendMessage("GameMode set to Adventure");
						} else if(args[0].equals("3")) {
							player.setGameMode(GameMode.SPECTATOR);
							player.sendMessage("GameMode set to Spectator");
						} else {
							player.sendMessage(args[0] + " is not a valid gamemode.");
						}
					} else {
						showHelp(sender);
						return true;
					}
				}
			} else {
				sender.sendMessage("You do not have permission.");
			}
		}
		return true;
	}

	private void showHelp(CommandSender sender) {
		sender.sendMessage("/gm {gamemode}");
		sender.sendMessage("0 = Survival; 1 = Creative; 2 = Adventure; 3 = Spectator");
	}
}

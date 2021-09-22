package com.jack.uhc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jack.uhc.UHC;

public class VoteCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("vote")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length != 1){
					help(player);
				} else {
					if(args[0].equalsIgnoreCase("yes") || args[0].equalsIgnoreCase("no")){
						if(args[0].equalsIgnoreCase("yes")){
							player.sendMessage("You voted to start the game");
							UHC.getPlugin().getVotes().addVote(player.getName(), true);
						} else {
							player.sendMessage("You voted to not start the game");
							UHC.getPlugin().getVotes().addVote(player.getName(), false);
						}
					} else {
						help(player);
					}
				}
			} else {
				sender.sendMessage("Sorry Nope");
			}
		}
		return true;
	}

	private void help(Player player) {
		player.sendMessage("Voting:");
		player.sendMessage("Type '/vote yes' to vote to start a game");
		player.sendMessage("Type '/vote no' to vote to not start a game");
	}
}

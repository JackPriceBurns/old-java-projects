package com.jack.uhc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jack.uhc.UHC;
import com.jack.uhc.utils.MetaUtils;

public class TeamChatCommand implements CommandExecutor {

	private MetaUtils MU;
	
	public TeamChatCommand(){
		MU = UHC.getPlugin().getMetaUtils();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("teamchat")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(MU.hasMetadata(player, "TeamChat")){
					player.sendMessage("TeamChat has been disabled!");
					MU.removeMetaData(player, "TeamChat");
				} else {
					player.sendMessage("TeamChat has been enabled!");
					MU.setMetaData(player, "TeamChat", true);
				}
			} else {
				sender.sendMessage("Sorry Nope");
			}
		}
		return false;
	}

	
	
}

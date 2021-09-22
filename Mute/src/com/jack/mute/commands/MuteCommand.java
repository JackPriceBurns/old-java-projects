package com.jack.mute.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jack.mute.Mute;
import com.jack.mute.utils.MetaUtils;
import com.jack.mute.utils.Utils;

public class MuteCommand implements CommandExecutor {

	private Mute plugin;
	private MetaUtils MU;
	private Utils U;
	
	public MuteCommand(Mute p){
		plugin = p;
		MU = plugin.getMetaUtils();
		U = plugin.getUtils();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mute")) {
			if(sender.hasPermission("mute.mute")){
				if(args.length != 1){
					U.sendMessage(sender, "&6Incorrect syntax");
					U.sendMessage(sender, "&6/mute &c<playername>");
				} else {
					Player tmpP = null;
					for(World w : Bukkit.getWorlds()){
						for(Player p : w.getPlayers()){
							if(p.getName().equals(args[0])){
								tmpP = p;
							}
						}
					}
					if(tmpP != null){
						if(MU.hasMetaData(tmpP, "muted")){
							U.sendMessage(sender, "&6" + tmpP.getName() + "&c, has been unmuted!");
							MU.removeMetaData(tmpP, "muted");
							U.sendMessage(tmpP, "&6You have been unmuted!");
						} else {
							U.sendMessage(sender, "&6" + tmpP.getName() + "&c, has been muted!");
							MU.setMetaData(tmpP, "muted", true);
							U.sendMessage(tmpP, "&6You have been muted!");
						}
					}
				}
			} else {
				U.sendMessage(sender, "&4You do not have permission for this!");
			}
		}
		return true;
	}
}

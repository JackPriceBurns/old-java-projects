package com.jack.mages.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jack.mages.spells.LightningShockwaveSpell;

public class WandCommand implements CommandExecutor{

	//private Mages plugin;
	//private Wands W;
	//private Utils U;
	
	public WandCommand(){
		
		//plugin = Mages.getPlugin();
		//U = Mages.getPlugin().getUtils();
		//W = Mages.getPlugin().getWands();
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lebel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("wand") || cmd.getName().equalsIgnoreCase("w")){
			if(sender instanceof Player){
				new LightningShockwaveSpell((Player) sender);
			} else {
				sender.sendMessage("Sorry Nope");
			}
		}
		return true;
	}

}

package com.jack.teaching.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.jack.teaching.Warn;
import com.jack.teaching.utils.Utils;

public class AdvCommand implements CommandExecutor{

	private Utils U;
	private Warn plugin;
	
	public AdvCommand(Warn p) {
		plugin = p;
		U = plugin.getUtils();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0){
			U.showHelp(sender);
		} else {
			
		}
		return false;
	}
}

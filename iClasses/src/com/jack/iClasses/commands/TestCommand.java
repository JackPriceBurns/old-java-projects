package com.jack.iclasses.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jack.iclasses.IClasses;
import com.jack.iclasses.UserManager;

public class TestCommand implements CommandExecutor{

	private UserManager UM;
	
	public TestCommand(){
		UM = IClasses.getInstance().getUserManager();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("test")){
			if(sender instanceof Player){
				for(int skill : UM.getSkills((Player) sender)){
					sender.sendMessage(skill + "");
					return true;
				}
			}
		}
		return false;
	}

}

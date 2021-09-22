package com.jack.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jack.essentials.Essentials;
import com.jack.essentials.User;
import com.jack.essentials.managers.UserManager;

public class HomeCommand implements CommandExecutor {

	private UserManager UM;
	
	public HomeCommand(){
		UM = Essentials.getInstance().getUserManager();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getLabel().equalsIgnoreCase("home")){
			if(args.length != 0){
				return false;
			}
			
			if(!(sender instanceof Player)){
				sender.sendMessage("Only players can use Home");
				return true;
			} else {
				Player player = (Player)sender;
				User user = UM.getUser(player.getUniqueId());
				if(!user.isHomeless()){
					String home = user.getHome();
					double x = Float.parseFloat(home.split(":")[0]);
					double y = Float.parseFloat(home.split(":")[1]);
					double z = Float.parseFloat(home.split(":")[2]);
					float yaw = Float.parseFloat(home.split(":")[3]);
					float pitch = Float.parseFloat(home.split(":")[4]);
					Location homeLoc = new Location(Bukkit.getWorld("World"), x, y, z, yaw, pitch);
					player.teleport(homeLoc);
				} else {
					player.sendMessage("You have no home set.");
				}
				return true;
			}
		} else if(cmd.getLabel().equalsIgnoreCase("sethome")){
			if(args.length != 0){
				return false;
			}
			
			if(!(sender instanceof Player)){
				sender.sendMessage("Only players can use Sethome");
			} else {
				Player player = (Player)sender;
				User user = UM.getUser(player.getUniqueId());
				user.setHome(player.getLocation());
				player.sendMessage("Home set.");
			}
			return true;
		}
		return false;
	}
}

package com.jack.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jack.essentials.Essentials;

public class SpawnCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("spawn")){
			if(args.length != 0){
				return false;
			}
			
			if(!(sender instanceof Player)){
				sender.sendMessage("Only players can use Spawn");
				return true;
			} else {
				Player player = (Player)sender;
				String spawn = Essentials.getInstance().getConfigManager().getSpawn();
				double x = Float.parseFloat(spawn.split(":")[0]);
				double y = Float.parseFloat(spawn.split(":")[1]);
				double z = Float.parseFloat(spawn.split(":")[2]);
				float yaw = Float.parseFloat(spawn.split(":")[3]);
				float pitch = Float.parseFloat(spawn.split(":")[4]);
				Location spawnLoc = new Location(Bukkit.getWorld("World"), x, y, z, yaw, pitch);
				player.teleport(spawnLoc);
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("setspawn") && sender.hasPermission("essentials.setspawn")){
			if(args.length != 0){
				return false;
			}
				
			if(!(sender instanceof Player)){
				sender.sendMessage("Only players can use the setspawn command :/");
				return true;
			} else {
				Player player = (Player)sender;
				Location spawn = player.getLocation();
				Essentials.getInstance().getConfigManager().setSpawn(spawn);
				player.sendMessage("Spawn set :p");
				return true;
			}
		}
		return false;
	}
}

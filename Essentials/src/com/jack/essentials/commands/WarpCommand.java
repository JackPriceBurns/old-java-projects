package com.jack.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jack.essentials.Essentials;
import com.jack.essentials.managers.WarpManager;

public class WarpCommand implements CommandExecutor{
	
	private WarpManager WM;
	
	public WarpCommand(){
		WM = Essentials.getInstance().getWarpManager();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("warp")){
			if(args.length != 1){
				return false;
			}
			
			if(!(sender instanceof Player)){
				sender.sendMessage("Only players can use Warp");
				return true;
			} else {
				Player player = (Player)sender;
				
				if(player.hasPermission("essentials.warp." + args[0]) || player.hasPermission("essentials.warp.*")){
					if(WM.isWarp(args[0])){
						String warp = WM.getWarpCords(args[0]);
						double x = Float.parseFloat(warp.split(":")[0]);
						double y = Float.parseFloat(warp.split(":")[1]);
						double z = Float.parseFloat(warp.split(":")[2]);
						float yaw = Float.parseFloat(warp.split(":")[3]);
						float pitch = Float.parseFloat(warp.split(":")[4]);
						Location warpLoc = new Location(Bukkit.getWorld("World"), x, y, z, yaw, pitch);
						player.teleport(warpLoc);
					} else {
						player.sendMessage("The warp " + args[0] + " does not exist.");
					}
					
					return true;
				} else {
					player.sendMessage("You do not have permission for this warp.");
					return true;
				}
				
				
			}
		} else if(cmd.getName().equalsIgnoreCase("setwarp") && sender.hasPermission("essentials.setwarp")){
			if(args.length != 1){
				return false;
			}
				
			if(!(sender instanceof Player)){
				sender.sendMessage("Only players can use Setwarp");
				return true;
			} else {
				Player player = (Player)sender;
				if(!WM.isWarp(args[0])){
					Location warp = player.getLocation();
					WM.setWarp(args[0], warp);
					player.sendMessage("Warp set.");
				} else {
					player.sendMessage(args[0] + ", already exists.");
				}
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("delwarp") && sender.hasPermission("essentials.delwarp")){
			if(args.length != 1){
				return false;
			}
			Player player = (Player)sender;
			if(WM.isWarp(args[0])){
				WM.delWarp(args[0]);
				player.sendMessage("Warp deleted.");
			} else {
				player.sendMessage(args[0] + ", doesn't exist.");
			}
			return true;
		} else if(cmd.getName().equalsIgnoreCase("warps")){
				if(args.length != 0){
					return false;
				}
				
				String warpnames = "";
				
				for(String name : WM.getWarps()){
					if(warpnames.equals("")){
						warpnames = warpnames + name;
					} else {
						warpnames = warpnames + ", " + name;
					}
				}
				
				sender.sendMessage("Warps: " + warpnames);
				return true;
			}
		return false;
	}
}

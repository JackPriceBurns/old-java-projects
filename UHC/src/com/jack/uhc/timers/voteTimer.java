package com.jack.uhc.timers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.jack.uhc.UHC;

public class voteTimer {
	
	public voteTimer(){
		run();
	}
	
	public void run(){
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(UHC.getPlugin(), new Runnable() {
		    
		    @Override
		    public void run() {
		    	if(!UHC.getPlugin().getGame().isStarted()){
		    		Bukkit.getServer().broadcastMessage("To start a game more than half of the people on the server must vote yes");
		    		Bukkit.getServer().broadcastMessage("Type '/vote yes' or '/vote no' to vote");
		    	} else {
		    		for(World world : Bukkit.getWorlds()){
		    			for(Player player : world.getPlayers()){
		    				player.setFlying(false);
		    				player.setAllowFlight(false);
		    			}
		    		}
		    	}
		    }
		    
		}, 0, 20 * 60);
	}
}

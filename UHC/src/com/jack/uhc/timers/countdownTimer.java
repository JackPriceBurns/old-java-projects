package com.jack.uhc.timers;

import org.bukkit.Bukkit;

import com.jack.uhc.UHC;

public class countdownTimer {
	
	private int taskID;
	
	public countdownTimer(){
		startCountDown();
	}
	
	public void startCountDown(){
		taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(UHC.getPlugin(), new Runnable() {
			
		    private int count = 30;
		    
		    @Override
		    public void run() {
		    	if(count == 30){
		    		Bukkit.broadcastMessage("Game starting in 30 seconds");
		    	}
		    	if(count == 15){
		    		Bukkit.broadcastMessage("Game starting in 15 seconds");
		    	}
		    	if(count == 5){
		    		Bukkit.broadcastMessage("Game starting in 5 seconds");
		    	}
		    	if(count == 4){
		    		Bukkit.broadcastMessage("Game starting in 4 seconds");
		    	}
		    	if(count == 3){
		    		Bukkit.broadcastMessage("Game starting in 3 seconds");
		    	}
		    	if(count == 2){
		    		Bukkit.broadcastMessage("Game starting in 2 seconds");
		    	}
		    	if(count == 1){
		    		Bukkit.broadcastMessage("Game starting in 1 seconds");
		    	}
		    	if(count == 0){
		    		Bukkit.broadcastMessage("Game starting...");
		    		cancelTimer();
		    	}
		        count--;
		    }
		}, 0, 20);
	}

	protected void cancelTimer() {
		Bukkit.getScheduler().cancelTask(taskID);
		UHC.getPlugin().getGame().startGame();
	}
}

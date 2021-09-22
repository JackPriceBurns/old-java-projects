package com.jack.mages.spells;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.jack.mages.Mages;
import com.jack.mages.utils.MetaUtils;
import com.jack.mages.utils.Utils;

public class LightningShockwaveSpell {
	
	private final Player player;
	private MetaUtils MU;
	private Mages plugin;
	private int taskID;
	private Utils U;
	
	public LightningShockwaveSpell(Player player){
		U = Mages.getPlugin().getUtils();
		MU = Mages.getPlugin().getMetaUtils();
		plugin = Mages.getPlugin();
		this.player = player;
		run();
	}

	private void run() {
		taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
		    private int count = 1;
		    
		    @Override
		    public void run() {
		        count++;
		        count++;
		        
		        MU.setMetaData(player, "Lightning", true);
		        
		        for(Location location : Mages.getPlugin().getUtils().circle(player.getLocation(), count, true)){
		        	World world = location.getWorld();
		        	int rand = U.randInt(1, 3);
		        	if(rand == 1){
		        		if(world.getHighestBlockYAt(location) >= location.getBlockY() - 6 && world.getHighestBlockYAt(location) <= location.getBlockY() + 6){
							world.strikeLightning(new Location(world, location.getX(), world.getHighestBlockYAt(location) + 1, location.getZ()));
						} else {
							world.strikeLightning(location);
						}
		        	}
		        }
		        
		        if(count == 15){
		        	MU.removeMetaData(player, "Lightning");
		            cancelTask();
		        }
		    }
		}, 0, 10);
		
	}

	protected void cancelTask() {
		plugin.getServer().getScheduler().cancelTask(taskID);
	}
	
	
}

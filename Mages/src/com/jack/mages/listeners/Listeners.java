package com.jack.mages.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.jack.mages.Mages;
import com.jack.mages.utils.MetaUtils;

public class Listeners implements Listener{
		
		private MetaUtils MU;
	
	public Listeners(){
		MU = Mages.getPlugin().getMetaUtils();
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		//Player player = event.getPlayer();
		//Location location = player.getLocation();
		//ItemStack iteminhand = player.getItemInHand();
		//if(PlayerUtils.isMage(Player.getName())){
			
		//}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			if(MU.hasMetadata(player, "Lightning")){
				event.setCancelled(true);
			}
		}
	}
}

package com.jack.iclasses;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {
	
	private FileManager FM;
	private UserManager UM;
	
	public Listeners(){
		FM = IClasses.getInstance().getFileManager();
		UM = IClasses.getInstance().getUserManager();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		String format = event.getFormat();
		String prefix = UM.getUser(player.getUniqueId()).getPrefix();
		prefix = colorize(prefix.trim());
		
		format = format.replaceAll("\\{class\\}", prefix);
		event.setFormat(format);
	}
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event){
		LivingEntity entity = event.getEntity();
		Location location = event.getEntity().getLocation();
		Location spawn = new Location(location.getWorld(), 0, 64, 0);
		double distance = location.distance(spawn);
		double level = distance * 0.006;
		int ilevel = (int) level;
	
		if(ilevel == 0){
			ilevel = 1;
		}
		
		@SuppressWarnings("deprecation")
		String name = entity.getType().getName();
		
		entity.setCustomName(colorize("&6" + name + " &3Lvl. " + ilevel));
		
		entity.setRemoveWhenFarAway(true);
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		
		//If player is the attacker
		
		if(event.getDamager() instanceof Player){
			double AufenceLevel = 5.0;
			event.setDamage(event.getDamage() * AufenceLevel);
		}
		
		//If mob is the attacker
		
		if(event.getDamager() instanceof LivingEntity){
			LivingEntity entity = (LivingEntity) event.getDamager();
			if(entity.getCustomName() != null){
				String level = entity.getCustomName();
				level = level.replaceAll(ChatColor.COLOR_CHAR + "[a-fA-F0-9]", "");
				level = level.replaceAll("[^0-9]", "");
				int tmp;
				try {
					tmp = Integer.parseInt(level);
				} catch (NumberFormatException e) {
					tmp = 0;
				}
				if(tmp != 0){
					double AufenceLevel = tmp / 2.0;
					event.setDamage(event.getDamage() * AufenceLevel);
				}
			}
		}
		
		//If player is the victim
		
		if(event.getEntity() instanceof Player){
			double DamageTolorence = 5.0;
			event.setDamage(event.getDamage() / DamageTolorence);
		}
		
		//If mob is the victim
		
		if(event.getEntity() instanceof LivingEntity){
			LivingEntity entity = (LivingEntity) event.getEntity();
			if(entity.getCustomName() != null){
				String level = entity.getCustomName();
				level = level.replaceAll("\\§[a-fA-F0-9]", "");
				level = level.replaceAll("[^0-9]", "");
				int tmp;
				try {
					tmp = Integer.parseInt(level);
				} catch (NumberFormatException e) {
					tmp = 0;
				}
				if(tmp != 0){
					double DamageTolorence = tmp / 2.0;
					event.setDamage(event.getDamage() / DamageTolorence);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		FM.CheckUser(event.getPlayer().getUniqueId());
	}
	
	private String colorize(String string){
		string = string.replaceAll("&", "§");
		return string;
	}
}

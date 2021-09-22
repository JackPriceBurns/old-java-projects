package com.mcmorcraft.elementalarrows.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mcmorcraft.elementalarrows.ArrowFlyingEffect;
import com.mcmorcraft.elementalarrows.ElementalArrow;
import com.mcmorcraft.elementalarrows.ElementalArrows;
import com.mcmorcraft.elementalarrows.utils.InventoryUtils;
import com.mcmorcraft.elementalarrows.utils.MetaUtils;

public class BowListener implements Listener {
	
	private ElementalArrows plugin;
	private MetaUtils MU;
	private InventoryUtils IU;
	private ArrowFlyingEffect AFE;

	public BowListener(ElementalArrows p){
		plugin = p;
		MU = plugin.getMetaUtils();
		IU = plugin.getInventoryUtils();
		AFE = plugin.getArrowFlyingEffect();
	}

	@EventHandler
	public void entityShootBow(EntityShootBowEvent event){
		if(event.getEntity() instanceof Player && event.getProjectile() instanceof Arrow){
			Arrow arrow = (Arrow) event.getProjectile();
			Player player = (Player) event.getEntity();
			ItemStack bow = event.getBow();
			ItemMeta itemmeta = bow.getItemMeta();
			if(itemmeta.hasLore()){
				for(ElementalArrow elearrow : plugin.getArrowList()){
					if(itemmeta.getLore().get(0).equals(elearrow.getLore())){
						if(IU.hasItem(player, elearrow.getMaterial(), elearrow.getMaterialAmout())){
							IU.removeItem(player, elearrow.getMaterial(), elearrow.getMaterialAmout());
							MU.setMetaData(arrow, elearrow.getName(), player.getName());
							AFE.effect(elearrow, arrow, player);
						}
					}
				}
			}
		}
	}
}

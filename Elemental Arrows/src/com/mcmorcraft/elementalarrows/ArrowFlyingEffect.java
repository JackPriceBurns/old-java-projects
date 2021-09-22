package com.mcmorcraft.elementalarrows;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

//import com.mcmorcraft.elementalarrows.utils.FireworkEffectUtils;

public class ArrowFlyingEffect {
	
	private ElementalArrows plugin;
	//private FireworkEffectUtils FEU;

	public ArrowFlyingEffect(ElementalArrows p) {
		plugin = p;
		//FEU = plugin.getFireworkEffectUtils();
	}
	
	public void effect(ElementalArrow elearrow, final Arrow arrow, Player player){
		if(elearrow.getName().equals("Explosive")){
            new BukkitRunnable() {
            	public void run() {
            		Firework firework = arrow.getWorld().spawn(arrow.getLocation(), Firework.class);
                    FireworkMeta fMeta = firework.getFireworkMeta();
                    fMeta.addEffect(FireworkEffect.builder().with(Type.BURST).withColor(Color.RED).build());
                    firework.setFireworkMeta(fMeta);
            		firework.detonate();
            		firework = null;
            	}
            }.runTaskLater(plugin, 1);
		}
	}

}

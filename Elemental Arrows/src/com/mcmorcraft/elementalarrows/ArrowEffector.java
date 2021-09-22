package com.mcmorcraft.elementalarrows;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

//import com.mcmorcraft.elementalarrows.utils.InventoryUtils;
import com.mcmorcraft.elementalarrows.utils.MetaUtils;

public class ArrowEffector {

	private ElementalArrows plugin;
	private MetaUtils MU;
	//private InventoryUtils IU;

	public ArrowEffector(ElementalArrows p) {
		plugin = p;
		MU = plugin.getMetaUtils();
		//IU = plugin.getInventoryUtils();
	}

	public void effectMob(ElementalArrow elearrow, Player player, Arrow arrow) {
		if(elearrow.getName().equals("Explosive")){
			MU.setMetaData(arrow, "landed", false);
		}

		if(elearrow.getName().equals("Poison")){
			player.sendMessage("test");
		}
	}

	public void effectBlock(ElementalArrow elearrow, Player player, Arrow arrow) {
		if(elearrow.getName().equals("Explosive")){
			MU.setMetaData(arrow, "landed", false);
		}

		if(elearrow.getName().equals("Poison")){
			player.sendMessage("test");
		}
	}
}

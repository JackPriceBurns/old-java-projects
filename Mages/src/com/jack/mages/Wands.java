package com.jack.mages;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.jack.mages.utils.Utils;

public class Wands {
	
	public final ItemStack NOVICE_WAND;
	public final ItemStack ADEPT_WAND;
	public final ItemStack MASTER_WAND;
	
	private Utils U;
	
	public Wands(){		
		U = Mages.getPlugin().getUtils();	
		
		NOVICE_WAND = NoviceWand();
		ADEPT_WAND = AdeptWand();
		MASTER_WAND = MasterWand();
	}
	
	public ItemStack NoviceWand(){
		ItemStack WAND = new ItemStack(Material.STICK);
		ItemMeta meta = WAND.getItemMeta();
		meta.setDisplayName(ChatColor.COLOR_CHAR + "6Novice Wand");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(U.Colorize("&2[&aNovice Wand&2]"));
		lore.add(U.Colorize("&6A starting wand for less expierenced Wizards"));
		lore.add(U.Colorize("&6Try not to blow yourself up"));
		lore.add(U.Colorize("&6You are on your way to become a Adept Mage"));
		meta.setLore(lore);
		WAND.setItemMeta(meta);
		return WAND;
	}
	
	public ItemStack AdeptWand(){
		ItemStack WAND = new ItemStack(Material.STICK);
		ItemMeta meta = WAND.getItemMeta();
		meta.setDisplayName(ChatColor.COLOR_CHAR + "6Adept Wand");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(U.Colorize("&3[&bAdept Wand&3]"));
		lore.add(U.Colorize("&6A wand which is more powerful than your previous"));
		lore.add(U.Colorize("&6If you were a novice mage the power would be too much!"));
		lore.add(U.Colorize("&6You are on your way to become a Master Mage"));
		meta.setLore(lore);
		WAND.setItemMeta(meta);
		return WAND;
	}

	public ItemStack MasterWand(){
		ItemStack WAND = new ItemStack(Material.STICK);
		ItemMeta meta = WAND.getItemMeta();
		meta.setDisplayName(ChatColor.COLOR_CHAR + "6Master Wand");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(U.Colorize("&6[&eMaster Wand&6]"));
		lore.add(U.Colorize("&6A wand much more powerful than any other you have seen before."));
		lore.add(U.Colorize("&6You can feel the magical Aura in the wand as you hold it."));
		meta.setLore(lore);
		WAND.setItemMeta(meta);
		return WAND;
	}
}

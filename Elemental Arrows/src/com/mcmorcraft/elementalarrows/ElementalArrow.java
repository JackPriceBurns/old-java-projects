package com.mcmorcraft.elementalarrows;

import org.bukkit.Material;

public class ElementalArrow {
	private String name;
	private String lore;
	private Material material;
	private int materialAmount;
	
	public ElementalArrow(String typename, String lorestring, Material materialtype, int amount) {
		name = typename;
		material = materialtype;
		materialAmount = amount;
		lore = lorestring;
	}
	
	public String getName(){
		return name;
	}
	
	public Material getMaterial(){
		return material;
	}
	
	public int getMaterialAmout(){
		return materialAmount;
	}
	
	public String getLore(){
		return lore;
	}
}

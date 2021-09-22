package com.mcmorcraft.elementalarrows.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.mcmorcraft.elementalarrows.ElementalArrows;

public class InventoryUtils {
	public void removeItem(Player player, Material material, int removalAmount) {
		Inventory i = player.getInventory();
		int totalRemoved = 0;
        for(ItemStack inven : i.getContents()){
        	if(inven != null){
        		if(inven.getType().equals(material)){
        			if(totalRemoved != removalAmount){
        				int amount = inven.getAmount();
        				int amountNeeded = removalAmount - totalRemoved;
        				int amountAfterRemoval;
        				if(amountNeeded < amount){
        					amountAfterRemoval = amount - amountNeeded;
        					inven.setAmount(amountAfterRemoval);
        					totalRemoved = totalRemoved + amountNeeded;
        				} else {
        					totalRemoved = totalRemoved + amount;
        					player.getInventory().removeItem(inven);
        				}
        				amount = 0;
        				amountNeeded = 0;
        				amountAfterRemoval = 0;
        				if(totalRemoved > removalAmount){
        					int offset = totalRemoved - removalAmount;
        					player.sendMessage(ElementalArrows.PLUGIN_TAG + " Too much " + material.getData().getName() + " was taken by accident!");
        					player.sendMessage(ElementalArrows.PLUGIN_TAG + " Giving you " + offset + " " + material.getData().getName() + " back!");
        					ItemStack itemstack = new ItemStack(material);
        					itemstack.setAmount(offset);
        					player.getInventory().addItem(itemstack);
        					totalRemoved = totalRemoved - offset;
        					offset = 0;
        				}
        			}
            	}
        	}
        }
	}
	
	public boolean hasItem(Player player, Material material, int minAmount) {
		Inventory i = player.getInventory();
		int totalAmount = 0;
        for(ItemStack inven : i.getContents()){
        	if(inven != null){
        		if(inven.getType().equals(material)){
        			totalAmount = totalAmount + inven.getAmount();
            	}
        	}
        }
        if(totalAmount > minAmount || totalAmount == minAmount)return true;
        return false;
	}
}

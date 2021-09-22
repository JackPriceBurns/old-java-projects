package com.jack.mages.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Utils {
	
	public String Colorize(String str){
		str = str.replaceAll("&", "§");
		return str;
	}
	
	public Set<Location> sphere(Location location, int radius, boolean hollow){
		Set<Location> blocks = new HashSet<Location>();
		World world = location.getWorld();
		int X = location.getBlockX();
		int Y = location.getBlockY();
		int Z = location.getBlockZ();
		int radiusSquared = radius * radius;
		
		if(hollow){
			for (int x = X - radius; x <= X + radius; x++) {
				for (int y = Y - radius; y <= Y + radius; y++) {
					for (int z = Z - radius; z <= Z + radius; z++) {
						if ((X - x) * (X - x) + (Y - y) * (Y - y) + (Z - z) * (Z - z) <= radiusSquared) {
							Location block = new Location(world, x, y, z);
							blocks.add(block);
						}
					}
				}
			}
			return makeHollow(blocks, true);
		} else {
			for (int x = X - radius; x <= X + radius; x++) {
				for (int y = Y - radius; y <= Y + radius; y++) {
					for (int z = Z - radius; z <= Z + radius; z++) {
						if ((X - x) * (X - x) + (Y - y) * (Y - y) + (Z - z) * (Z - z) <= radiusSquared) {
							Location block = new Location(world, x, y, z);
							blocks.add(block);
						}
					}
				}
			}
			return blocks;
		}
	}
	
	public Set<Location> circle(Location location, int radius, boolean hollow){
		Set<Location> blocks = new HashSet<Location>();
		World world = location.getWorld();
		int X = location.getBlockX();
		int Y = location.getBlockY();
		int Z = location.getBlockZ();
		int radiusSquared = radius * radius;
		
		if(hollow){
			for (int x = X - radius; x <= X + radius; x++) {
				for (int z = Z - radius; z <= Z + radius; z++) {
	            	if ((X - x) * (X - x) + (Z - z) * (Z - z) <= radiusSquared) {
	                	Location block = new Location(world, x, Y, z);
	                    blocks.add(block);
	                }
	            }
			}
			return makeHollow(blocks, false);
		} else {
			for (int x = X - radius; x <= X + radius; x++) {
				for (int z = Z - radius; z <= Z + radius; z++) {
					if ((X - x) * (X - x) + (Z - z) * (Z - z) <= radiusSquared) {
						Location block = new Location(world, x, Y, z);
						blocks.add(block);
					}
				}
			}
			return blocks;
		}
	}
	
	private Set<Location> makeHollow(Set<Location> blocks, boolean sphere){
		Set<Location> edge = new HashSet<Location>();
		if(!sphere){
			for(Location l : blocks){
				World w = l.getWorld();
				int X = l.getBlockX();
				int Y = l.getBlockY();
				int Z = l.getBlockZ();
				Location front = new Location(w, X + 1, Y, Z);
				Location back = new Location(w, X - 1, Y, Z);
				Location left = new Location(w, X, Y, Z + 1);
				Location right = new Location(w, X, Y, Z - 1);
				if(!(blocks.contains(front) && blocks.contains(back) && blocks.contains(left) && blocks.contains(right))){
					edge.add(l);
				}
			}
			return edge;
		} else {
			for(Location l : blocks){
				World w = l.getWorld();
				int X = l.getBlockX();
				int Y = l.getBlockY();
				int Z = l.getBlockZ();
				Location front = new Location(w, X + 1, Y, Z);
				Location back = new Location(w, X - 1, Y, Z);
				Location left = new Location(w, X, Y, Z + 1);
				Location right = new Location(w, X, Y, Z - 1);
				Location top = new Location(w, X, Y + 1, Z);
				Location bottom = new Location(w, X, Y - 1, Z);
				if(!(blocks.contains(front) && blocks.contains(back) && blocks.contains(left) && blocks.contains(right) && blocks.contains(top) && blocks.contains(bottom))){
					edge.add(l);
				}
			}
			return edge;
		}
	}
	
	public int randInt(int min, int max) {
	    Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public boolean isOnline(String name){
		Player player = null;
		for(World w : Bukkit.getWorlds()){
			for(Player p : w.getPlayers()){
				if(p.getName().equals(name)){
					player = p;
				}
			}
		}
		if(player == null){
			return false;
		} else {
			return true;
		}
	}
	
	public Player getPlayer(String name){
		if(isOnline(name)){
			Player player = null;
			for(World w : Bukkit.getWorlds()){
				for(Player p : w.getPlayers()){
					if(p.getName().equals(name)){
						player = p;
					}
				}
			}
			if(player == null){
				return null;
			} else {
				return player;
			}
		} else {
			return null;
		}
	}
}

package com.jack.prisoncells.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jack.prisoncells.Database;
import com.jack.prisoncells.PrisonCells;

public class Utils {

	private PrisonCells plugin;
	private Database DB;
	private PlayerUtils PU;
	private MetaUtils MU;
	
	public Utils(PrisonCells p){
		plugin = p;
		DB = plugin.getDB();
		PU = plugin.getPU();
		MU = plugin.getMU();
	}
	
	public Object getCell(Location location){
		ResultSet cells = DB.getAllCells();
		int cellid = 0;
		try {
			while(cells.next()){
				int pos1X = cells.getInt("pos1X");
				int pos1Y = cells.getInt("pos1Y");
				int pos1Z = cells.getInt("pos1Z");
				int pos2X = cells.getInt("pos2X");
				int pos2Y = cells.getInt("pos2Y");
				int pos2Z = cells.getInt("pos2Z");
				String world = cells.getString("world");
				Location pos1 = new Location(Bukkit.getWorld(world), pos1X, pos1Y, pos1Z);
				Location pos2 = new Location(Bukkit.getWorld(world), pos2X, pos2Y, pos2Z);
				if(isInRegion(location, pos1, pos2)){
					cellid = cells.getInt("id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(cellid != 0){
			return cellid;
		} else {
			return null;
		}
	}
	
	public Object getPrison(Location location){
		ResultSet prisons = DB.getAllPrisons();
		int prisonid = 0;
		try {
			while(prisons.next()){
				int pos1X = prisons.getInt("pos1X");
				int pos1Y = prisons.getInt("pos1Y");
				int pos1Z = prisons.getInt("pos1Z");
				int pos2X = prisons.getInt("pos2X");
				int pos2Y = prisons.getInt("pos2Y");
				int pos2Z = prisons.getInt("pos2Z");
				String world = prisons.getString("world");
				Location pos1 = new Location(Bukkit.getWorld(world), pos1X, pos1Y, pos1Z);
				Location pos2 = new Location(Bukkit.getWorld(world), pos2X, pos2Y, pos2Z);
				if(isInRegion(location, pos1, pos2)){
					prisonid = prisons.getInt("id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(prisonid != 0){
			return prisonid;
		} else {
			return null;
		}
	}
	
	public boolean canBuild(int cellId, Player player){
		ResultSet cell = DB.getCell(cellId);
		try {
			while(cell.next()){
				if(cell.getString("owner").equals(player.getName())){
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public boolean isInRegion(Location block, Location pos1, Location pos2){
		int MaxX;
		int MinX;
		int MaxY;
		int MinY;
		int MaxZ;
		int MinZ;
		if(pos1.getBlockX() >= pos2.getBlockX()){
			MaxX = pos1.getBlockX();
			MinX = pos2.getBlockX();
		} else {
			MinX = pos1.getBlockX();
			MaxX = pos2.getBlockX();
		}
		if(pos1.getBlockY() >= pos2.getBlockY()){
			MaxY = pos1.getBlockY();
			MinY = pos2.getBlockY();
		} else {
			MinY = pos1.getBlockY();
			MaxY = pos2.getBlockY();
		}
		if(pos1.getBlockZ() >= pos2.getBlockZ()){
			MaxZ = pos1.getBlockZ();
			MinZ = pos2.getBlockZ();
		} else {
			MinZ = pos1.getBlockZ();
			MaxZ = pos2.getBlockZ();
		}
		if(block.getBlockX() <= MaxX && block.getBlockX() >= MinX){
			if(block.getBlockY() <= MaxY && block.getBlockY() >= MinY){
				if(block.getBlockZ() <= MaxZ && block.getBlockZ() >= MinZ){
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void newDay(){
		Bukkit.broadcastMessage(PU.colourise("&6A new prison day is here!"));
		ResultSet cells = DB.getAllCells();
		try {
			while(cells.next()){
				if(!(cells.getInt("timeLeft") == -1)){
					int timeLeft = cells.getInt("timeLeft");
					timeLeft = timeLeft - 1;
					if(timeLeft == 0){
						DB.resetCell(cells.getInt("id"));
					} else {
						DB.updateCellTime(cells.getInt("id"), timeLeft);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getCellInfo(Player player) {
		if(getCell(player.getLocation()) == null){
			sendMessage(player, "&9[&6PrisonCells&9] - &6Cell Info");
			sendMessage(player, "&6You are not standing in a cell");
		} else {
			ResultSet cell = DB.getCell((int) getCell(player.getLocation()));
			sendMessage(player, "&9[&6PrisonCells&9] - &6Cell Info");
			try {
				while(cell.next()){
					if(cell.getString("owner").equals("-1")){
						sendMessage(player, "&6This cell has no one living in it");
					} else {
						sendMessage(player, "&6Owner: &9" + cell.getString("owner"));
						sendMessage(player, "&6Days Left: &9" + cell.getInt("timeLeft"));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void createPrison(Player player) {
		if(MU.hasMetaData(player, "pos1") && MU.hasMetaData(player, "pos2")){
			if((MU.getMetaData(player, "pos1") instanceof Location) && (MU.getMetaData(player, "pos2") instanceof Location)){
				Location loc1 = (Location) MU.getMetaData(player, "pos1");
				Location loc2 = (Location) MU.getMetaData(player, "pos2");
				if(loc1.getWorld().getName().equals(loc2.getWorld().getName())){
					if(getPrison(loc1) == null && getPrison(loc2) == null){
						DB.insertPrison(player, loc1, loc2);
					} else {
						sendMessage(player, "&cYour prison collides with another prison");
					}
				} else {
					sendMessage(player, "&cYour two locations you selected with a stick must be in the same world");
				}
			} else {
				sendMessage(player, "&cYou need to select two locations with a stick");
			}
		} else {
			sendMessage(player, "&cYou need to select two locations with a stick");
		}
	}

	public void createCell(Player player) {
		if(MU.hasMetaData(player, "pos1") && MU.hasMetaData(player, "pos2")){
			if((MU.getMetaData(player, "pos1") instanceof Location) && (MU.getMetaData(player, "pos2") instanceof Location)){
				Location loc1 = (Location) MU.getMetaData(player, "pos1");
				Location loc2 = (Location) MU.getMetaData(player, "pos2");
				if(loc1.getWorld().getName().equals(loc2.getWorld().getName())){
					if(getPrison(loc1) != null && getPrison(loc2) != null){
						if(getCell(loc1) == null && getCell(loc2) == null){
							DB.insertCell(player, loc1, loc2);
						} else {
							sendMessage(player, "&cYour cell colides with another cell");
						}
					} else {
						sendMessage(player, "&cYour cell must be inside a prison");
					}
				} else {
					sendMessage(player, "&cYour two locations you selected with a stick must be in the same world");
				}
			} else {
				sendMessage(player, "&cYou need to select two locations with a stick");
			}
		} else {
			sendMessage(player, "&cYou need to select two locations with a stick");
		}
	}

	public boolean canBuy(Player player) {
		if(getCell(player.getLocation()) != null){
			int cellid = (int) getCell(player.getLocation());
			try {
				ResultSet cell = DB.getCell(cellid);
				while(cell.next()){
					if(cell.getString("owner").equals("-1")){
						return true;
					} else {
						sendMessage(player, "&cYou cant buy someone elses cell, you need to wait for there time to expire first");
						return false;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				sendMessage(player, "&4Something broke D:");
				return false;
			}
		
		} else {
			sendMessage(player, "&cYou need to stand inside a cell to buy it");
			return false;
		}
		return false;
	}

	public void buyCell(Player player, int days) {
		int cellid = (int) getCell(player.getLocation());
		DB.buyCell(cellid, days, player.getName());
	}
	
	public void sendMessage(CommandSender sender, String string) {
		string = string.replaceAll("&", "§");
		sender.sendMessage(string);
	}
}

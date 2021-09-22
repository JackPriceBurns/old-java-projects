package com.jack.prisoncells;

import java.sql.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Database {

	private PrisonCells plugin;
	private Statement stmt;
	
	public Database(String host, String user, String pass, String db, PrisonCells p){
		plugin = p;
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+db+"?user="+user+"&password="+pass);
			stmt = con.createStatement();
			createTables();
		} catch (Exception e){
			plugin.getLogger().info("Could not connect to MySQL database");
			Bukkit.getPluginManager().disablePlugin(plugin);
		}
	}
	
	private void createTables(){
		try {
			stmt.execute(
					"CREATE TABLE IF NOT EXISTS `PrisonCells_Cells` (" +
							"`id` int(11) not null auto_increment," +
							"`pos1X` int(11)," +
							"`pos1Y` int(11)," + 
							"`pos1Z` int(11)," +
							"`pos2X` int(11)," +
							"`pos2Y` int(11)," +
							"`pos2Z` int(11)," +
							"`world` varchar(255)," +
							"`owner` varchar(255) not null default '-1'," +
							"`timeLeft` int(11) not null default '-1'," +
							"PRIMARY KEY (`id`)" +
					") ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;");

			stmt.execute(
					"CREATE TABLE IF NOT EXISTS `PrisonCells_Prisons` (" +
							"`id` int(11) not null auto_increment," +
							"`pos1X` int(11)," +
							"`pos1Y` int(11)," + 
							"`pos1Z` int(11)," +
							"`pos2X` int(11)," +
							"`pos2Y` int(11)," +
							"`pos2Z` int(11)," +
							"`world` varchar(255)," +
							"PRIMARY KEY (`id`)" +
					") ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getPrison(int id){
		ResultSet result = null;
		try {
			result = stmt.executeQuery("SELECT * FROM PrisonCells_Prisons WHERE id = '" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;	
	}
	
	public ResultSet getCell(int id){
		ResultSet result = null;
		try {
			result = stmt.executeQuery("SELECT * FROM PrisonCells_Cells WHERE id = '" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;	
	}
	
	public ResultSet getAllCells(){
		ResultSet result = null;
		try {
			result = stmt.executeQuery("SELECT * FROM PrisonCells_Cells");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ResultSet getAllPrisons() {
		ResultSet result = null;
		try {
			result = stmt.executeQuery("SELECT * FROM PrisonCells_Prisons");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void updateCellTime(int id, int time){
		try {
			stmt.execute("UPDATE PrisonCells_Cells SET timeLeft = " + time + " WHERE id = '" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void resetCell(int id){
		try {
			stmt.execute("UPDATE PrisonCells_Cells SET timeLeft = -1, owner = '-1' WHERE id = '" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertPrison(Player player, Location loc1, Location loc2) {
		String world = loc1.getWorld().getName();
		int pos1X = loc1.getBlockX();
		int pos1Y = loc1.getBlockY();
		int pos1Z = loc1.getBlockZ();
		int pos2X = loc2.getBlockX();
		int pos2Y = loc2.getBlockY();
		int pos2Z = loc2.getBlockZ();
		try {
			stmt.execute("INSERT INTO PrisonCells_Prisons (pos1X, pos1Y, pos1Z, pos2X, pos2Y, pos2Z, world) VALUES ("+pos1X+", "+pos1Y+", "+pos1Z+", "+pos2X+", "+pos2Y+", "+pos2Z+", '"+world+"')");
			sendMessage(player, "&aCell successfully created");
		} catch (SQLException e) {
			e.printStackTrace();
			sendMessage(player, "&cCell creation failed");
		}
	}

	public void insertCell(Player player, Location loc1, Location loc2) {
		String world = loc1.getWorld().getName();
		int pos1X = loc1.getBlockX();
		int pos1Y = loc1.getBlockY();
		int pos1Z = loc1.getBlockZ();
		int pos2X = loc2.getBlockX();
		int pos2Y = loc2.getBlockY();
		int pos2Z = loc2.getBlockZ();
		try {
			stmt.execute("INSERT INTO PrisonCells_Cells (pos1X, pos1Y, pos1Z, pos2X, pos2Y, pos2Z, world) VALUES ("+pos1X+", "+pos1Y+", "+pos1Z+", "+pos2X+", "+pos2Y+", "+pos2Z+", '"+world+"')");
			sendMessage(player, "&aCell successfully created");
		} catch (SQLException e) {
			e.printStackTrace();
			sendMessage(player, "&cCell creation failed");
		}
	}
	
	public void buyCell(int id, int days, String player) {
		try {
			stmt.execute("UPDATE PrisonCells_Cells SET timeLeft = " + days + ", owner = '" + player + "' WHERE id = '" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(CommandSender sender, String string) {
		string = string.replaceAll("&", "§");
		sender.sendMessage(string);
	}
}

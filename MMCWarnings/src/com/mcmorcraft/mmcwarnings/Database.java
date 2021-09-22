package com.mcmorcraft.mmcwarnings;

import java.sql.*;

public class Database {

	private Statement stmt;
	
	public Database(String host, String port, String user, String pass){
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/minecraft?user="+user+"&password="+pass);
			stmt = con.createStatement();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public ResultSet getWarnings(String name){
		ResultSet result = null;
		try{
			result = stmt.executeQuery("SELECT * FROM warnings WHERE name = '" + name + "'");
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		if(result == null){
			return null;
		}
		return result;
	}
	
	public boolean insertWarning(String player, String reason, int points, String issuer){
		boolean result = true;
		try{
			result = stmt.execute("INSERT INTO warnings (name,reason,issuer,points) VALUES('" + player + "','" + reason + "','" + issuer + "'," + points + ")");
		 } catch (Exception e) {
			e.printStackTrace();
			result = false;
		 }
		return result;
	}
	
	public int countWarnings(String name){
		ResultSet result = null;
		try{
			result = stmt.executeQuery("SELECT * FROM warnings WHERE name = '" + name + "'");
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		if(result == null){
			return 0;
		} else {
			int tmp = 0;
			try {
				while(result.next()){
					if(!(result.getString("reason") == null)){
						tmp++;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return tmp;
		}
	}
	
	public boolean clearWarnings(String player){
		return false;
	}
}

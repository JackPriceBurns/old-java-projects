package com.jack.mages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
	
	private Statement stmt;
	
	public Database(String host, String port, String user, String pass, String database){
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/minecraft?user="+user+"&password="+pass);
			stmt = con.createStatement();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public ResultSet getUser(String name){
		ResultSet user = null;
		try{
			user = stmt.executeQuery("SELECT * FROM mages WHERE name = '" + name + "'");
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		if(user == null){
			return null;
		}
		return user;
	}
}

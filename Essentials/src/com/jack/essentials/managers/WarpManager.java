package com.jack.essentials.managers;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import org.bukkit.Location;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tonian.director.dm.json.JSonWriter;

public class WarpManager {
	
	private FileManager FM = new FileManager();
	private File warpsFile;
	private JSONObject warps;
	
	public WarpManager(){
		warpsFile = FM.getWarpsFile();
		
		if(!FM.fileExists(warpsFile)){
			JSONObject defaultConfig = new JSONObject();
			Writer writer = new JSonWriter();
			try {
				defaultConfig.writeJSONString(writer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			FM.createFile(warpsFile);
			FM.fileAppend(warpsFile, writer.toString());
		}
		reloadWarps();
	}
	
	public void reloadWarps(){
		String configFromFile = FM.readFile(warpsFile);
		JSONParser parser = new JSONParser();
		Object object;
		try {
			object = parser.parse(configFromFile);
			JSONObject jsonobject = (JSONObject)object;
			warps = jsonobject;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void saveWarps(){
		Writer writer = new JSonWriter();
		try {
			warps.writeJSONString(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		FM.fileReplaceContents(warpsFile, writer.toString());
	}

	public String getWarpCords(String name) {
		return (String) warps.get(name);
	}
	
	public boolean isWarp(String name){
		if(warps.containsKey(name)){
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public void setWarp(String warpname, Location warp) {
		String warploc = warp.getX() + ":" + warp.getY() + ":" + warp.getZ() + ":" + warp.getYaw() + ":" + warp.getPitch();
		warps.put(warpname, warploc);
		saveWarps();
	}

	public boolean delWarp(String warpname) {
		if(warps.containsKey(warpname)){
			warps.remove(warpname);
			saveWarps();
			return true;
		} else {
			return false;
		}
	}
	
	public Set<String> getWarps(){
		@SuppressWarnings("unchecked")
		Set<String> warpnames = warps.keySet();
		return warpnames;
	}
}

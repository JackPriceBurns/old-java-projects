package com.jack.essentials.managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jack.essentials.Essentials;

public class FileManager {

	private File dataFolder;
	private File configFile;
	private File userDataFolder;
	private File warpsFile;
	private Logger logger;
	
	public FileManager(){
		dataFolder = Essentials.getInstance().getDataFolder();
		userDataFolder = new File(dataFolder.getPath(), "UserData");
		configFile = new File(dataFolder.getPath(), "config.yml");
		warpsFile = new File(dataFolder.getPath(), "warps.yml");
		logger = Essentials.getInstance().getLogger();
		
		CheckFolders();
	}
	
	private void CheckFolders() {
		if(!dataFolder.exists()){
			createFolder(dataFolder);
		}
		if(!userDataFolder.exists()){
			createFolder(userDataFolder);
		}
	}

	public void deleteFile(File file){
		if(file.exists()){
			if(!file.delete()){
				logger.log(Level.SEVERE, "Failed to delete file: " + file.getName());
			}
		} else {
			logger.log(Level.INFO, "Tried to delete file, " + file.getName() + ", however the file does not exist.");
		}
	}
	
	public String readFile(File file){
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner;
		try {
			scanner = new Scanner(file);
			String lineSeparator = System.getProperty("line.separator");

		    try {
		        while(scanner.hasNextLine()) {        
		            fileContents.append(scanner.nextLine() + lineSeparator);
		        }
		        return fileContents.toString();
		    } finally {
		        scanner.close();
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public void fileReplaceContents(File file, String string){
		deleteFile(file);
		createFile(file);
		fileAppend(file, string);
	}
	
	public void fileAppend(File file, String string) {
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
		    out.println(string);
		    out.close();
		}catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to write string to file, file: " + file.getName());
		}
	}

	public void createFolder(File file){
		if(!file.mkdir()){
			logger.log(Level.SEVERE, "Unable to created folder: " + file.getName());
		}
	}
	
	public void createFile(File file){
		try {
			if(!file.createNewFile()){}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Unable to create file: " + file.getName());
			e.printStackTrace();
		}
	}
	
	public boolean fileExists(File file){
		if(file.exists()){
			return true;
		} else {
			return false;
		}
	}
	
	public File getDataFolder(){
		return dataFolder;
	}
	
	public File getConfigFile(){
		return configFile;
	}
	
	public File getUserDataFolder(){
		return userDataFolder;
	}

	public File getWarpsFile() {
		return warpsFile;
	}
}

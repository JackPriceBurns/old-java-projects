package com.jack.uhc.teams;

import java.util.HashSet;
import java.util.Set;

public class Team {

	private Set<String> players = new HashSet<String>();
	
	public Set<String> getPlayers(){
		return players;
	}
	
	public int countPlayers(){
		return players.size();
	}
	
	public void addPlayer(String name){
		players.add(name);
	}
	
	public boolean removePlayer(String name){
		if(isInTeam(name)){
			Set<String> newPlayers = new HashSet<String>();
			for(String player : players){
				if(!name.equals(player)){
					newPlayers.add(player);
				}
			}
			players = newPlayers;
			return true;
		}
		return false;
	}
	
	public boolean isInTeam(String name){
		boolean found = false;
		for(String player : players){
			if(name.equals(player)){
				found = true;
			}
		}
		return found;
	}
}

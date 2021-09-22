package com.jack.uhc.teams;

import java.util.HashSet;
import java.util.Set;

public class Teams {

	private Set<Team> teams = new HashSet<Team>();
	
	public Team createTeam(){
		Team team = new Team();
		return team;
	}
	
	public void addTeam(Team team){
		teams.add(team);
	}
	
	public boolean isInTeam(String name, Team team){
		if(team.isInTeam(name)){
			return true;
		} else {
			return false;
		}
	}
	
	public Team getTeam(String name){
		Team team = null;
		for(Team t : teams){
			if(t.isInTeam(name)){
				team = t;
			}
		}
		if(team == null){
			return null;
		} else {
			return team;
		}
	}
	
	public Set<Team> getTeams(){
		return teams;
	}
}

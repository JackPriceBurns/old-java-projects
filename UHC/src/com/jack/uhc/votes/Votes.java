package com.jack.uhc.votes;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.jack.uhc.timers.countdownTimer;

public class Votes {

	private Set<Vote> votes = new HashSet<Vote>();
	private boolean locked;
	
	public void removeVote(String voter){
		if(!locked){
			Set<Vote> newVotes = new HashSet<Vote>();
			for(Vote vote : votes){
				if(!vote.getName().equals(voter)){
					newVotes.add(vote);
				}
			}
			votes = newVotes;
		}
	}
	
	public void clearVotes(){
		if(!locked){
			votes = new HashSet<Vote>();
		}
	}
	
	public Object getVote(String voter){
		Vote vote = null;
		for(Vote v : votes){
			if(v.getName().equals(voter)){
				vote = v;
			}
		}
		return vote;
	}
	
	public void addVote(String voter, boolean vote){
		String votestr = "";
		if(vote){
			votestr = "yes";
		} else {
			votestr = "no";
		}
		if(!locked){
			if(getVote(voter) != null){
				if(((Vote) getVote(voter)).getVote() != vote){
					removeVote(voter);
				}
				votes.add(new Vote(vote, voter));
				Bukkit.getServer().broadcastMessage(voter + " has changed their vote to " + votestr);
			} else {
				votes.add(new Vote(vote, voter));
				Bukkit.getServer().broadcastMessage(voter + " has voted " + votestr);
			}
			shouldStartGame(true);
		}
	}
	
	private int yesvoteCount(){
		int yesvotes = 0;
		for(Vote vote : votes){
			if(vote.getVote() == true){
				yesvotes++;
			}
		}
		return yesvotes;
	}
	
	private int playerCount(){
		int playercount = 0;
		for(World w : Bukkit.getWorlds()){
			for(Player p : w.getPlayers()){
				if(p instanceof Player){
					playercount++;
				}
			}
		}
		return playercount;
	}
	
	public boolean shouldStartGame(boolean count) {
		if(playerCount() >= 8){
			int playerCount = playerCount();
			if(yesvoteCount() > (playerCount / 2)){
				if(count){
					new countdownTimer();
					return true;
				} else {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isLocked(){
		return locked;
	}
	
	public void unLock(){
		locked = false;
	}
	
	public void Lock(){
		locked = true;
	}
}

package com.jack.uhc.votes;

public class Vote {

	private boolean vote;
	private String name;
	
	public Vote(boolean vote, String name){
		this.vote = vote;
		this.name = name;
	}
	
	public boolean getVote(){
		return vote;
	}
	
	public String getName(){
		return name;
	}
}

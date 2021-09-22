package com.jack.uhc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.jack.uhc.commands.TeamChatCommand;
import com.jack.uhc.commands.VoteCommand;
import com.jack.uhc.listeners.Listeners;
import com.jack.uhc.teams.Teams;
import com.jack.uhc.timers.voteTimer;
import com.jack.uhc.utils.MetaUtils;
import com.jack.uhc.votes.Votes;

public class UHC extends JavaPlugin{

	private static UHC plugin;
	private Game G;
	private Votes V;
	private Teams T;
	private MetaUtils MU;
	
	@Override
	public void onEnable(){
		plugin = this;

		Bukkit.getPluginManager().registerEvents(new Listeners(), this);

		getCommand("vote").setExecutor(new VoteCommand());
		getCommand("teamchat").setExecutor(new TeamChatCommand());

		new voteTimer();

		getLogger().info("has been Enabled!");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("has been Disabled!");
	}
	
	public static UHC getPlugin(){
		return plugin;
	}
	
	public Game getGame(){
		if(G == null){
			G = new Game();
		}
		return G;
	}
	
	public Votes getVotes(){
		if(V == null){
			V = new Votes();
		}
		return V;
	}

	public Teams getTeams() {
		if(T == null){
			T = new Teams();
		}
		return T;
	}
	
	public MetaUtils getMetaUtils(){
		if(MU == null){
			MU = new MetaUtils();
		}
		return MU;
	}
}

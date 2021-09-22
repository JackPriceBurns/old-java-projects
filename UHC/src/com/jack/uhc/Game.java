package com.jack.uhc;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.jack.uhc.teams.Team;
import com.jack.uhc.teams.Teams;

public class Game {
	
	private World world = Bukkit.getWorld("UHC");
	private boolean started;
	
	public void startGame(){
		if(stillValid()){
			started = true;
			calculateTeams();
			spreadTeams();
			Bukkit.broadcastMessage("Game Started :D");
			Bukkit.broadcastMessage("Have Fun");
		} else {
			Bukkit.broadcastMessage("Game could not start not enough people or someone changed there vote!");
		}
	}

	private void spreadTeams() {
		Teams teams = UHC.getPlugin().getTeams();
		for(Team team : teams.getTeams()){
			Set<Player> players = new HashSet<Player>();
			for(String name : team.getPlayers()){
				Player player = null;
				for(World w : Bukkit.getWorlds()){
					for(Player p : w.getPlayers()){
						if(name.equals(p.getName())){
							player = p;
						}
					}
				}
				if(player != null){
					players.add(player);
				}
			}
			for(Player player : players){
				player.teleport(calculateTeleport());
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 15, 100000));
				player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 15, 100000));
				player.setGameMode(GameMode.SURVIVAL);
				player.setFlying(false);
			}
		}
	}
	
	private Location calculateTeleport(){
		int X = randNum(-1500, 1500);
		int Z = randNum(-1500, 1500);
		Location location = new Location(world, X, 64, Z);
		location = new Location(world, location.getX(), world.getHighestBlockYAt(location), location.getZ());
		if(location.getBlock().getBiome().equals(Biome.DEEP_OCEAN) || location.getBlock().getBiome().equals(Biome.OCEAN)){
			return calculateTeleport();
		} else {
			return location.getBlock().getLocation();
		}
	}
	
	private int randNum(int min, int max){
		Random rand = new Random();
		 int randomNum = rand.nextInt((max - min) + 1) + min;
		 return randomNum;
	}

	private void calculateTeams() {
		Teams teams = UHC.getPlugin().getTeams();
		if(playerCount() >= 8 && playerCount() < 12){
			for(String player : getPlayers()){
				Team team = teams.createTeam();
				team.addPlayer(player);
				teams.addTeam(team);
			}
		}
		if(playerCount() >= 12 && playerCount() < 16){
			int tmp = 0;
			Team team = new Team();
			for(String player : getPlayers()){
				tmp++;
				if(team == null){
					team = new Team();
				}
				if(tmp == 1){
					team.addPlayer(player);
				}
				if(tmp == 2){
					team.addPlayer(player);
					tmp = 0;
					teams.addTeam(team);
					team = null;
				}
			}
			if(team != null){
				teams.addTeam(team);
				team = null;
			}
		}
		if(playerCount() >= 16 && playerCount() < 32){
			int tmp = 0;
			Team team = new Team();
			for(String player : getPlayers()){
				tmp++;
				if(team == null){
					team = new Team();
				}
				if(tmp == 1){
					team.addPlayer(player);
				}
				if(tmp == 2){
					team.addPlayer(player);
					tmp = 0;
					teams.addTeam(team);
					team = null;
				}
			}
			if(team != null){
				teams.addTeam(team);
				team = null;
			}
		}
		if(playerCount() >= 32){
			int tmp = 0;
			Team team = new Team();
			for(String player : getPlayers()){
				tmp++;
				if(team == null){
					team = new Team();
				}
				if(tmp == 1){
					team.addPlayer(player);
				}
				if(tmp == 2){
					team.addPlayer(player);
				}
				if(tmp == 3){
					team.addPlayer(player);
				}
				if(tmp == 4){
					team.addPlayer(player);
					tmp = 0;
					teams.addTeam(team);
					team = null;
				}
			}
			if(team != null){
				teams.addTeam(team);
				team = null;
			}
		}
	}

	private boolean stillValid() {
		if(UHC.getPlugin().getVotes().shouldStartGame(false)){
			return true;
		} else {
			return false;
		}
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
	
	private Set<String> getPlayers(){
		Set<String> players = new HashSet<String>();
		for(World w : Bukkit.getWorlds()){
			for(Player p : w.getPlayers()){
				players.add(p.getName());
			}
		}
		return players;
	}

	public boolean isStarted() {
		return started;
	}
}

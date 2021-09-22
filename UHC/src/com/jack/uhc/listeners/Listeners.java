package com.jack.uhc.listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.inventory.ItemStack;

import com.jack.uhc.UHC;
import com.jack.uhc.teams.Team;
import com.jack.uhc.teams.Teams;
import com.jack.uhc.utils.MetaUtils;
import com.jack.uhc.votes.Votes;

public class Listeners implements Listener{

	private Votes votes;
	private Teams teams;
	private MetaUtils MU;
	
	public Listeners(){
		votes = UHC.getPlugin().getVotes();
		teams = UHC.getPlugin().getTeams();
		MU = UHC.getPlugin().getMetaUtils();
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		if(!UHC.getPlugin().getGame().isStarted()){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		if(!UHC.getPlugin().getGame().isStarted()){
			event.setCancelled(true);
		} else {
			if(event.getBlock().getType() == Material.STONE){
				Block block = event.getBlock();
				Location location = block.getLocation();
				World world = location.getWorld();
				int X = location.getBlockX();
				int Y = location.getBlockY();
				int Z = location.getBlockZ();
				Set<Location> locs = new HashSet<Location>();
				locs.add(new Location(world, X, Y + 1, Z));
				locs.add(new Location(world, X, Y - 1, Z));
				locs.add(new Location(world, X + 1, Y, Z));
				locs.add(new Location(world, X - 1, Y, Z));
				locs.add(new Location(world, X, Y, Z + 1));
				locs.add(new Location(world, X, Y, Z - 1));
				for(Location loc : locs){
					if(loc.getBlock().getType() == Material.IRON_ORE || loc.getBlock().getType() == Material.COAL_ORE || loc.getBlock().getType() == Material.REDSTONE_ORE){
						antiStripMine(loc);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		if(UHC.getPlugin().getGame().isStarted()){
			if(MU.hasMetadata(event.getPlayer(), "TeamChat")){
				String message = event.getMessage();
				Team team = teams.getTeam(event.getPlayer().getName());
				for(String name : team.getPlayers()){
					Player player = null;
					for(World w : Bukkit.getWorlds()){
						for(Player p : w.getPlayers()){
							if(name.equals(p.getName())){
								player = p;
							}
						}
					}
					player.sendMessage(message);
				}
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		if(UHC.getPlugin().getGame().isStarted()){
			if(event.getEntity() instanceof Player){
				if(event.getDamager() instanceof Player){
					Player victim = (Player) event.getEntity();
					Player damanger = (Player) event.getDamager();
					Teams teams = UHC.getPlugin().getTeams();
					if(teams.isInTeam(victim.getName(), teams.getTeam(damanger.getName()))){
						event.setCancelled(true);
					}
				}
			}
		} else {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event){
		if(event.getEntity() instanceof Player){
			if(!UHC.getPlugin().getGame().isStarted()){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		event.getPlayer().setAllowFlight(true);
		if(!UHC.getPlugin().getGame().isStarted()){
			event.getPlayer().setFlying(true);
			event.getPlayer().sendMessage("Welcome to UHC, there needs to be at least 8 people to play and more than half must vote to start the game.");
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event){
		event.getPlayer().setAllowFlight(true);
		if(UHC.getPlugin().getGame().isStarted()){
			event.disallow(Result.KICK_OTHER, "You can't join, there is a game in progress");
		} else {
			event.getPlayer().setFlying(true);
			event.getPlayer().sendMessage("Welcome to UHC, there needs to be at least 8 people to play and more than half must vote to start the game.");
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		Location location = player.getLocation();
		if(location.getBlockX() >= 2000 || location.getBlockX() <= -2000){
			if(location.getBlockX() >= 2000){
				player.teleport(new Location(location.getWorld(), 1980, location.getWorld().getHighestBlockYAt(location), location.getZ()));
			}
			if(location.getBlockX() <= -2000){
				player.teleport(new Location(location.getWorld(), -1980, location.getWorld().getHighestBlockYAt(location), location.getZ()));
			}
			player.sendMessage("There is a strange barrior stopping you from exploring further!");
			event.setCancelled(true);
		} else {
			if(location.getBlockZ() >= 2000 || location.getBlockZ() <= -2000){
				if(location.getBlockZ() >= 2000){
					player.teleport(new Location(location.getWorld(), location.getX(), location.getWorld().getHighestBlockYAt(location), 1980));
				}
				if(location.getBlockZ() <= -2000){
					player.teleport(new Location(location.getWorld(), location.getX(), location.getWorld().getHighestBlockYAt(location), -1980));
				}
				player.sendMessage("There is a strange barrior stopping you from exploring further!");
				event.setCancelled(true);
			}
		}
	}
	
	public void onPlayerQuit(PlayerQuitEvent event){
		if(!votes.isLocked()){
			votes.removeVote(event.getPlayer().getName());
		}
		if(UHC.getPlugin().getGame().isStarted()){
			World world = event.getPlayer().getWorld();
			Location location = event.getPlayer().getLocation();
			for(ItemStack stack : event.getPlayer().getInventory().getContents()){
				world.dropItemNaturally(location, stack);
			}
		}
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		if(UHC.getPlugin().getGame().isStarted()){
			event.getPlayer().kickPlayer("You have died.");
		} else {
			event.getPlayer().setAllowFlight(true);
			event.getPlayer().setFlying(true);
		}
	}
	
	public void antiStripMine(Location location){
		World world = location.getWorld();
		int X = location.getBlockX();
		int Y = location.getBlockY();
		int Z = location.getBlockZ();
		Set<Location> locs = new HashSet<Location>();
		locs.add(new Location(world, X, Y + 1, Z));
		locs.add(new Location(world, X, Y - 1, Z));
		locs.add(new Location(world, X + 1, Y, Z));
		locs.add(new Location(world, X - 1, Y, Z));
		locs.add(new Location(world, X, Y, Z + 1));
		locs.add(new Location(world, X, Y, Z - 1));
		boolean air = false;
		for(Location loc : locs){
			if(loc.getBlock().getType().equals(Material.AIR)){
				air = true;
			}
		}
		if(!air){
			location.getBlock().setType(Material.STONE);
		}
	}
}

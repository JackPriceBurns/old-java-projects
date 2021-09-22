package com.jack.prosigns.listeners;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.jack.prosigns.Prosigns;
import com.jack.prosigns.utils.Utils;

public class BlockPlaceListener implements Listener{

	private Prosigns plugin;
	private Utils U;
	
	public BlockPlaceListener(Prosigns p) {
		plugin = Prosigns.getInstance();
		U = plugin.getUtils();
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		if(event.getBlock().getState() instanceof Sign){
			Sign sign = (Sign) event.getBlock().getState();
			if(U.isProSign(sign)){
				event.setCancelled(true);
			}
		}
	}
}

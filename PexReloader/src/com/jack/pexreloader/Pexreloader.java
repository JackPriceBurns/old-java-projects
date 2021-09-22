package com.jack.pexreloader;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Pexreloader extends JavaPlugin {

	@Override
	public void onEnable() {
		new BukkitRunnable() {
			 
            @Override
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex reload");
            }
 
        }.runTaskTimer(this, 20, 600);
        getLogger().info("has been Enabled!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("has been Disabled!");
	}
}

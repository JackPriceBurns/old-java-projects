package com.mcmorcraft.elementalarrows;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcmorcraft.elementalarrows.listeners.*;
import com.mcmorcraft.elementalarrows.utils.FireworkEffectUtils;
import com.mcmorcraft.elementalarrows.utils.InventoryUtils;
import com.mcmorcraft.elementalarrows.utils.MetaUtils;

public class ElementalArrows extends JavaPlugin{
	
	private MetaUtils MU;
	private ArrowEffector AE;
	private FireworkEffectUtils EFU;
	private ArrowFlyingEffect AFE;
	private InventoryUtils IU;
	private List<ElementalArrow> ArrowList = new ArrayList<ElementalArrow>();
	
	public static final String PLUGIN_TAG = ChatColor.RED + "[" + ChatColor.DARK_AQUA + "Elemental Arrows" + ChatColor.RED + "]" + ChatColor.DARK_AQUA;
	
	@Override
	public void onEnable(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BowListener(this), this);
		pm.registerEvents(new ArrowListener(this), this);
		
		getLogger().info("has been enabled!");
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public MetaUtils getMetaUtils(){
		if(MU == null){
			MU = new MetaUtils(this);
		}
		return MU;
	}
	
	public List<ElementalArrow> getArrowList(){
		if(ArrowList.isEmpty() || ArrowList == null){
			ArrowList.add(new ElementalArrow("Explosive", "[Explosive Bow]", Material.SULPHUR, 4));
			ArrowList.add(new ElementalArrow("Poison", "[Poison Bow]", Material.SPIDER_EYE, 1));
		}
		return ArrowList;
	}

	public InventoryUtils getInventoryUtils() {
		if(IU == null){
			IU = new InventoryUtils();
		}
		return IU;
	}

	public ArrowEffector getArrowEffector() {
		if(AE == null){
			AE = new ArrowEffector(this);
		}
		return AE;
	}

	public ArrowFlyingEffect getArrowFlyingEffect() {
		if(AFE == null){
			AFE = new ArrowFlyingEffect(this);
		}
		return AFE;
	}

	public FireworkEffectUtils getFireworkEffectUtils() {
		if(EFU == null){
			EFU = new FireworkEffectUtils();
		}
		return EFU;
	}
}

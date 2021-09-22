package com.jack.prisoncells.utils;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.jack.prisoncells.PrisonCells;

public class MetaUtils {
	
	private PrisonCells plugin;
	
	public MetaUtils(PrisonCells p){
		plugin = p;
	}
	
	public void setMetaData(Player player, String key, Object value){
		player.setMetadata(key, new FixedMetadataValue(plugin, value));
	}
	
	public Object getMetaData(Player player, String key){
		List<MetadataValue> values = player.getMetadata(key);
		for(MetadataValue val : values){
			if(val.getOwningPlugin().getDescription().getName().equals(plugin.getDescription().getName())){
				return val.value();
			}
		}
		return null;
	}
	
	public boolean hasMetaData(Player player, String key){
		List<MetadataValue> values = player.getMetadata(key);
		for(MetadataValue val : values){
			if(val.getOwningPlugin().getDescription().getName().equals(plugin.getDescription().getName())){
				return true;
			}
		}
		return false;
	}
}

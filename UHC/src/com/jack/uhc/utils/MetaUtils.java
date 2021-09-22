package com.jack.uhc.utils;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.jack.uhc.UHC;

public class MetaUtils {
	
	private UHC plugin;
	
	public MetaUtils(){
		plugin = UHC.getPlugin();
	}
	
	public void setMetaData(Player player, String key, Object value){
		player.setMetadata(key, new FixedMetadataValue(plugin, value));
	}
	
	public void removeMetaData(Player player, String key){
		player.removeMetadata(key, plugin);
	}
	
	public Object getMetadata(Player player, String key){
		List<MetadataValue> values = player.getMetadata(key);
		for(MetadataValue value: values){
			if(value.getOwningPlugin().getDescription().getName().equals(plugin.getDescription().getName())){
				value.value();
			}
		}
		return null;
	}
	
	public boolean hasMetadata(Player player, String key){
		List<MetadataValue> values = player.getMetadata(key);
		for(MetadataValue value: values){
			if(value.getOwningPlugin().getDescription().getName().equals(plugin.getDescription().getName())){
				return true;
			}
		}
		return false;
	}
}

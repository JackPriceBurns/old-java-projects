package com.jack.iclasses.utils;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.jack.iclasses.IClasses;

public class MetaUtils {
	
	private IClasses plugin;
	
	public MetaUtils(){
		plugin = IClasses.getInstance();
	}
	
	public void setMetaData(Player player, String key, Object value){
		player.setMetadata(key, new FixedMetadataValue(plugin, value));
	}
	
	public void removeMetaData(Player player, String key){
		player.removeMetadata(key, plugin);
	}
	
	public void changeMetaData(Player player, String key, Object value){
		player.removeMetadata(key, plugin);
		player.setMetadata(key, new FixedMetadataValue(plugin, value));
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

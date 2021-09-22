package com.mcmorcraft.elementalarrows.utils;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.mcmorcraft.elementalarrows.ElementalArrows;

public class MetaUtils {

	private final ElementalArrows plugin;

	public MetaUtils(ElementalArrows p) {
		plugin = p;
	}

	public void setMetaData(Entity entity, String key, Object value) {
		entity.setMetadata(key, new FixedMetadataValue(plugin, value));
	}
	
	public void removeMetaData(Entity entity, String key) {
		entity.removeMetadata(key, plugin);
	}

	public Object getMetadata(Entity entity, String key) {
		List<MetadataValue> values = entity.getMetadata(key);
		for (MetadataValue val : values) {
			if (val.getOwningPlugin().getDescription().getName().equals(plugin.getDescription().getName())) {
				return val.value();
			}
		}
		return null;
	}

	public Boolean hasMetadata(Entity arrow, String key) {
		List<MetadataValue> values = arrow.getMetadata(key);
		for (MetadataValue val : values) {
			if (val.getOwningPlugin().getDescription().getName().equals(plugin.getDescription().getName())) {
				return true;
			}
		}
		return false;
	}
	
}

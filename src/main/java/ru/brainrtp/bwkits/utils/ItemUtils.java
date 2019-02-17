package ru.brainrtp.bwkits.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class ItemUtils {
	@SuppressWarnings("deprecation")
	public static ItemStack create(Material material, int amount, byte data, String displayName, List<String> lore) {
		ItemStack item = new ItemStack(material, amount, data);
		ItemMeta meta = item.getItemMeta();
		if(lore != null){
			meta.setLore(lore);
		}
		meta.setDisplayName(displayName);

		item.setItemMeta(meta);
		
		return item;
	}
	public static ItemStack create (Material material, String displayName) {
		return create(material, 1, (byte)0, displayName, null);
	}
	public static ItemStack create (Material material, byte data, String displayName) {
		return create(material, 1, data, displayName, null);
	}
	public static ItemStack create (Material material, int amount, String displayName) {
		return create(material, amount, (byte)0, displayName, null);
	}
	public static ItemStack create (Material material, int amount, byte data, String displayName) {
		return create(material, 1, data, displayName, null);
	}
	
	public static List<String> modifyLore(List<String> lore, String... str){
		for(String s : str) {
			lore.add(s);
		}
		return lore;
	}
}

package ru.brainrtp.bwkits.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import ru.brainrtp.bwkits.utils.ColorUtils;

public class Menu implements InventoryHolder {

	private Inventory inv;
	  
	Menu(String title){
		this.inv = Bukkit.createInventory(this, 54, ColorUtils.color(title));
	}
	  
	public Inventory getInventory(){
		return this.inv;
	}
}

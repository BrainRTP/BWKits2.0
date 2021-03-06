package ru.brainrtp.bwkits.gui;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.brainrtp.bwkits.BWKits;
import ru.brainrtp.bwkits.listeners.BedWarsRelListeners;
import ru.brainrtp.bwkits.utils.ColorUtils;
import ru.brainrtp.bwkits.utils.ItemUtils;
import ru.brainrtp.bwkits.utils.Kit;
import ru.brainrtp.bwkits.utils.XMaterial;
import ru.brainrtp.bwkits.yml.LanguageConfig;

import java.util.List;

public class PlayerKitsMenu implements Listener {

	private static LanguageConfig lang;
	private static ItemStack resetKit;

	public static void defineStaticItems(LanguageConfig lang) {
		PlayerKitsMenu.lang = lang;
		resetKit = ItemUtils.create(XMaterial.BARRIER.parseMaterial(), ColorUtils.color(lang.getMsg("menuResetKit", false)));
	}

	public static void open(Player player) {
		int index = 0;
		Menu menu = new Menu(lang.getMsg("menuTitle", false));
		menu.getInventory().setItem(49, resetKit);

		for (Kit kit : BWKits.kits.values()) {
			menu.getInventory().setItem(index++, createItem(player, kit));
		}
		player.openInventory(menu.getInventory());
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(event.getInventory().getHolder() instanceof Menu) {
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);

			ItemStack item = event.getCurrentItem();
			if(item == null)
				return;

			if(item.equals(resetKit)) {
				player.sendMessage(lang.getMsg("kitClear", true));
				BedWarsRelListeners.playerKit.remove(player);
				player.closeInventory();
			}

			for(Kit kit : BWKits.kits.values()) {
				if (item.getType().equals(XMaterial.fromString("GRAY_DYE").parseMaterial())){
					player.sendMessage(lang.getMsg("kitNoPermission", true));
					player.closeInventory();
					return;
				}
				if (kit.getName().equals(item.getItemMeta().getDisplayName())) {
					if(!BedWarsRelListeners.playerKit.containsKey(player)) {
						BedWarsRelListeners.playerKit.put(player, kit.getId());
					} else {
						BedWarsRelListeners.playerKit.replace(player, kit.getId());
					}
					player.sendMessage(lang.getMsg("kitSelected", true).replace("%s", kit.getName()));
					player.closeInventory();
					return;
				}
			}
		}
	}

	private static ItemStack createItem(Player player, Kit kit){
		ItemStack itemStack =  new ItemStack(kit.getItemStack());
		ItemMeta itemMeta = itemStack.getItemMeta();

		if (kit.getPermission() != null){
			if (player.hasPermission(kit.getPermission())){
				if (BedWarsRelListeners.playerKit.containsKey(player) && kit.getId().equals(BedWarsRelListeners.playerKit.get(player))) {
					itemStack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
					return itemStack;
				} else {
					return itemStack;
				}
			} else {
				itemStack.setType(XMaterial.fromString("GRAY_DYE").parseMaterial());
				itemMeta.setDisplayName(ChatColor.GRAY + itemMeta.getDisplayName());
				List<String> lore = itemMeta.getLore();
				lore.add(" ");
				lore.add(lang.getMsg("kitNoPermission", false));
				itemMeta.setLore(lore);
				itemStack.setItemMeta(itemMeta);
				return itemStack;
			}
		} else {
			if (BedWarsRelListeners.playerKit.containsKey(player) && kit.getId().equals(BedWarsRelListeners.playerKit.get(player))) {
				itemStack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
				return itemStack;
			} else {
				return itemStack;
			}
		}
	}
//	@EventHandler
//	public void onInventoryClose(InventoryCloseEvent e) {
//		if(e.getInventory().getHolder() instanceof Menu) {
//			if(e.getPlayer() instanceof Player) {
//				if(npcs.containsKey(e.getPlayer())) {
//					npcs.remove(e.getPlayer());
//				}
//			}
//		}
//	}
}

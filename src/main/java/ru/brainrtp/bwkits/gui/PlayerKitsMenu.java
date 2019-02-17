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

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PlayerKitsMenu implements Listener {
	
	private static HashMap<Integer, Menu> invs = new HashMap<>();
	private static LanguageConfig lang;
	private static ItemStack resetKit;

//	public PlayerKitsMenu(LanguageConfig lang) {
//		PlayerKitsMenu.lang = lang;
//		resetKit = ItemUtils.create(XMaterial.BARRIER.parseMaterial(), ColorUtils.color(lang.getMsg("menu.resetKit", false)));
//	}

	public static void defineStaticItems(LanguageConfig lang) {
		PlayerKitsMenu.lang = lang;
		resetKit = ItemUtils.create(XMaterial.BARRIER.parseMaterial(), ColorUtils.color(lang.getMsg("menuResetKit", false)));
	}
	
	public static void open(Player player, List<ItemStack> bwitems) {
		List<ItemStack> items = bwitems;
		int maxItems = 45;
		int itemCount = 0;
//		double pagesCount = Math.ceil((double)items.size()/maxItems);
		System.out.println("=========");
		System.out.println(items);
		System.out.println("=========");
		if(items.isEmpty()) {
			Menu menu = new Menu(lang.getMsg("menuTitle", false));
			menu.getInventory().setItem(49, resetKit);
//			menu.getInventory().setItem(22, ItemUtils.create(UndependMaterial.get(Materials.WEB), ColorUtil.color(CSM.getLang().get().getString("menu.emptyMenu"))));
			
			invs.put(1, menu);
			
			player.openInventory((invs.get(1)).getInventory());
			return;
		}
		

		int limit = 0;
		Menu menu = new Menu(lang.getMsg("menuTitle", false));

		menu.getInventory().setItem(49, resetKit);

		for(int a = 0; a < items.size(); a++) {
			if(limit != maxItems) {
				if(menu.getInventory().getItem(limit) == null) {
					menu.getInventory().setItem(limit, createItem(player, items.get(itemCount)));
				}
				limit++;
				if(itemCount<(items.size()-1)) {
					itemCount++;
				}
				else {
					break;
				}
			}
		}
		invs.put(1, menu);

		player.openInventory((invs.get(1)).getInventory());
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
//				MessageManager.sendMessage(player, CSM.getLang().get().getString("successResetSkin"));
			}
			
			for(Kit kit : BWKits.kits.values()) {
//				if(kit.getName().equals(item.getItemMeta().getDisplayName())) {
//				System.out.println("1 - " + kit.getItemStack());
//				System.out.println("2 - " + item);
//				System.out.println(kit.getItemStack() == item);
				if(kit.getItemStack() == item) {
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

	private static ItemStack createItem(Player player, ItemStack itemStack){
		System.out.println(itemStack);
		Kit kit = BWKits.iconsKits.get(itemStack);
		if (kit.getPermission() != null && player.hasPermission(kit.getPermission())) {
			if (BedWarsRelListeners.playerKit.containsKey(player)) {
				itemStack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
				return itemStack;
			} else {
				return itemStack;
			}
		} else {
			itemStack.setType(XMaterial.fromString("GRAY_DYE").parseMaterial());
			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setDisplayName(ChatColor.GRAY + itemMeta.getDisplayName());
			List<String> lore = itemMeta.getLore();
			lore.add(" ");
			lore.add(lang.getMsg("kitNoPermission", false));
			itemMeta.setLore(lore);
			itemStack.setItemMeta(itemMeta);
			return itemStack;
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

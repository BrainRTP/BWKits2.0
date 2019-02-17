package ru.brainrtp.bwkits.listeners;

import io.github.bedwarsrel.events.BedwarsGameStartedEvent;
import io.github.bedwarsrel.events.BedwarsPlayerLeaveEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import ru.brainrtp.bwkits.BWKits;
import ru.brainrtp.bwkits.utils.ColorUtils;
import ru.brainrtp.bwkits.utils.Kit;
import ru.brainrtp.bwkits.utils.XMaterial;
import ru.brainrtp.bwkits.yml.LanguageConfig;

import java.util.HashMap;

public class BedWarsRelListeners implements Listener {

    private BWKits plugin;
    private LanguageConfig lang;
    public static HashMap<Player, String> playerKit = new HashMap<>();

    public BedWarsRelListeners(BWKits plugin, LanguageConfig lang) {
        this.plugin = plugin;
        this.lang = lang;
    }

    @EventHandler
    public void onGameStart(BedwarsGameStartedEvent event){
        if (event == null){
            return;
        }

        event.getGame().getPlayers().forEach(player -> {
            if (playerKit.containsKey(player)){
                player.sendMessage(ColorUtils.color(lang.getMsg("kitRecived", false).replace("%s", BWKits.kits.get(playerKit.get(player)).getName())));
                Kit kit = BWKits.kits.get(playerKit.get(player));
                kit.getEquipments().forEach((k, v) -> {
                    switch (k){
                        case "items":
                            v.forEach(item -> {
                                String newItem = (String) item;
                                ItemStack is = XMaterial.valueOf(newItem.split(":")[0]).parseItem();
                                int ammount = Integer.parseInt(newItem.split(":")[1]);
                                is.setAmount(ammount);
                                player.getInventory().addItem(is);
                            });
                            break;
                        case "helmet":
                            player.getInventory().setHelmet(XMaterial.valueOf((String) v.get(0)).parseItem());
                            break;
                        case "chestplate":
                            player.getInventory().setChestplate(XMaterial.valueOf((String) v.get(0)).parseItem());
                            break;
                        case "leggings":
                            player.getInventory().setLeggings(XMaterial.valueOf((String) v.get(0)).parseItem());
                            break;
                        case "boots":
                            player.getInventory().setBoots(XMaterial.valueOf((String) v.get(0)).parseItem());
                            break;
                    }
                });
            }
        });
        playerKit.clear();
    }

    @EventHandler
    public void onPlayerLeaveGame(BedwarsPlayerLeaveEvent event){
        if (event == null) {
            return;
        }

        if (!playerKit.isEmpty()) {
            playerKit.remove(event.getPlayer());
        }
    }
}

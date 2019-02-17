package ru.brainrtp.bwkits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ru.brainrtp.bwkits.commands.CommandsRegistrator;
import ru.brainrtp.bwkits.commands.KitsCmd;
//import ru.brainrtp.bwkits.gui.PlayerKitsMenu;
import ru.brainrtp.bwkits.gui.PlayerKitsMenu;
import ru.brainrtp.bwkits.listeners.BedWarsRelListeners;
import ru.brainrtp.bwkits.utils.Kit;
import ru.brainrtp.bwkits.utils.XMaterial;
import ru.brainrtp.bwkits.yml.KitsConfig;
import ru.brainrtp.bwkits.yml.LanguageConfig;

import javax.security.auth.login.Configuration;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public final class BWKits extends JavaPlugin {


    private static Plugin plugin;
    private LanguageConfig languageConfig;
    private KitsConfig kitsConfig;
//    private Map<String, List> eq = new HashMap<>();
    public static HashMap<String, Kit> kits = new HashMap<>();
    public static HashMap<ItemStack, Kit> iconsKits = new HashMap<>();
    public final static List<ItemStack> iconsKitsList = new ArrayList<>();

    @Override
    public void onEnable(){
        plugin = this;
        checkDependencies();
        languageConfig = new LanguageConfig(this);
        kitsConfig = new KitsConfig(this);
        CommandsRegistrator.reg(this, new KitsCmd(languageConfig), new String[] {"bwk"}, "BWKits command", "/bwk");
        plugin.getServer().getPluginManager().registerEvents(new BedWarsRelListeners(this, languageConfig),this);
        plugin.getServer().getPluginManager().registerEvents(new PlayerKitsMenu(), this);
        PlayerKitsMenu.defineStaticItems(languageConfig);
        prepareKits();
    }

    private void prepareKits(){
        kitsConfig.getFileConfiguration().getKeys(true).forEach(key -> {
            if (kitsConfig.getFileConfiguration().isConfigurationSection(key)) {
                if (key.split("[.]").length == 1) {
                    ConfigurationSection configurationSection = kitsConfig.getFileConfiguration().getConfigurationSection(key);
                    String kitName = ChatColor.translateAlternateColorCodes('&', configurationSection.getString("name"));
                    String iconItem = configurationSection.getString("iconItem");
                    String permission = configurationSection.getString("permission");
                    int cost = configurationSection.getInt("cost");
                    List<String> description = (List<String>)configurationSection.getList("description");
//                    System.out.println(desc);
//                    description.replaceAll(txt -> ChatColor.translateAlternateColorCodes('&', (String) txt));
                    description.replaceAll(txt -> ChatColor.translateAlternateColorCodes('&', txt));
                    ConfigurationSection equipment = configurationSection.getConfigurationSection("equipment");
                    Map<String, List> eq = new HashMap<>();
                    equipment.getKeys(true).forEach(equipments -> {
                        switch (equipments){
                            case "items":
                                eq.put("items", equipment.getList(equipments));
//                                System.out.println("Предметы - " + equipment.getList(equipments));
                                break;
                            case "helmet":
                                eq.put("helmet", Collections.singletonList(equipment.getString(equipments)));
//                                System.out.println("Шлем - " + equipment.getString(equipments));
                                break;
                            case "chestplate":
                                eq.put("chestplate", Collections.singletonList(equipment.getString(equipments)));
//                                System.out.println("Броник - " + equipment.getString(equipments));
                                break;
                            case "leggings":
                                eq.put("leggings", Collections.singletonList(equipment.getString(equipments)));
//                                System.out.println("Леггинсы - " + equipment.getString(equipments));
                                break;
                            case "boots":
                                eq.put("boots", Collections.singletonList(equipment.getString(equipments)));
                                break;
//                                System.out.println("Обувь - " + equipment.getString(equipments));
                        }
                    });
//                    System.out.println("kitName - " + kitName);
//                    System.out.println("iconItem - " + iconItem);
//                    description.forEach(System.out::println);
//                    System.out.println("permission - " + permission);
//                    System.out.println("cost - " + cost);
//                    System.out.println("key - " + key);
//                    eq.forEach((k, v) -> {
//                        System.out.println("Key - " + k + " | Value - " + v);
//                    });
//                    System.out.println("========");
                    ItemStack is = XMaterial.valueOf(iconItem).parseItem();
                    ItemMeta im = is.getItemMeta();
                    im.setDisplayName(kitName);
                    im.setLore(description);
                    is.setItemMeta(im);
                    Kit kit = new Kit(key, kitName, permission, cost, description, eq, XMaterial.valueOf(iconItem).parseMaterial(), is);
                    iconsKits.put(is, kit);
                    iconsKitsList.add(is);
                    kits.put(key, kit);
                }
            }
        });
//        kits.forEach((k, v) -> {
//            System.out.println(v.getName());
//            System.out.println(v.getEquipments());
//        });
    }

    private void checkDependencies(){
        if(getServer().getPluginManager().getPlugin("Vault") == null){
            getLogger().severe("Vault is not installed!");
            getServer().getPluginManager().disablePlugin(this);
        }
    }
}

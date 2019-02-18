package ru.brainrtp.bwkits.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import ru.brainrtp.bwkits.BWKits;
import ru.brainrtp.bwkits.listeners.BedWarsRelListeners;
import ru.brainrtp.bwkits.yml.KitsConfig;
import ru.brainrtp.bwkits.yml.LanguageConfig;

public class ReloadCmd  implements CommandExecutor {
    private LanguageConfig lang;
    private KitsConfig kitsConfig;

    public ReloadCmd(LanguageConfig lang, KitsConfig kitsConfig){
        this.lang = lang;
        this.kitsConfig = kitsConfig;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("bwr")) {
            return false;
        }
        if (!sender.hasPermission("bwk.reload")) {
            sender.sendMessage(lang.getMsg("permissionDeny", true));
            return true;
        }
        Bukkit.getOnlinePlayers().forEach(HumanEntity::closeInventory);
        BedWarsRelListeners.playerKit.clear();
        lang.reload();
        kitsConfig.reload();
        BWKits.prepareKits();
        sender.sendMessage(lang.getMsg("reloadSuccess", true));
        return true;
    }
}
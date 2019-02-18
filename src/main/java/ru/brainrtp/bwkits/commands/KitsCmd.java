package ru.brainrtp.bwkits.commands;

import io.github.bedwarsrel.BedwarsRel;
import io.github.bedwarsrel.game.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.brainrtp.bwkits.BWKits;
import ru.brainrtp.bwkits.gui.PlayerKitsMenu;
import ru.brainrtp.bwkits.yml.LanguageConfig;

public class KitsCmd implements CommandExecutor {

    private LanguageConfig lang;
    private BWKits plugin;

    public KitsCmd(LanguageConfig lang) {
        this.lang = lang;
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)){
            return false;
        }
        if (!command.getName().equalsIgnoreCase("bwk")) {
            return false;
        }
        if (!sender.hasPermission("bwk.player")) {
            sender.sendMessage(lang.getMsg("permissionDeny", true));
            return true;
        }
        Player player = (Player) sender;
        for (Game game : BedwarsRel.getInstance().getGameManager().getGames()) {
            if (game.getPlayers().contains(sender)) {
                PlayerKitsMenu.open(player);
                return false;
            }
        }
        sender.sendMessage(lang.getMsg("notInGame", true));

        return true;
    }
}
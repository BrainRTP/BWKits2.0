package ru.brainrtp.bwkits.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CommandsRegistrator extends Command implements PluginIdentifiableCommand {
	protected Plugin plugin;
	protected final CommandExecutor owner;
	protected final Object registeredWith;

	public CommandsRegistrator(String[] aliases, String desc, String usage, CommandExecutor owner, Object registeredWith,
                               Plugin plugin2) {
		super(aliases[0], desc, usage, Arrays.asList(aliases));
		this.owner = owner;
		this.plugin = plugin2;
		this.registeredWith = registeredWith;
	}

	@Override
	public Plugin getPlugin() {
		return this.plugin;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		return this.owner.onCommand(sender, this, label, args);
	}

	public static void reg(Plugin plugin, CommandExecutor cxecutor, String[] aliases, String desc, String usage) {
		try {
			CommandsRegistrator reg = new CommandsRegistrator(aliases, desc, usage, cxecutor, new Object(), plugin);
			Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			field.setAccessible(true);
			CommandMap map = (CommandMap) field.get(Bukkit.getServer());
			map.register(plugin.getDescription().getName(), reg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
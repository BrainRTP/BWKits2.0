package ru.brainrtp.bwkits.utils;

import org.bukkit.ChatColor;

public final class ColorUtils {

    private ColorUtils() {
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
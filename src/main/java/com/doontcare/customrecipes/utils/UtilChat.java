package com.doontcare.customrecipes.utils;

import org.bukkit.ChatColor;

public class UtilChat {

    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}

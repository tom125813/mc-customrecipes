package com.doontcare.customrecipes.utils;

import org.bukkit.inventory.ItemFlag;

public class UtilFlags {

    public static ItemFlag getFlag(String text) {
        switch(text.toLowerCase()) {
            case "hide attributes":
                return ItemFlag.HIDE_ATTRIBUTES;
            case "hide enchantments":
                return ItemFlag.HIDE_ENCHANTS;
            default:
                return null;
        }
    }

}

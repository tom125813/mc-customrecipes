package com.doontcare.customrecipes.utils;

import org.bukkit.enchantments.Enchantment;

public class UtilEnchantments {

    public static Enchantment getEnchantment(String text) {
        switch(text.toLowerCase()) {
            case "sharpness":
                return Enchantment.DAMAGE_ALL;
            case "unbreaking":
                return Enchantment.DURABILITY;
            default:
                return null;
        }
    }
}

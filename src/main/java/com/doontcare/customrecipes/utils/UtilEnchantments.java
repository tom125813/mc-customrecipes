package com.doontcare.customrecipes.utils;

import org.bukkit.enchantments.Enchantment;

public class UtilEnchantments {

    public static Enchantment getEnchantment(String text) {
        switch(text.toLowerCase()) {
            case "aqua affinity":
                return Enchantment.WATER_WORKER;
            case "bane of arthropods":
                return Enchantment.DAMAGE_ARTHROPODS;
            case "blast protection":
                return Enchantment.PROTECTION_EXPLOSIONS;
            case "channeling":
                return Enchantment.CHANNELING;
            case "curse of binding":
                return Enchantment.BINDING_CURSE;
            case "curse of vanishing":
                return Enchantment.VANISHING_CURSE;
            case "depth strider":
                return Enchantment.DEPTH_STRIDER;
            case "efficiency":
                return Enchantment.DIG_SPEED;
            case "feather falling":
                return Enchantment.PROTECTION_FALL;
            case "fire aspect":
                return Enchantment.FIRE_ASPECT;
            case "fire protection":
                return Enchantment.PROTECTION_FIRE;
            case "flame":
                return Enchantment.ARROW_FIRE;
            case "fortune":
                return Enchantment.LOOT_BONUS_BLOCKS;
            case "frost walker":
                return Enchantment.FROST_WALKER;
            case "impaling":
                return Enchantment.IMPALING;
            case "infinity":
                return Enchantment.ARROW_INFINITE;
            case "knockback":
                return Enchantment.KNOCKBACK;
            case "looting":
                return Enchantment.LOOT_BONUS_MOBS;
            case "loyalty":
                return Enchantment.LOYALTY;
            case "luck of the sea":
                return Enchantment.LUCK;
            case "lure":
                return Enchantment.LURE;
            case "mending":
                return Enchantment.MENDING;
            case "multishot":
                return Enchantment.MULTISHOT;
            case "piercing":
                return Enchantment.PIERCING;
            case "power":
                return Enchantment.ARROW_DAMAGE;
            case "projectile protection":
                return Enchantment.PROTECTION_PROJECTILE;
            case "protection":
                return Enchantment.PROTECTION_ENVIRONMENTAL;
            case "punch":
                return Enchantment.ARROW_KNOCKBACK;
            case "quick charge":
                return Enchantment.QUICK_CHARGE;
            case "respiration":
                return Enchantment.OXYGEN;
            case "riptide":
                return Enchantment.RIPTIDE;
            case "sharpness":
                return Enchantment.DAMAGE_ALL;
            case "silk touch":
                return Enchantment.SILK_TOUCH;
            case "smite":
                return Enchantment.DAMAGE_UNDEAD;
            case "soul speed":
                return Enchantment.SOUL_SPEED;
            case "sweeping edge":
                return Enchantment.SWEEPING_EDGE;
            case "swift sneak":
                return Enchantment.SWIFT_SNEAK;
            case "thorns":
                return Enchantment.THORNS;
            case "unbreaking":
                return Enchantment.DURABILITY;
            default:
                return null;
        }
    }
}

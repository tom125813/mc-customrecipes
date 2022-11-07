package com.doontcare.customrecipes.manager;

import com.doontcare.customrecipes.CustomRecipes;
import com.doontcare.customrecipes.utils.UtilChat;
import com.doontcare.customrecipes.utils.UtilEnchantments;
import com.doontcare.customrecipes.utils.UtilFlags;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemHandler {


    public static ItemStack getCustomItem(String identifier) {
        File file = new File(CustomRecipes.getInstance().getDataFolder(), "recipes.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ItemStack customItemStack = new ItemStack(Material.valueOf(config.getString("recipes." + identifier + ".material").toUpperCase().replaceAll(" ", "_")));
        ItemMeta customItemMeta = customItemStack.getItemMeta();
        customItemMeta.setDisplayName(UtilChat.translate(config.getString("recipes." + identifier + ".displayname")));
        List<String> customItemLore = new ArrayList<>();
        config.getStringList("recipes." + identifier + ".lore").forEach(l -> {
            customItemLore.add(UtilChat.translate(l));
        });
        customItemMeta.setLore(customItemLore);

        // ITEM FLAGS

        if (config.getConfigurationSection("recipes." + identifier + ".flags") != null) {
            for (String flagIdentifier : config.getConfigurationSection("recipes." + identifier + ".flags").getKeys(false)) {
                if (config.getBoolean("recipes." + identifier + ".flags." + flagIdentifier))
                    customItemMeta.addItemFlags(UtilFlags.getFlag(flagIdentifier));
            }
        }

        // ENCHANTS

        if (config.getConfigurationSection("recipes." + identifier + ".enchantments") != null) {
            for (String enchantIdentifier : config.getConfigurationSection("recipes." + identifier + ".enchantments").getKeys(false)) {
                customItemMeta.addEnchant(UtilEnchantments.getEnchantment(enchantIdentifier), config.getInt("recipes." + identifier + ".enchantments." + enchantIdentifier), true);
            }
        }

        // SETTING ITEM META
        customItemStack.setItemMeta(customItemMeta);
        return customItemStack;
    }
}

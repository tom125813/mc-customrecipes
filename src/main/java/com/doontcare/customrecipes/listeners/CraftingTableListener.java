package com.doontcare.customrecipes.listeners;

import com.doontcare.customrecipes.CustomRecipes;
import com.doontcare.customrecipes.events.CustomRecipeCraftEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class CraftingTableListener implements Listener {


    @EventHandler
    public void customCraftCheck(CraftItemEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if (CustomRecipes.getInstance().getRecipeManager().contains(e.getRecipe()))
            Bukkit.getServer().getPluginManager().callEvent(new CustomRecipeCraftEvent(player, item));
    }

}

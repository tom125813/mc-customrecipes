package com.doontcare.customrecipes.listeners;

import com.doontcare.customrecipes.events.CustomRecipeCraftEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CustomRecipeListener implements Listener {

    @EventHandler
    public void customRecipeCraft(CustomRecipeCraftEvent e) {
        Bukkit.broadcastMessage(
                ChatColor.translateAlternateColorCodes(
                        '&',
                        "&6[CustomRecipes] &eCustom item crafted by " + e.getPlayer().getName()
                ));
    }

}

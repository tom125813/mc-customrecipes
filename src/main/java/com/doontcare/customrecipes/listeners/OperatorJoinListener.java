package com.doontcare.customrecipes.listeners;

import com.doontcare.customrecipes.CustomRecipes;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OperatorJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (e.getPlayer().isOp() && CustomRecipes.getInstance().isOutdated()) {
            e.getPlayer().sendMessage(" ");
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes(
                    '&',
                    "&4[CustomRecipes] &cYou are running an outdated version. " +
                            "&cVisit the download page to stay up to date or disable this message in &nsettings.yml&c"
            ));
            e.getPlayer().sendMessage(" ");
        }
    }
}

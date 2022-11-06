package com.doontcare.customrecipes.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class CustomRecipeCraftEvent extends Event {

    // Replace ItemStack with custom item class.

    private Player player;
    private ItemStack item;

    private static HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false;

    public CustomRecipeCraftEvent(Player player, ItemStack item) {
        this.player = player;
        this.item = item;
    }

    @Override
    public HandlerList getHandlers() {return HANDLERS;}
    public static HandlerList getHandlerList() {return HANDLERS;}

    public Player getPlayer() {return player;}
    public ItemStack getCustomItem() {return item;}

    public boolean isCancelled() {return cancelled;}
    public void setCancelled(boolean cancel) {cancelled = cancel;}

}

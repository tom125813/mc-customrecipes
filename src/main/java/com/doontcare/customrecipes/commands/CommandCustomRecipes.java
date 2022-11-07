package com.doontcare.customrecipes.commands;

import com.doontcare.customrecipes.CustomRecipes;
import com.doontcare.customrecipes.manager.ItemHandler;
import com.doontcare.customrecipes.utils.UtilChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CommandCustomRecipes implements CommandExecutor, Listener {

    private String inventoryTitle = UtilChat.translate("&8Custom Item Recipes");
    private ItemStack backItem;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("customrecipes")) {
            CustomRecipes customRecipes = CustomRecipes.getInstance();

            if (!(sender instanceof Player)) {
                displayItemList(sender);
                return false;
            }

            Player player = (Player) sender;

            if (args.length == 0) {
                 openGui(player);
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    displayItemList((CommandSender) player);
                } else {
                    player.sendMessage(UtilChat.translate("&6[CustomRecipes] &eUsage: /customrecipes [list]"));
                    if (player.hasPermission("customrecipes.admin")) {
                        player.sendMessage(UtilChat.translate("&4[CustomRecipes] &cStaff Usage: /customrecipes give <player> <item>"));
                    }
                }
            } else if (args.length == 3) {
                if (!player.hasPermission("customrecipes.admin")) {
                    player.sendMessage(UtilChat.translate("&6[CustomRecipes] &eUsage: /customrecipes [list]"));
                    return false;
                }
                if (args[0].equalsIgnoreCase("give")) {
                    Player targetPlayer;
                    ItemStack targetItem;

                    String playerName = args[1];
                    String itemId = args[2];

                    try {
                        targetPlayer = Bukkit.getPlayer(playerName);
                    } catch (Exception ex) {
                        player.sendMessage(UtilChat.translate("&4[CustomRecipes] &cPlayer could not be found"));
                        return false;
                    }

                    try {
                        targetItem = ItemHandler.getCustomItem(itemId);
                    } catch (Exception ex) {
                        player.sendMessage(UtilChat.translate("&4[CustomRecipes] &cCustom item could not be found"));
                        return false;
                    }

                    targetPlayer.getInventory().addItem(targetItem);
                    targetPlayer.sendMessage(UtilChat.translate("&2[CustomRecipes] &aYou have received 1x " + targetItem.getItemMeta().getDisplayName()));
                    player.sendMessage(UtilChat.translate("&4[CustomRecipes] &cYou have sent 1x " + targetItem.getItemMeta().getDisplayName() + "&c to " + targetPlayer.getName()));
                } else {
                    player.sendMessage(UtilChat.translate("&6[CustomRecipes] &eUsage: /customrecipes [list]"));
                    player.sendMessage(UtilChat.translate("&4[CustomRecipes] &cStaff Usage: /customrecipes give <player> <item>"));
                }

            } else {
                player.sendMessage(UtilChat.translate("&6[CustomRecipes] &eUsage: /customrecipes [list]"));
                if (player.hasPermission("customrecipes.admin")) {
                    player.sendMessage(UtilChat.translate("&4[CustomRecipes] &cStaff Usage: /customrecipes give <player> <item>"));
                }
            }
        }
        return false;
    }

    private void displayItemList(CommandSender sender) {
        List<String> recipesList = new ArrayList<>();
        CustomRecipes.getInstance().getRecipeManager().getRecipes().forEach(recipe -> {recipesList.add(recipe.getResult().getItemMeta().getDisplayName());});
        if (recipesList.size()!=0) {
            String recipeNamesMessage = "";
            for (String name : recipesList)
                recipeNamesMessage += name + ", ";
            recipeNamesMessage = ChatColor.stripColor(recipeNamesMessage);
            recipeNamesMessage = recipeNamesMessage.substring(0, recipeNamesMessage.length() - 2);
            sender.sendMessage(UtilChat.translate("&2[CustomRecipes] &aAvailable recipes: &7" + recipeNamesMessage));
        } else {
            sender.sendMessage(UtilChat.translate("&2[CustomRecipes] &aNo available recipes"));
        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(inventoryTitle)) {
            e.setCancelled(true);

            Recipe recipe = null;
            //for (ItemStack item : e.getInventory().getContents()) {
            ItemStack item = e.getCurrentItem();
                if (CustomRecipes.getInstance().getRecipeManager().contains(item)) {
                    recipe = CustomRecipes.getInstance().getRecipeManager().getRecipe(item);
                    //break;
                }
            //}

            if (recipe == null)
                return;

            ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
            Map<Character, ItemStack> ingredientMap = shapedRecipe.getIngredientMap();
            Inventory inv = Bukkit.createInventory(null,45, UtilChat.translate("&8Crafting: " + recipe.getResult().getItemMeta().getDisplayName()));

            // FILLER ITEM
            ItemStack fillerItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta fillerMeta = fillerItem.getItemMeta();
            fillerMeta.setDisplayName(" ");
            fillerMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
            fillerItem.setItemMeta(fillerMeta);

            for (int i = 0; i < inv.getSize(); i++) {
                if (i != 10 && i != 11 && i != 12 && i!=19 && i!=20 && i!=21 && i!= 23 && i!= 25 && i!=28 && i!=29 && i!=30)
                    inv.setItem(i, fillerItem);
            }

            inv.setItem(23, recipe.getResult());

            // CRAFTING TABLE ITEM
            ItemStack tableItem = new ItemStack(Material.CRAFTING_TABLE);
            ItemMeta tableMeta = tableItem.getItemMeta();
            tableMeta.setDisplayName(UtilChat.translate("&6How can I obtain this item?"));
            List<String> tlore = new ArrayList<>();
            tlore.add(" ");
            tlore.add(UtilChat.translate("&6 → &eUse a crafting table and"));
            tlore.add(UtilChat.translate("&e   and use the items displayed   "));
            tlore.add(UtilChat.translate("&e   on the left to craft."));
            tlore.add("  ");
            tableMeta.setLore(tlore);
            tableItem.setItemMeta(tableMeta);

            inv.setItem(25, tableItem);

            int slotVal = 0;
            int[] availableSlots = new int[]{10,11,12,19,20,21,28,29,30};
            int currentSlot;
            for (String chars : shapedRecipe.getShape()) {
                for (int i = 0; i < 3; i++) {
                    currentSlot = availableSlots[slotVal];
                    Character charz = chars.charAt(i);
                    if (charz != " ".charAt(0)) {
                        ItemStack ingredient = ingredientMap.get(charz);
                        inv.setItem(currentSlot, ingredient);
                        slotVal++;
                    } else {
                        slotVal++;
                    }
                }
            }

            /*ItemStack whiteFiller = fillerItem;
            whiteFiller.setType(Material.WHITE_STAINED_GLASS_PANE);
            ItemMeta whiteMeta = whiteFiller.getItemMeta();
            whiteMeta.setDisplayName(UtilChat.translate("&cNo items needed here!"));
            whiteFiller.setItemMeta(whiteMeta);

            for (int i = 0; i < inv.getSize(); i++) {
                if (inv.getItem(i) == null)
                    inv.setItem(i,whiteFiller);
            }*/

            backItem = new ItemStack(Material.BARRIER);
            ItemMeta backMeta = backItem.getItemMeta();
            backMeta.setDisplayName(" ");
            List<String> lore = new ArrayList<>();
            lore.add(UtilChat.translate("   &4Go back!"));
            lore.add(" ");
            lore.add(UtilChat.translate("   &8 → &cClick this button to go"));
            lore.add(UtilChat.translate("   &c    to the previous page.     "));
            lore.add(" ");
            backMeta.setLore(lore);
            backItem.setItemMeta(backMeta);


            inv.setItem(44, backItem);

            e.getWhoClicked().openInventory(inv);
        } else if (e.getView().getTitle().startsWith(UtilChat.translate("&8Crafting: "))) {
            e.setCancelled(true);

            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().equals(backItem))
                    openGui((Player) e.getWhoClicked());
            }
        }
    }

    private void openGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 36, inventoryTitle);

        // FILLER ITEM

        ItemStack fillerItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = fillerItem.getItemMeta();
        fillerMeta.setDisplayName(" ");
        fillerMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        fillerItem.setItemMeta(fillerMeta);

        for (int i = 0; i < inv.getSize(); i++) {
            if (i < 10 || i == 17 || i == 18 || i > 25) {
                if (inv.getItem(i) == null)
                    inv.setItem(i, fillerItem);
            }
        }

        ItemStack noteItem = new ItemStack(Material.PAPER);
        ItemMeta noteMeta = noteItem.getItemMeta();
        noteMeta.setDisplayName(UtilChat.translate("&8Custom Items"));
        List<String> lore = new ArrayList<>();
        lore.add(UtilChat.translate("&8 → &7" + CustomRecipes.getInstance().getRecipeManager().getCount() + " available"));
        noteMeta.setLore(lore);
        noteItem.setItemMeta(noteMeta);
        inv.setItem(4, noteItem);

        // ADD RECIPES

        for (Recipe recipe : CustomRecipes.getInstance().getRecipeManager().getRecipes()) {
            try {
                inv.addItem(recipe.getResult());
            } catch (Exception ex) {
                // add multiple pages
            }
        }

        player.openInventory(inv);
    }

}

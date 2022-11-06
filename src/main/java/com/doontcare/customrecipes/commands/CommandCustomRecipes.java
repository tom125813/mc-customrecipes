package com.doontcare.customrecipes.commands;

import com.doontcare.customrecipes.CustomRecipes;
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

            if (!(sender instanceof Player) || args.length!=0) {
                List<String> recipesList = new ArrayList<>();
                customRecipes.getRecipeManager().getRecipes().forEach(recipe -> {recipesList.add(recipe.getResult().getItemMeta().getDisplayName());});
                if (recipesList.size()!=0) {
                    String recipeNamesMessage = "";
                    for (String name : recipesList)
                        recipeNamesMessage += name + ", ";
                    recipeNamesMessage = ChatColor.stripColor(recipeNamesMessage);
                    recipeNamesMessage = recipeNamesMessage.substring(0, recipeNamesMessage.length() - 2);
                    sender.sendMessage(UtilChat.translate("&2[CustomRecipes] &aAvailable recipes: &7" + recipeNamesMessage));
                    return false;
                }
                sender.sendMessage(UtilChat.translate("&2[CustomRecipes] &aNo available recipes"));
            }

            Player player = (Player) sender;

            if (args.length == 0) {
                 openGui(player);
            }
        }
        return false;
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

            // 10, 19, 28: + 2
            for (int i = 0; i < inv.getSize(); i++) {
                if (i != 10 && i != 11 && i != 12 && i!=19 && i!=20 && i!=21 && i!= 23 && i!=28 && i!=29 && i!=30)
                    inv.setItem(i, fillerItem);
            }

            inv.setItem(23, recipe.getResult());

            for (String chars : shapedRecipe.getShape()) {
                Character charz = chars.charAt(0);
                ItemStack ingredient = ingredientMap.get(charz);
                /*ItemMeta ingredientMeta = ingredient.getItemMeta();
                ingredientMeta.setDisplayName(ChatColor.GRAY + ingredient.getItemMeta().getLocalizedName());
                ingredientMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.BLACK + String.valueOf(new Random().nextFloat()));
                ingredientMeta.setLore(lore);
                ingredient.setItemMeta(ingredientMeta);*/
                inv.addItem(ingredient);
            }

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

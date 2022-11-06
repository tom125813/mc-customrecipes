package com.doontcare.customrecipes.files;

import com.doontcare.customrecipes.CustomRecipes;
import com.doontcare.customrecipes.manager.RecipeManager;
import com.doontcare.customrecipes.utils.UtilChat;
import com.doontcare.customrecipes.utils.UtilEnchantments;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeFileHandler {

    private CustomRecipes customRecipes;

    public void init() {
        customRecipes = CustomRecipes.getInstance();

        if (!customRecipes.getDataFolder().exists())
            customRecipes.getDataFolder().mkdir();

        File settingsFile = new File(customRecipes.getDataFolder(), "settings.yml");
        YamlConfiguration settingsConfig = YamlConfiguration.loadConfiguration(settingsFile);
        if (!settingsFile.exists()) {
            try {
                settingsFile.createNewFile();
                settingsConfig.set("update-checker", true);
                settingsConfig.save(settingsFile);
            } catch (IOException e) {e.printStackTrace();}
        }

        File messagesFile = new File(customRecipes.getDataFolder(), "messages.yml");
        YamlConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        if (!messagesFile.exists()) {
            try {
                messagesFile.createNewFile();
                messagesConfig.set("test", "lmfao");
                messagesConfig.save(messagesFile);
            } catch (IOException e) {e.printStackTrace();}
        }

        File file = new File(customRecipes.getDataFolder(), "recipes.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            try {
                file.createNewFile();
                config.set("recipes.crimsonblade.displayname", "&4Crimson Blade");
                List<String> lore = new ArrayList<>();
                lore.add("&cThis sword comes from the");
                lore.add("&cdepths of the nether forests..");
                lore.add("");
                lore.add("&4&lRARE");
                config.set("recipes.crimsonblade.lore", lore);
                List<String> recipe = new ArrayList<>();
                recipe.add("#");
                recipe.add("#");
                recipe.add("/");
                config.set("recipes.crimsonblade.material", "netherite_sword");
                config.set("recipes.crimsonblade.recipe.format", recipe);
                config.set("recipes.crimsonblade.recipe.materials.#", "bedrock");
                config.set("recipes.crimsonblade.recipe.materials./", "stick");
                config.set("recipes.crimsonblade.flags.hide_attributes", true);
                config.save(file);
            } catch (IOException e) {e.printStackTrace();}
        }

        registerCustomRecipes();
    }

    private void registerCustomRecipes() {
        File file = new File(customRecipes.getDataFolder(), "recipes.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        int availableCount = 0;
        int totalCount = 0;
        if (config.getConfigurationSection("recipes") != null) {
            for (String identifier : config.getConfigurationSection("recipes").getKeys(false)) {
                totalCount++;
                try {
                    ItemStack customItemStack = new ItemStack(Material.valueOf(config.getString("recipes." + identifier + ".material").toUpperCase().replaceAll(" ", "_")));
                    ItemMeta customItemMeta = customItemStack.getItemMeta();
                    customItemMeta.setDisplayName(UtilChat.translate(config.getString("recipes." + identifier + ".displayname")));
                    List<String> customItemLore = new ArrayList<>();
                    config.getStringList("recipes." + identifier + ".lore").forEach(l -> {
                        customItemLore.add(UtilChat.translate(l));
                    });
                    customItemMeta.setLore(customItemLore);

                    if (config.getBoolean("recipes." + identifier + ".flags.hide_attributes"))
                        customItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

                    // ENCHANTS

                    if (config.getConfigurationSection("recipes." + identifier + ".enchantments") != null) {
                        for (String enchantIdentifier : config.getConfigurationSection("recipes." + identifier + ".enchantments").getKeys(false)) {
                            customItemMeta.addEnchant(UtilEnchantments.getEnchantment(enchantIdentifier), config.getInt("recipes." + identifier + ".enchantments." + enchantIdentifier), true);
                        }
                    }


                    customItemStack.setItemMeta(customItemMeta);

                    ShapedRecipe recipe = new ShapedRecipe(
                            new NamespacedKey(customRecipes, identifier),
                            customItemStack
                    );
                    List<String> recipeData = config.getStringList("recipes." + identifier + ".recipe.format");
                    recipe.shape(
                            recipeData.get(0),
                            recipeData.get(1),
                            recipeData.get(2)
                    );

                    for (String recipeMaterials : config.getConfigurationSection("recipes." + identifier + ".recipe.materials").getKeys(false)) {
                        Character character = recipeMaterials.charAt(0);
                        Material material = Material.valueOf(config.getString("recipes." + identifier + ".recipe.materials." + character).toUpperCase().replaceAll(" ", "_"));
                        recipe.setIngredient(character, material);
                    }

                    customRecipes.getRecipeManager().addRecipe(recipe);
                    availableCount++;
                } catch (Exception ex) {
                }
            }
        }
        customRecipes.getLogger().info(String.valueOf(availableCount) + "/" + totalCount + " recipe(s) have been loaded.");
    }


}

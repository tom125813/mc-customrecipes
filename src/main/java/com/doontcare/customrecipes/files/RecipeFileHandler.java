package com.doontcare.customrecipes.files;

import com.doontcare.customrecipes.CustomRecipes;
import com.doontcare.customrecipes.manager.ItemHandler;
import com.doontcare.customrecipes.manager.RecipeManager;
import com.doontcare.customrecipes.utils.UtilChat;
import com.doontcare.customrecipes.utils.UtilEnchantments;
import com.doontcare.customrecipes.utils.UtilFlags;
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
                config.set("recipes.crimsonblade.enchantments.sharpness", "10");
                config.set("recipes.crimsonblade.enchantments.fire aspect", "2");
                config.set("recipes.crimsonblade.enchantments.unbreaking", "9");
                config.set("recipes.crimsonblade.recipe.format", recipe);
                config.set("recipes.crimsonblade.recipe.materials.#", "bedrock");
                config.set("recipes.crimsonblade.recipe.materials./", "stick");
                config.set("recipes.crimsonblade.flags.hide attributes", true);
                config.set("recipes.crimsonblade.flags.hide enchantments", false);
                config.set("recipes.crimsonblade.flags.unbreakable", false);

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
                    ItemStack customItemStack = ItemHandler.getCustomItem(identifier);

                    // RECIPE
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
                    ex.printStackTrace();
                }
            }
        }
        customRecipes.getLogger().info(String.valueOf(availableCount) + "/" + totalCount + " recipe(s) have been loaded.");
    }


}

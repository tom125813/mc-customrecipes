package com.doontcare.customrecipes;

import com.doontcare.customrecipes.commands.CommandCustomRecipes;
import com.doontcare.customrecipes.files.RecipeFileHandler;
import com.doontcare.customrecipes.listeners.CraftingTableListener;
import com.doontcare.customrecipes.listeners.CustomRecipeListener;
import com.doontcare.customrecipes.listeners.OperatorJoinListener;
import com.doontcare.customrecipes.manager.RecipeManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomRecipes extends JavaPlugin {

    // TODO: Add an update checker which alerts staff and console on join.
    // TODO: E.g turn '#': red dye into red_dye

    private static CustomRecipes instance;
    private RecipeFileHandler fileHandler;
    private RecipeManager recipeManager;

    private boolean outdated = true;

    @Override
    public void onEnable() {
        // check for spigot update

        instance = this;

        recipeManager = new RecipeManager();

        fileHandler = new RecipeFileHandler();
        fileHandler.init();

        CommandCustomRecipes commandCustomRecipes = new CommandCustomRecipes();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new OperatorJoinListener(), this);
        pm.registerEvents(new CraftingTableListener(), this);
        pm.registerEvents(new CustomRecipeListener(), this);
        pm.registerEvents(commandCustomRecipes, this);

        getCommand("customrecipes").setExecutor(commandCustomRecipes);
    }

    @Override
    public void onDisable() {

    }

    private void registerRecipes() {

    }

    public static CustomRecipes getInstance() {return instance;}
    public RecipeManager getRecipeManager() {return recipeManager;}
    public boolean isOutdated() {return outdated;}
}

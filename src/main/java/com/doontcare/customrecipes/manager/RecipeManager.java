package com.doontcare.customrecipes.manager;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeManager {

    private List<Recipe> recipes = new ArrayList<>();

    public List<Recipe> getRecipes() {return recipes;}
    public Recipe getRecipe(int index) {return recipes.get(index);}

    public Recipe getRecipe(ItemStack item) {
        for (Recipe recipez : recipes) {
            // can return false trues
            if (recipez.getResult().equals(item))
                return recipez;
        }
        return null;
    }

    public int getCount() {return recipes.size();}

    public boolean contains(Recipe recipe) {
        for (Recipe recipez : recipes) {
            // can return false trues
            if (recipez.getResult().equals(recipe.getResult()))
                return true;
        }
        return false;
    }

    public boolean contains(ItemStack item) {
        for (Recipe recipez : recipes) {
            // can return false trues
            if (recipez.getResult().equals(item))
                return true;
        }
        return false;
    }

    public void addRecipe(Recipe recipe) {
        Bukkit.addRecipe(recipe);
        recipes.add(recipe);
    }

}

package com.devfox.recipes;

import com.devfox.recipes.persistence.RecipeListIO;
import com.devfox.recipes.persistence.RecipeListIOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Singleton Pattern.
 * The role of the RecipeManager is to act as the sole point of contact for the set of recipes to base calculations on.
 */
public class RecipeManager {
    private volatile static RecipeManager uniqueInstance;
    private List<Recipe> recipeList = new ArrayList<>();

    private RecipeManager(){}

    /**
     *
     * @return The sole instance of the RecipeManager class
     */
    public static RecipeManager getInstance() {
        if (uniqueInstance == null) {
            synchronized (RecipeManager.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new RecipeManager();
                }
            }
        }
        return uniqueInstance;
    }

    /**
     * This method is guaranteed to return the list of recipes as of the last operation performed on it through the {@linkplain RecipeManager}.
     * @return A clone of the internal recipe list
     */
    public Recipe[] getRecipeList() {
        return recipeList.toArray(new Recipe[0]).clone();
    }

    /**
     * Adds a new recipe to the recipe list of the manager
     * @param recipe The recipe to add
     */
    public final void addRecipe(Recipe recipe){
        recipeList.add(recipe);
    }

    /**
     * TODO override recipe hashcode() method to allow remove to function correctly
     * Removes a recipe from the recipe list of the manager
     * @param recipe the recipe to remove
     */
    public final void removeRecipe(Recipe recipe){
        recipeList.remove(recipe);
    }

    /**
     * Attempts to save the recipe list using the specified IO method.
     * @param recipeListIO The IO method to use
     * @param file The file to save to
     * @throws RecipeListIOException if there is an error saving the recipe list to the file
     */
    public void saveRecipeList(RecipeListIO recipeListIO, File file) throws RecipeListIOException {
        recipeListIO.saveList(getRecipeList(),file);
    }

    /**
     * Attempts to load recipes from a form of IO into memory.
     * @param recipeListIO the IO method to use
     * @param file The file to use
     * @throws RecipeListIOException if there is an error loading the recipe list from the file
     */
    public void loadRecipeList(RecipeListIO recipeListIO, File file) throws RecipeListIOException {
        recipeList = Arrays.asList(recipeListIO.loadList(file));
    }

}

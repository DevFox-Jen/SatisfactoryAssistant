package com.devfox.recipes;

import com.devfox.recipes.persistence.RecipeListIO;
import com.devfox.recipes.persistence.RecipeListIOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Singleton Pattern.
 * The role of the RecipeManager is to act as the sole point of contact for the set of recipes to base calculations on.
 */
public final class RecipeManager {
    private volatile static RecipeManager uniqueInstance;
    private List<Recipe> recipeList = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(RecipeManager.class);

    private RecipeManager(){}

    /**
     *
     * @return The sole instance of the RecipeManager class
     */
    public static RecipeManager getInstance() {
        if (uniqueInstance == null) {
            synchronized (RecipeManager.class) {
                if (uniqueInstance == null) {
                    logger.trace("Created new RecipeManager");
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
     * Adds a new recipe to the recipe list of the manager. If the recipe already exists then a duplicate will not be added
     * @param recipe The recipe to add
     */
    public void addRecipe(Recipe recipe){
        if(!recipeList.contains(recipe)){
            recipeList.add(recipe);
        }
    }

    /**
     * Removes a recipe from the recipe list of the manager
     * @param recipe the recipe to remove
     */
    public void removeRecipe(Recipe recipe){
        recipeList.remove(recipe);
    }

    /**
     * Clears the internal recipe list maintained by the RecipeManager
     */
    public void clearRecipeList(){
        recipeList.clear();
    }

    /**
     * Destroys the RecipeManager resulting in {@link #getInstance()} returning a new instance of RecipeManager
     */
    public void destroyInstance(){
        uniqueInstance = null; //De-refence will put the old intance up for GC
        logger.trace("Destroyed RecipeManager");
    }

    /**
     * Attempts to save the recipe list using the specified IO method.
     * @param recipeListIO The IO method to use
     * @param outputStream The outputStream to save to
     * @throws RecipeListIOException if there is an error saving the recipe list to the outputStream
     */
    public void saveRecipeList(RecipeListIO recipeListIO, OutputStream outputStream) throws RecipeListIOException {
        recipeListIO.saveList(getRecipeList(),outputStream);
    }

    /**
     * Attempts to load recipes from a form of IO into memory.
     * @param recipeListIO the IO method to use
     * @param inputStream The inputStream to use
     * @throws RecipeListIOException if there is an error loading the recipe list from the inputStream
     */
    public void loadRecipeList(RecipeListIO recipeListIO, InputStream inputStream) throws RecipeListIOException {
        logger.trace("Start recipe list load");
        recipeList = Arrays.asList(recipeListIO.loadList(inputStream));
    }

}

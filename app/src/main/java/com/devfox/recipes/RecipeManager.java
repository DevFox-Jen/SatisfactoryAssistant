package com.devfox.recipes;

import java.io.File;

/**
 * Singleton Pattern.
 * The role of the RecipeManager is to act as the sole point of contact for the set of recipes to base calculations on.
 */
public class RecipeManager {
    private volatile static RecipeManager uniqueInstance;
    private Recipe[] recipeList;
    private File lastLoadedFile;

    private RecipeManager(){}

    /**
     *
     * @return The sole instance of the RecipeManager class
     */
    public static RecipeManager getInstance(){
        if(uniqueInstance == null){
            synchronized (RecipeManager.class){
                if(uniqueInstance == null){
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
    public Recipe[] getRecipeList(){
        throw new RuntimeException("Method not implemented");
    }

    /**
     * Attempts to parse an XML file containing recipes and load them into memory
     * @param file The xml file to parse
     */
    public void loadRecipesFromXMLFile(File file){
        throw new RuntimeException("Method not implemented");
    }

    /**
     * Calls {@link #loadRecipesFromXMLFile} using the last file that was successfully loaded
     */
    public void reloadRecipesFromXMLFile(){
        loadRecipesFromXMLFile(lastLoadedFile);
    }

    /**
     * Adds a new recipe to the recipe list of the manager
     * @param recipe The recipe to add
     */
    public void addRecipe(Recipe recipe){
        throw new RuntimeException("Method not implemented");
    }

    public void saveRecipeListToFile(File file){
        throw new RuntimeException("Method not implemented");
    }

    public void saveRecipeListToLastFile(){
        saveRecipeListToFile(lastLoadedFile);
    }
}

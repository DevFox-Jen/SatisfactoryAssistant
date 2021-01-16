package com.devfox.recipes.persistence;

import com.devfox.recipes.Recipe;

import java.io.File;

public interface RecipeListIO {
    public void saveList(Recipe[] recipeList, File location) throws RecipeListIOException;
    public Recipe[] loadList(File location) throws RecipeListIOException;
}

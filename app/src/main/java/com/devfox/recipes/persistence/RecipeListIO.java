package com.devfox.recipes.persistence;

import com.devfox.recipes.Recipe;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public interface RecipeListIO {
    public void saveList(Recipe[] recipeList, OutputStream outputStream) throws RecipeListIOException;
    public Recipe[] loadList(InputStream inputStream) throws RecipeListIOException;
}

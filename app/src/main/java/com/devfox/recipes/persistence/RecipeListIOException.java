package com.devfox.recipes.persistence;

/**
 * Thrown if there is an error when trying to perform IO on a  a recipe list
 */
public class RecipeListIOException extends Exception{
    public RecipeListIOException(String message) {
        super(message);
    }
}

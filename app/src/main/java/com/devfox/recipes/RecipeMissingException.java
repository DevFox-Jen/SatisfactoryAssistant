package com.devfox.recipes;

public class RecipeMissingException extends RuntimeException{
    public RecipeMissingException(String itemMissingRecipe){
        super("Recipe missing for: " + itemMissingRecipe);
    }
}

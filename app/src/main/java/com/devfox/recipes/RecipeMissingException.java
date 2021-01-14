package com.devfox.recipes;

import com.devfox.items.Items;

public class RecipeMissingException extends RuntimeException{
    public RecipeMissingException(Items itemMissingRecipe){
        super("Recipe missing for: " + itemMissingRecipe);
    }

    public RecipeMissingException(String message){
        super(message);
    }
}

package com.devfox.production.pipeline;

public class RecipeMissingException extends RuntimeException{
    /**
     * Used if a single item is missing a recipe
     * @param itemMissingRecipe the name of the item missing a recipe
     */
    public RecipeMissingException(String itemMissingRecipe){
        super("Recipe missing for item: " + itemMissingRecipe);
    }

    /**
     * Used if several items are missing recipes
     * @param itemsMissingRecipes an array of the names of the items missing recipes
     */
    public RecipeMissingException(String[] itemsMissingRecipes){
        super(buildOutputMessage(itemsMissingRecipes));
    }

    private static String buildOutputMessage(String[] itemsMissingRecipes){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The following items are missing recipes:\n");
        for(String item : itemsMissingRecipes){
            stringBuilder.append(">");
            stringBuilder.append(item);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}

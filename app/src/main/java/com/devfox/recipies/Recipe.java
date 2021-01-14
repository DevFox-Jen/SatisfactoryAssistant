package com.devfox.recipies;

import com.devfox.items.Items;

public class Recipe {
    private Recipe[] inputRecipes;
    private String name;
    private Items outputItem;
    /**
     * Constructor for creating a recipe that takes a set of input recipes and
     * @param name
     * @param inputRecipes
     * @param outputItem
     */
    public Recipe(String name, Recipe[] inputRecipes, Items outputItem){
        this.inputRecipes = inputRecipes;
        this.name = name;
        this.outputItem = outputItem;
    }

    public String getName(){
        return name;
    }

    public Items getOutputItem(){
        return outputItem;
    }

    public Recipe[] getInputRecipes(){
        return inputRecipes;
    }
}

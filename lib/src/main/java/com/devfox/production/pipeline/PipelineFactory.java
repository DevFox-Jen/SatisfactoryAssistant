package com.devfox.production.pipeline;

import com.devfox.items.ItemStack;
import com.devfox.recipes.Recipe;

import java.util.*;

/**
 * The PipelineFactory aims to create a tree in the form of chained {@link PipelineNode}.
 */
public abstract class PipelineFactory {
    private String outputItem;

    public static final float NO_LIMIT = -1;
    private float outputTargetPerMinute = NO_LIMIT;

    /**
     *
     * @param targetItem the item that the factory should produce
     */
    public PipelineFactory(String targetItem){
        setOutputItem(targetItem);
    }

    public void setOutputItem(String itemName){
        outputItem = itemName;
    }

    public String getOutputItem(){
        return outputItem;
    }

    /**
     * Attempts to construct a pipeline to produce the set target item. if {@link #setTargetOutputPerMinute} has not been set, then the pipeline will aim to produce as much of the set target item as possible.
     * @param recipes The set of recipes to use for creating the pipeline. If there are duplicate recipes then the builder will use the first recipe it finds to produce an item.
     * @return The root pipeline node for the resulting pipeline
     * @throws RecipeMissingException if one or more required recipes are missing
     */
    public PipelineNode buildPipeline(Recipe[] recipes) throws RecipeMissingException{
        checkAllRequiredRecipesExist(recipes,getOutputItem());
        return onBuildPipeline(recipes);
    }

    /**
     * Performs a search of the recipe list using the target item as the starting point to check that every recipe required to create the output item exists in the list
     * It will not stop when one missing recipe is found. It will report the recipes missing from each branch of the pipeline in the form of the thrown exception
     * It cannot report recipes that are missing where there is no transitive relationship.
     * E.g. if we take the iron plate recipe: Miner -> Iron Ore -> Iron Ingot -> Iron Plate, and remove Iron Ore -> Iron Ingot
     * The function cannot infer that Iron Ore -> Iron Ingot is missing, it can only state that there is no recipe present to craft Iron Ingots to enable the recipe Iron Ingot -> Iron Plate
     * @param recipes The set of recipes to check
     * @param targetItem The item to check the recipes are present for
     * @throws RecipeMissingException if one or more recipes are missing from the list.
     */
    public static void checkAllRequiredRecipesExist(Recipe[] recipes, String targetItem) throws RecipeMissingException{
        Queue<String> itemLookupQueue = new LinkedList<>();
        HashMap<String, Recipe> recipeHashMap = new HashMap<>();
        List<String> itemsMissingRecipes = new ArrayList<>();

        for(Recipe recipe : recipes){
            //Make an entry using the recipe output item id as the key, and the recipe as the value
            recipeHashMap.put(recipe.getOutputItemStack().getItemID(),recipe);
        }

        //Add the target item as the first item in the queue
        itemLookupQueue.add(targetItem);

        while(!itemLookupQueue.isEmpty()){
            String nextItem = itemLookupQueue.poll();
            Recipe recipeForItem = recipeHashMap.get(nextItem);
            if(recipeForItem == null){
                itemsMissingRecipes.add(nextItem);
            }else{
                itemLookupQueue.addAll(Arrays.asList(getNamesOfItems(recipeForItem.getInputItemStacks())));
            }
        }
        if(itemsMissingRecipes.size() > 0){
            throw new RecipeMissingException(itemsMissingRecipes.toArray(new String[0]));
        }
    }

    private static String[] getNamesOfItems(ItemStack[] itemStacks){
        ArrayList<String> arrayList = new ArrayList<>();
        for(ItemStack itemStack : itemStacks)
            arrayList.add(itemStack.getItemID());
        return arrayList.toArray(new String[0]);
    }

    /**
     * To be implemented by the extending class. Called when {@link #buildPipeline} is called.
     * It will only be called if the recipe set has been checked. This means this method will only be called if {@link #checkAllRequiredRecipesExist} returns true on the set of recipes
     * @param recipes The set of recipes to use for creating the pipeline. If there are duplicate recipes then the builder will use the first recipe it finds to produce an item.
     * @return The root pipeline node for the resulting pipeline
     */
    public abstract PipelineNode onBuildPipeline(Recipe[] recipes);

    /**
     * Sets the amount of the output item to produce per minute
     * @param amountPerMinute amount to produce of the item set in {@link #setOutputItem}
     */
    public void setTargetOutputPerMinute(float amountPerMinute){
        outputTargetPerMinute = amountPerMinute;
    }
}

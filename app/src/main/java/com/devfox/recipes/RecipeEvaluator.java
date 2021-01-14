package com.devfox.recipes;

import com.devfox.items.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Utility class that performs calculations and evaluates the result of a set of recipes.
 */
public final class RecipeEvaluator {
    private static final Logger logger = LogManager.getLogger(RecipeEvaluator.class);
    private RecipeEvaluator(){

    }

    public static void reportAmountOfEachComponentPerMin(Recipe[] recipeSet, String item,float bQuotaPerMin){
    logger.info(item + "|" + bQuotaPerMin + "/min REQUIRES:");
        String[] components = getComponentsOfItem(recipeSet,item);
        logger.info(components.length);
        for(String component : components){
            logger.info(component + "|" + calcAmountRequiredPerMin(recipeSet,item,bQuotaPerMin,component) + "/min");
        }
    }

    public static float getAmountOfItemProducedPerMin(Recipe[] recipeSet, String item){
        Recipe recipe = findFirstRecipeForItem(recipeSet,item);
        return recipe.getOutputProducedPerMin();
    }

    /**
     * Given an item, returns a list of the items that are used to make it
     * @param recipeSet
     * @param item
     * @return
     */
    public static String[] getComponentsOfItem(Recipe[] recipeSet,String item){
        Recipe originalItemRecipe = findFirstRecipeForItem(recipeSet,item);

        Queue<ItemStack> itemStackQueue = new LinkedList<>(Arrays.asList(originalItemRecipe.getInputItemStacks()));
        Set<String> componentList = new HashSet<>();

        while(!itemStackQueue.isEmpty()){
            String nextItem = itemStackQueue.remove().getItemID();
            componentList.add(nextItem);
            Recipe itemRecipe = findFirstRecipeForItem(recipeSet,nextItem);
            itemStackQueue.addAll(Arrays.asList(itemRecipe.getInputItemStacks()));
        }

        return componentList.toArray(new String[0]);
    }

    /**
     * Determines how much of item A is needed to produce a certain amount of Item B per minute.
     * Uses the first recipes it finds for each item.
     * Performs a breadth first recursive search starting from the recipe of the item
     * @param recipeSet The set of recipes to use for calculating the values
     * @param itemB The item you're trying to produce
     * @param bQuotaPerMin The amount of B to produce per minute
     * @param itemA The input item you want to know how much you need of
     * @return returns the total amount of itemA needed to make the quota amount of itemB or 0 if the item is not needed
     */
    public static float calcAmountRequiredPerMin(Recipe[] recipeSet, String itemB,float bQuotaPerMin,String itemA){
        List<Recipe> recipeList = Arrays.asList(recipeSet);

        //Find the recipe for the target item
        Recipe itemBRecipe = findFirstRecipeForItem(recipeSet,itemB);

        //Calculate how many times this recipe need to be used in a min to create the quota amount
        float recipeMultiplier = bQuotaPerMin/itemBRecipe.getOutputProducedPerMin(); //How many lots of the recipe need to be completed per min to match quota
        logger.trace("Recipe Multiplier for " + itemB + ": " + recipeMultiplier);

        float itemACountInThisRecipe = 0;

        //For each input item stack to the recipe, call this method but multiply the required input amount per minute by the recipe multiplier.
        //If the recipe is a base recipe then this for loop won't be used
        for(ItemStack inputItemStack : itemBRecipe.getInputItemStacksPerMinute()){
            float amountOfInputItemPerMinRequiredToMeetQuota = inputItemStack.getCount() * recipeMultiplier;
            itemACountInThisRecipe += calcAmountRequiredPerMin(recipeSet,inputItemStack.getItemID(),amountOfInputItemPerMinRequiredToMeetQuota, itemA);
        }

        //Calculate the amount of item A that this recipe needs, if any
        for(ItemStack inputItemStack : itemBRecipe.getInputItemStacksPerMinute()){
            if(inputItemStack.getItemID().equals(itemA)){
                logger.trace(itemA + " is in recipe for " + itemB);
                itemACountInThisRecipe += inputItemStack.getCount() * recipeMultiplier;
            }
        }

        return itemACountInThisRecipe;
    }

    /**
     * Searches a set of recipes for a recipe that produces a stack of the item. Returns the first matching recipe found
     * @param recipeSet
     * @param item
     * @return
     */
    public static Recipe findFirstRecipeForItem(Recipe[] recipeSet, String item){
        for(Recipe r : recipeSet){
            if(r.getOutputItemStack().getItemID().equals(item)){
                return r;
            }
        }
        throw new RecipeMissingException(item);
    }
}

package com.devfox.recipes;

import com.devfox.items.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class Recipe {
    private ItemStack[] inputItemStacks;
    private String name;
    private ItemStack outputItemStack;
    private static final float SECS_IN_MIN = 60.0f;
    private float timeTakenSecs;

    public Recipe(String name, ItemStack[] inputItemStacks, ItemStack outputItemStack, float timeTakenSecs){
        if(inputItemStacks == null)
            throw new IllegalArgumentException("Input Item Stacks cannot be null");
        if(outputItemStack == null)
            throw new IllegalArgumentException("Output Item Stack cannot be null");
        if(timeTakenSecs <= 0)
            throw new IllegalArgumentException("Time taken must be greater than 0 seconds");
        if(name == null)
            throw new IllegalArgumentException("Recipe must have a name");

        this.inputItemStacks = inputItemStacks;
        this.name = name;
        this.outputItemStack = outputItemStack;
        this.timeTakenSecs = timeTakenSecs;
    }

    public String getName(){
        return name;
    }

    public ItemStack getOutputItemStack(){
        return outputItemStack;
    }

    public ItemStack[] getInputItemStacks(){
        return inputItemStacks;
    }

    public ItemStack[] getInputItemStacksPerMinute(){
        ItemStack[] minItemStacks = new ItemStack[getInputItemStacks().length];
        for(int i = 0;i < getInputItemStacks().length;i++){
            minItemStacks[i] = new ItemStack(getInputItemStacks()[i].getItemID(),getInputItemStacks()[i].getCount() * getIterationsPerMin());
        }
        return minItemStacks;
    }

    /**
     *
     * @return How many times the recipe can occur per minute
     */
    public float getIterationsPerMin(){
        return SECS_IN_MIN/getTimeTakenSecs();
    }

    public float getTimeTakenSecs(){
        return timeTakenSecs;
    }

    /**
     * Divide the number of seconds in minute by the time taken for the recipe to get a ratio, then multiply the output item stack amount by the ratio
     * @return
     */
    public float getOutputProducedPerMin(){
        return getOutputItemStack().getCount() * getIterationsPerMin();
    }

    /**
     * The hash of the Recipe is denoted by a hash of the input item stacks, output item stacks and time taken.
     * The name is not taken into consideration and is just decorative.
     * @return
     */
    @Override
    public int hashCode(){
        return Objects.hash(inputItemStacks,outputItemStack,timeTakenSecs);
    }

    /**
     * Two Recipes are equal if they have identical input item stacks, output item stacks, and take the same amount of time.
     * The name is not taken into consideration and is considered decorative
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Recipe){
            Recipe otherRecipe = (Recipe) obj;
            if(Arrays.equals(inputItemStacks,otherRecipe.inputItemStacks)){
                if(outputItemStack.equals(otherRecipe.outputItemStack)){
                    if(timeTakenSecs == otherRecipe.timeTakenSecs){
                        return true;
                    }
                }
            }
        }
        return false;
    }

//    @Override
//    public String toString(){
//        StringBuilder builder = new StringBuilder();
//        builder.append(getOutputItemStack().getItemID());
//        builder.append("(").append(getOutputProducedPerMin()).append(")");
//        builder.append(" <- ");
//
//        ItemStack[] minItemStacks = getInputItemStacksPerMinute();
//
//        for(int i = 0;i < minItemStacks.length-1;i++){
//            builder.append(minItemStacks[i].getItemID()).append("(").append(minItemStacks[i].getCount()).append(")").append(" + ");
//        }
//
//        builder.append(minItemStacks[minItemStacks.length-1]).append("(").append(minItemStacks[minItemStacks.length-1].getCount()).append(")");
//        builder.append(" ").append("(/min");
//        return builder.toString();
//    }

}

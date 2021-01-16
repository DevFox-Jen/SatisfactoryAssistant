package com.devfox.production;

import com.devfox.SatisfactoryAssistant.MethodNotImplementedException;
import com.devfox.items.ItemStack;
import com.devfox.recipes.Recipe;

/**
 * A {@linkplain Machine} represents the production of a {@link com.devfox.recipes.Recipe} at a certain rate per minute. The production rate can be varied between 0 -> 250% where 100% represents a pattern's base production rate.
 * The maximum clockspeed is determined by the number of powershards in the machine
 */
public class Machine {
    private Recipe recipe;
    private int powerShards;
    private float currentClockSpeed;
    private static final float CLOCK_SPEED_PER_POWERSHARD = 0.5f; //The increase in max clock speed per powershard added
    private static final float BASE_CLOCK_SPEED = 1.0f; //The clock speed without any powershards
    private static final float SECS_IN_MIN = 60.0f;

    public Machine(Recipe recipe){
        this.recipe = recipe;
        powerShards = 0;
        currentClockSpeed = 1.0f;
    }

    /**
     * Get the amount of the input components to the machine that will be consumed per min, for the current recipe, for the current machine properties
     * @return
     */
    public ItemStack[] getInputPartsPerMin(){
        //Apply the scaled frequency per minute to the input item stacks
         ItemStack[] baseInputStacks = recipe.getInputItemStacks();
        ItemStack[] scaledInputStacks = new ItemStack[recipe.getInputItemStacks().length];

        for(int index = 0; index < baseInputStacks.length;index++){
            scaledInputStacks[index] = new ItemStack(baseInputStacks[index].getItemID(),baseInputStacks[index].getCount() * getRecipeFrequencyPerMin());
        }
        return scaledInputStacks;
    }

    /**
     * Calculates the frequency with which the current recipe occurs in a minute when taking into account the current machine clock speed
     * @return The frequency in iterations per minute
     */
    public float getRecipeFrequencyPerMin(){
        return SECS_IN_MIN / getProductionRate();
    }

    /**
     * Get the number of parts output per minute from the machine, for the current recipe, for the current machine properties
     * @return
     */
    public ItemStack getOutputPartsPerMin(){
        ItemStack baseOutputStack = recipe.getOutputItemStack();
        return new ItemStack(baseOutputStack.getItemID(),baseOutputStack.getCount() * getRecipeFrequencyPerMin());
    }

    /**
     * Attempts to set the output parts per minute of the machine to the given amount by modifying the clock speed accordingly
     * @throws IllegalConfigurationException if the output parts per minute is not achievable given the current maximum clock speed
     */
    public void setOutputPartsPerMinute(float outputPartsPerMinute) throws IllegalConfigurationException{
        throw new MethodNotImplementedException();
    }

    public void setClockSpeed(float clockSpeed){
        if(clockSpeed < 0)
            throw new IllegalArgumentException("Clockspeed provided is " + clockSpeed + " but it cannot be less than 0.");
        if(clockSpeed > getMaxClockSpeed())
            throw new IllegalArgumentException("Clockspeed provided is " + clockSpeed + " but it cannot exceed current maximum of " + getMaxClockSpeed());
        currentClockSpeed = clockSpeed;
    }

    public float getCurrentClockSpeed(){
        return currentClockSpeed;
    }

    public int getPowerShards(){
        return powerShards;
    }

    public void setPowerShard(int powerShards){
        this.powerShards = powerShards;
    }

    /**
     * Gets the current production rate of the machine for the current recipe and current machine properties.
     * The production rate denotes how long a single operation takes.
     * The production rate is affected by the current clockspeed
     * @return
     */
    public float getProductionRate(){
        if(getCurrentClockSpeed() == 0)
            return 0.0f;
        return recipe.getTimeTakenSecs() / getCurrentClockSpeed();
    }

    public float getMaxClockSpeed(){
        return BASE_CLOCK_SPEED + (CLOCK_SPEED_PER_POWERSHARD * getPowerShards());
    }
}

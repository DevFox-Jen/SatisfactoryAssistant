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
        throw new MethodNotImplementedException();
    }

    /**
     * Get the number of parts output per minute from the machine, for the current recipe, for the current machine properties
     * @return
     */
    public ItemStack getOutputPartsPerMin(){
        throw new MethodNotImplementedException();
    }

    public void setClockSpeed(float clockSpeed){
        throw new MethodNotImplementedException();
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
        throw new MethodNotImplementedException();
    }

    public float getMaxClockSpeed(){
        throw new MethodNotImplementedException();
    }
}

package com.devfox.production;

import com.devfox.recipes.Recipe;

/**
 * A micromachine is an extension of the {@link Machine} class which includes clockspeed limitations and powershards
 */
public class MicroMachine extends Machine {
    private int powerShards;
    private static final float CLOCK_SPEED_PER_POWERSHARD = 0.5f; //The increase in max clock speed per powershard added

    public MicroMachine(Recipe recipe) {
        super(recipe);
        powerShards = 0;
    }


    public int getPowerShards(){
        return powerShards;
    }

    public void setPowerShard(int powerShards){
        this.powerShards = powerShards;
    }

    @Override
    public float getMaxClockSpeed(){
        return BASE_CLOCK_SPEED + (CLOCK_SPEED_PER_POWERSHARD * getPowerShards());
    }
}

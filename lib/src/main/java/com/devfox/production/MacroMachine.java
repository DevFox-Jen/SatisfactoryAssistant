package com.devfox.production;

import com.devfox.recipes.Recipe;

/**
 * An extension of the {@link Machine} class which allows for unlimited clock speeds.
 */
public class MacroMachine extends Machine{

    public MacroMachine(Recipe recipe) {
        super(recipe);
    }

    @Override
    public float getMaxClockSpeed() {
        return Float.POSITIVE_INFINITY;
    }
}

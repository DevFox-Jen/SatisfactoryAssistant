package com.devfox.production;

import com.devfox.recipes.Recipe;

/**
 * A mock implementation of the {@link Machine} class for testing the implemented methods of the abstract class
 */
public class MockMachine extends Machine{

    public MockMachine(Recipe recipe) {
        super(recipe);
    }

    @Override
    public float getMaxClockSpeed() {
        return Float.POSITIVE_INFINITY;
    }
}


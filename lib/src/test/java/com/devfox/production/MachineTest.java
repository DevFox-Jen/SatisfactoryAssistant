package com.devfox.production;

import com.devfox.items.ItemNames;
import com.devfox.items.ItemStack;
import com.devfox.recipes.Recipe;
import com.devfox.recipes.persistence.RecipeListIOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

public class MachineTest {
    private Machine testMachine;
    private Recipe testRecipe;

    private static final float POWER_SHARD_CLOCK_SPEED_MODIFIER = +0.5f;
    private static final float BASE_MACHINE_CLOCK_SPEED = 1.0f;
    private static final float NORMAL_CLOCK_SPEED = 0.5f; //A clock speed that lies within the acceptable parameters before adding power shards
    private static final float SECS_IN_MIN = 60.0f;

    @Before
    public void setup() throws FileNotFoundException, RecipeListIOException {

        testRecipe = new Recipe("IronPlate",new ItemStack[]{new ItemStack(ItemNames.IRON_INGOT,3)},
                new ItemStack(ItemNames.IRON_PLATE,2),
                6.0f); //Recipe is Iron_Ingot 3 -> 6 seconds -> Iron_Plate 2

        testMachine = new MockMachine(testRecipe);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestSetClockSpeedThrowsWhenSpeedLessThan0(){
        testMachine.setClockSpeed(-1.0f);
    }

    @Test
    public void TestProductionRateVaryWithClockSpeed(){
        testMachine.setClockSpeed(0.5f); //Half clock rate means double the time taken to produce something
        float expectedProductionRate = testRecipe.getTimeTakenSecs() * 2.0f;
        Assert.assertEquals(expectedProductionRate,testMachine.getProductionRate(),0.0f);
    }

    @Test
    public void TestProductionRateIsZeroAtZeroClockSpeed(){
        testMachine.setClockSpeed(0.0f);
        Assert.assertEquals(0.0f,testMachine.getProductionRate(),0.0f);
    }

    @Test
    public void TestOutputPartsPerMinScalesCorrectly(){
        float testRecipeIterationsPerMin = SECS_IN_MIN / testRecipe.getTimeTakenSecs();
        float clockSpeed = 0.25f; //Should drop the output rate to 5ppm
        testMachine.setClockSpeed(clockSpeed);
        Assert.assertEquals(5.0f,testMachine.getOutputPartsPerMin().getCount(),0.0f);
    }

    @Test
    public void TestInputPartsPerMinScalesCorrectly(){
        float testRecipeIterationsPerMin = SECS_IN_MIN / testRecipe.getTimeTakenSecs();
        float clockSpeed = 3.5f;
        float testRecipeScaledIterationsPerMin = testRecipeIterationsPerMin * clockSpeed;

        ItemStack[] scaledItemStacks = new ItemStack[testRecipe.getInputItemStacks().length];
        for(int index = 0;index < testRecipe.getInputItemStacks().length;index++){
            ItemStack currentItemStack = testRecipe.getInputItemStacks()[index];
            scaledItemStacks[index] = new ItemStack(currentItemStack.getItemID(),currentItemStack.getCount() * testRecipeScaledIterationsPerMin);
        }

        testMachine.setClockSpeed(clockSpeed);
        Assert.assertArrayEquals(scaledItemStacks,testMachine.getInputPartsPerMin());
    }

    @Test(expected = IllegalConfigurationException.class)
    public void TestSetOutputPPMThrowsIfPPMLessThan0(){
        testMachine.setOutputPartsPerMinute(-1.0f);
    }

    @Test
    public void TestSetOutputPPMWithZeroWorks(){
        testMachine.setOutputPartsPerMinute(0.0f);
    }

    @Test
    public void TestSetOutputPPMWithValidInputWorks(){
        testMachine.setOutputPartsPerMinute(5.0f);
        Assert.assertEquals(0.25f,testMachine.getCurrentClockSpeed(),0.0f);
    }
}

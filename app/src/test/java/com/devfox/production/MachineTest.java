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
    private static final float NORMAL_CLOCK_SPEED = BASE_MACHINE_CLOCK_SPEED - 0.2f; //A clock speed that lies within the acceptable parameters before adding power shards
    private static final float SECS_IN_MIN = 60.0f;

    @Before
    public void setup() throws FileNotFoundException, RecipeListIOException {

        testRecipe = new Recipe("IronPlate",new ItemStack[]{new ItemStack(ItemNames.IRON_INGOT,3)},
                new ItemStack(ItemNames.IRON_PLATE,2),
                6.0f); //Recipe is Iron_Ingot 3 -> 6 seconds -> Iron_Plate 2

        testMachine = new Machine(testRecipe);
    }

    @Test
    public void TestMaxClockSpeedWithPowerShards(){
        int numPowerShards = 1;
        testMachine.setPowerShard(numPowerShards);
        Assert.assertEquals(BASE_MACHINE_CLOCK_SPEED + (POWER_SHARD_CLOCK_SPEED_MODIFIER * numPowerShards),testMachine.getMaxClockSpeed(),0.0f);
        numPowerShards = 3;
        testMachine.setPowerShard(numPowerShards);
        Assert.assertEquals(BASE_MACHINE_CLOCK_SPEED + (POWER_SHARD_CLOCK_SPEED_MODIFIER * numPowerShards),testMachine.getMaxClockSpeed(),0.0f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestSetClockSpeedThrowsOnExceedingMaxBaseClockSpeed(){
        testMachine.setClockSpeed(BASE_MACHINE_CLOCK_SPEED + 1.0f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestSetClockSpeedThrowsOnExceedMaxClockSpeedWithPowerShard(){
        int numPowerShards = 2;
        testMachine.setPowerShard(numPowerShards);
        testMachine.setClockSpeed(BASE_MACHINE_CLOCK_SPEED + (POWER_SHARD_CLOCK_SPEED_MODIFIER * numPowerShards) + 1.0f);
    }

    @Test
    public void TestSetClockSpeedSetsSpeedWithinBounds(){
        testMachine.setClockSpeed(NORMAL_CLOCK_SPEED);
    }

    @Test
    public void TestSetClockSpeedSetsAtEdgeCases(){
        testMachine.setClockSpeed(BASE_MACHINE_CLOCK_SPEED);
        testMachine.setClockSpeed(0.0f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestSetClockSpeedThrowsWhenSpeedLessThan0(){
        testMachine.setClockSpeed(-1.0f);
    }

    @Test
    public void TestProductionRateVaryWithClockSpeed(){
        testMachine.setClockSpeed(NORMAL_CLOCK_SPEED);
        float expectedProductionRate = testRecipe.getTimeTakenSecs() * NORMAL_CLOCK_SPEED;
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
        float randomClockSpeed = (float)Math.random();
        float testRecipeScaledIterationsPerMin = testRecipeIterationsPerMin * randomClockSpeed;
        ItemStack scaledItemStack = new ItemStack(testRecipe.getOutputItemStack().getItemID(),testRecipe.getOutputItemStack().getCount() * testRecipeScaledIterationsPerMin);
        testMachine.setClockSpeed(randomClockSpeed);
        Assert.assertEquals(scaledItemStack,testMachine.getOutputPartsPerMin());
    }

    @Test
    public void TestInputPartsPerMinScalesCorrectly(){
        float testRecipeIterationsPerMin = SECS_IN_MIN / testRecipe.getTimeTakenSecs();
        float randomClockSpeed = (float)Math.random();
        float testRecipeScaledIterationsPerMin = testRecipeIterationsPerMin * randomClockSpeed;

        ItemStack[] scaledItemStacks = new ItemStack[testRecipe.getInputItemStacks().length];
        for(int index = 0;index < testRecipe.getInputItemStacks().length;index++){
            ItemStack currentItemStack = testRecipe.getInputItemStacks()[index];
            scaledItemStacks[index] = new ItemStack(currentItemStack.getItemID(),currentItemStack.getCount() * testRecipeScaledIterationsPerMin);
        }

        testMachine.setClockSpeed(randomClockSpeed);
        Assert.assertArrayEquals(scaledItemStacks,testMachine.getInputPartsPerMin());
    }
}

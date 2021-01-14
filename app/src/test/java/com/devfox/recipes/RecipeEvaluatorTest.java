package com.devfox.recipes;

import com.devfox.items.Items;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RecipeEvaluatorTest {
    private Recipe[] plateRecipeTestSet;

    @Before
    public void initialise(){
        plateRecipeTestSet = RecipeGenerator.GetFirstRecipeSet();
    }

    @Test public void TestIronPlateRecipe(){
        float output = RecipeEvaluator.calcAmountRequiredPerMin(plateRecipeTestSet, Items.IRON_PLATE,20, Items.IRON_ORE);
        assertEquals(30.0f,output,0.0f);
    }

    @Test public void TestIronPlateRecipeWithOverQuota(){
        float output = RecipeEvaluator.calcAmountRequiredPerMin(plateRecipeTestSet,Items.IRON_PLATE,75, Items.IRON_ORE);
        assertEquals(112.5f,output,0.0f);
    }

    @Test (expected = RecipeMissingException.class) public void TestExceptionWhenNoRecipe(){
        Recipe[] emptyRecipes = new Recipe[0];
        RecipeEvaluator.calcAmountRequiredPerMin(emptyRecipes, Items.IRON_PLATE,20, Items.IRON_ORE);
    }

    @Test public void TestGetComponents(){
        Items[] components = RecipeEvaluator.getComponentsOfItem(plateRecipeTestSet,Items.IRON_PLATE);
        Items[] actualComponents = new Items[]{Items.IRON_INGOT,Items.IRON_ORE};
        assertArrayEquals(actualComponents,components);
    }
}

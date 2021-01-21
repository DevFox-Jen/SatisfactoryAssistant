package com.devfox.recipes;

import com.devfox.items.ItemNames;
import com.devfox.items.ItemStack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RecipeManagerTest {
    private Recipe equalRecipe1;
    private Recipe equalRecipe2;

    @Before
    public void setup(){
        RecipeManager.getInstance().destroyInstance();
        equalRecipe1 = new Recipe("Test Recipe 1",
                new ItemStack[]{new ItemStack(ItemNames.IRON_ORE,1.0f)},new ItemStack(ItemNames.IRON_INGOT,1.0f),1.0f);
        equalRecipe2 = new Recipe("Test Recipe 1",
                new ItemStack[]{new ItemStack(ItemNames.IRON_ORE,1.0f)},new ItemStack(ItemNames.IRON_INGOT,1.0f),1.0f);
    }

    @Test
    public void TestRemoveRecipeSuccessfullyRemovesUsingIdenticalRecipe(){
        Assert.assertEquals("The identical recipe is not classed as equal using .equals",equalRecipe1,equalRecipe2);

        RecipeManager.getInstance().addRecipe(equalRecipe1);

        Recipe[] recipes = RecipeManager.getInstance().getRecipeList();
        Assert.assertEquals("The recipe was not added to the RecipeManager's list ",equalRecipe1,recipes[0]);

        //Now call remove using the identical recipe but different object
        RecipeManager.getInstance().removeRecipe(equalRecipe2);
        Assert.assertEquals("The first recipe was not removed when calling remove using the identical second recipe",0,RecipeManager.getInstance().getRecipeList().length);
    }

    @Test
    public void TestRecipeManagerDoesNotAllowDuplicateRecipe(){
        RecipeManager.getInstance().addRecipe(equalRecipe1);
        RecipeManager.getInstance().addRecipe(equalRecipe1);
        Assert.assertEquals("A duplicate of the recipe was able to be added",1,RecipeManager.getInstance().getRecipeList().length);
    }

    @Test
    public void TestRecipeManagerDoesNotAllowEqualRecipe(){
        RecipeManager.getInstance().addRecipe(equalRecipe1);
        RecipeManager.getInstance().addRecipe(equalRecipe2);
        Assert.assertEquals("An identical recipe was allowed to be added to the RecipeManager's list ",1,RecipeManager.getInstance().getRecipeList().length);
    }

    @Test
    public void TestClearRecipeListClearsList(){
        RecipeManager.getInstance().addRecipe(equalRecipe1);
        Assert.assertEquals("Recipe failed to add to list",1,RecipeManager.getInstance().getRecipeList().length);
        RecipeManager.getInstance().clearRecipeList();
        Assert.assertEquals("Recipe was not removed",0,RecipeManager.getInstance().getRecipeList().length);
    }

    @Test
    public void TestDestroyRecipeManagerSuccessful(){
        int rHashCode = RecipeManager.getInstance().hashCode();
        RecipeManager.getInstance().destroyInstance();
        int newHashCode = RecipeManager.getInstance().hashCode();
        Assert.assertNotEquals("The hashcode of the RecipeManagers was the same so the manager was not destroyed",rHashCode,newHashCode);
    }
}

package com.devfox.recipes.persistence;

import com.devfox.items.ItemNames;
import com.devfox.items.ItemStack;
import com.devfox.recipes.BaseRecipe;
import com.devfox.recipes.Recipe;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class RecipeListXMLFileIOTest {
    private static final String TEST_RECIPE_FILE_NAME = "recipes_test.xml";
    private static final String EMPTY_RECIPE_FILE_NAME = "empty_recipes.xml";
    private File testRecipesFile;
    private File emptyRecipesFile;
    private Recipe[] groundTruthTestRecipeList; //The manually created Recipe array equivalent of the recipes_test.xml file
    private RecipeListIO recipeListIO;

    @Before
    public void setup(){
        recipeListIO = new RecipeListXMLFileIO();
        testRecipesFile = new File(ClassLoader.getSystemResource(TEST_RECIPE_FILE_NAME).getPath());
        emptyRecipesFile = new File(ClassLoader.getSystemResource(EMPTY_RECIPE_FILE_NAME).getPath());

        groundTruthTestRecipeList = new Recipe[3];
        groundTruthTestRecipeList[0] = new BaseRecipe("NormalIronOre",new ItemStack(ItemNames.IRON_ORE,1),1.0f);
        groundTruthTestRecipeList[1] = new Recipe("IronIngot",new ItemStack[]{new ItemStack(ItemNames.IRON_ORE,1.0f)},new ItemStack(ItemNames.IRON_INGOT,1.0f),2.0f);
        groundTruthTestRecipeList[2] = new Recipe("IronPlate",new ItemStack[]{new ItemStack(ItemNames.IRON_INGOT,3.0f)},new ItemStack(ItemNames.IRON_PLATE,2.0f),6.0f);
    }

    @Test
    public void TestLoadListReturnsCorrectRecipes() throws RecipeListIOException {
        Assert.assertArrayEquals("The recipe list created from the file did not match the manually created list ",
                groundTruthTestRecipeList,
                recipeListIO.loadList(testRecipesFile));
    }

    @Test
    public void TestLoadListReturnsEmptyArrayWithEmptyFile() throws RecipeListIOException{
        Assert.assertArrayEquals("The recipe list returned was not empty",new Recipe[0],recipeListIO.loadList(emptyRecipesFile));
    }
}

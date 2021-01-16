package com.devfox.recipes.persistence;

import com.devfox.items.ItemNames;
import com.devfox.items.ItemStack;
import com.devfox.recipes.BaseRecipe;
import com.devfox.recipes.Recipe;
import com.google.common.base.Ascii;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class RecipeListXMLIOTest {
    private static final String TEST_RECIPE_FILE_NAME = "recipes_test.xml";
    private static final String EMPTY_RECIPE_FILE_NAME = "empty_recipes.xml";
    private static final Logger logger = LogManager.getLogger(RecipeListXMLIOTest.class);
    private File testRecipesFile;
    private File emptyRecipesFile;

    private InputStream testRecipesInputStream;
    private InputStream emptyRecipesInputStream;
    private ByteArrayOutputStream testRecipesOutputStream;

    private Recipe[] groundTruthTestRecipeList; //The manually created Recipe array equivalent of the recipes_test.xml file
    private RecipeListIO recipeListIO;

    @Before
    public void setup() throws FileNotFoundException {
        recipeListIO = new RecipeListXMLIO();

        testRecipesFile = new File(ClassLoader.getSystemResource(TEST_RECIPE_FILE_NAME).getPath());
        emptyRecipesFile = new File(ClassLoader.getSystemResource(EMPTY_RECIPE_FILE_NAME).getPath());
        testRecipesInputStream = new FileInputStream(testRecipesFile);
        emptyRecipesInputStream = new FileInputStream(emptyRecipesFile);

        testRecipesOutputStream = new ByteArrayOutputStream(); //For writing to instead of writing to the file system

        groundTruthTestRecipeList = new Recipe[3];
        groundTruthTestRecipeList[0] = new BaseRecipe("NormalIronOre",new ItemStack(ItemNames.IRON_ORE,1),1.0f);
        groundTruthTestRecipeList[1] = new Recipe("IronIngot",new ItemStack[]{new ItemStack(ItemNames.IRON_ORE,1.0f)},new ItemStack(ItemNames.IRON_INGOT,1.0f),2.0f);
        groundTruthTestRecipeList[2] = new Recipe("IronPlate",new ItemStack[]{new ItemStack(ItemNames.IRON_INGOT,3.0f)},new ItemStack(ItemNames.IRON_PLATE,2.0f),6.0f);
    }

    @Test
    public void TestLoadListReturnsCorrectRecipes() throws RecipeListIOException {
        Assert.assertArrayEquals("The recipe list created from the file did not match the manually created list ",
                groundTruthTestRecipeList,
                recipeListIO.loadList(testRecipesInputStream));
    }

    @Test
    public void TestLoadListReturnsEmptyArrayWithEmptyFile() throws RecipeListIOException{
        Assert.assertArrayEquals("The recipe list returned was not empty",new Recipe[0],recipeListIO.loadList(emptyRecipesInputStream));
    }

    @Test
    public void TestSaveListSuccessfullyWritesFileToSystem() throws RecipeListIOException{
        recipeListIO.saveList(groundTruthTestRecipeList,testRecipesOutputStream);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(testRecipesOutputStream.toByteArray());
        Assert.assertArrayEquals("The recipelist output and read back in was not the same as the original output",groundTruthTestRecipeList,recipeListIO.loadList(byteArrayInputStream));
    }
}

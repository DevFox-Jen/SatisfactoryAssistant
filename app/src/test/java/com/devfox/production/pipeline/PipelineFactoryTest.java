package com.devfox.production.pipeline;

import com.devfox.items.ItemNames;
import com.devfox.recipes.Recipe;
import com.devfox.recipes.RecipeManager;
import com.devfox.recipes.persistence.RecipeListIOException;
import com.devfox.recipes.persistence.RecipeListTestFiles;
import com.devfox.recipes.persistence.RecipeListXMLIO;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

public class PipelineFactoryTest {
    Recipe[] ironPlateRecipeSet;
    Recipe[] emptyRecipeList = new Recipe[0];

    @Before
    public void setup() throws FileNotFoundException, RecipeListIOException {
        //Load the basic iron plate recipe set into the recipe manager
        RecipeManager.getInstance().destroyInstance();
        File ironPlateRecipeFile = new File(ClassLoader.getSystemResource(RecipeListTestFiles.BASIC_RECIPE_LIST_FILE).getPath());
        FileInputStream fileInputStream = new FileInputStream(ironPlateRecipeFile);
        RecipeManager.getInstance().loadRecipeList(new RecipeListXMLIO(),fileInputStream);

        ironPlateRecipeSet = RecipeManager.getInstance().getRecipeList();
    }

    @Test(expected = RecipeMissingException.class)
    public void TestBuildPipelineThrowsIfRecipeListEmpty(){
        MockPipelineFactory mockPipelineFactory = new MockPipelineFactory(ItemNames.IRON_ORE);
        mockPipelineFactory.buildPipeline(emptyRecipeList);
    }

    @Test(expected = RecipeMissingException.class)
    public void TestBuildPipelineThrowsIfRecipeForTargetItemMissing(){
        MockPipelineFactory mockPipelineFactory = new MockPipelineFactory(ItemNames.COPPER_ORE);
        mockPipelineFactory.buildPipeline(ironPlateRecipeSet);
    }

    @Test(expected = RecipeMissingException.class)
    public void TestBuildPipelineThrowsIfRequiredRecipeMissingFromList(){
        //Test that if a recipe list passed into the factory is missing a recipe required to craft the output item, then the method throws an exception

        //Remove the iron ingot recipe from the list
        ArrayList<Recipe> recipeArrayList = new ArrayList<>(Arrays.asList(ironPlateRecipeSet));
        recipeArrayList.removeIf((recipe) -> recipe.getOutputItemStack().getItemID().equals(ItemNames.IRON_INGOT));

        //Now try to create a pipeline for the Iron Plate
        MockPipelineFactory mockPipelineFactory = new MockPipelineFactory(ItemNames.IRON_PLATE);
        mockPipelineFactory.buildPipeline(recipeArrayList.toArray(new Recipe[0]));
    }
}

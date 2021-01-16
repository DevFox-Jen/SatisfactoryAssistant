package com.devfox.recipes;

import static org.junit.Assert.*;

import com.devfox.recipes.persistence.RecipeListIOException;
import com.devfox.recipes.persistence.RecipeListXMLIO;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static com.devfox.items.ItemNames.*;
import static com.devfox.recipes.persistence.RecipeListTestFiles.*;

@Ignore
public class RecipeEvaluatorTest {
    private Recipe[] plateRecipeTestSet;

    @Before
    public void initialise() throws IOException, RecipeListIOException {
        File recipeFile = new File(ClassLoader.getSystemResource(BASIC_RECIPE_LIST_FILE).getPath());
        FileInputStream fileInputStream = new FileInputStream(recipeFile);
        RecipeManager recipeManager = RecipeManager.getInstance();
        recipeManager.loadRecipeList(new RecipeListXMLIO(),fileInputStream);
        plateRecipeTestSet = recipeManager.getRecipeList();

        //plateRecipeTestSet = RecipeGenerator.readRecipesFromXMLFile(new File(ClassLoader.getSystemResource("recipes_test.xml").getPath()));
    }

    @Test public void TestIronPlateRecipeAmountRequired(){
        float output = RecipeEvaluator.calcAmountRequiredPerMin(plateRecipeTestSet, IRON_PLATE,20, IRON_ORE);
        assertEquals(30.0f,output,0.0f);
    }

    @Test public void TestIronPlateRecipeWithOverQuota(){
        float output = RecipeEvaluator.calcAmountRequiredPerMin(plateRecipeTestSet,IRON_PLATE,75, IRON_ORE);
        assertEquals(112.5f,output,0.0f);
    }

    @Test (expected = RecipeMissingException.class) public void TestExceptionWhenNoRecipe(){
        Recipe[] emptyRecipes = new Recipe[0];
        RecipeEvaluator.calcAmountRequiredPerMin(emptyRecipes, IRON_PLATE,20, IRON_ORE);
    }

    @Test public void TestGetComponents(){
        String[] components = RecipeEvaluator.getComponentsOfItem(plateRecipeTestSet,IRON_PLATE);
        String[] actualComponents = new String[]{IRON_ORE,IRON_INGOT};
        assertArrayEquals(actualComponents,components);
    }
}

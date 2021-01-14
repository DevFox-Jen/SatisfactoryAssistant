package com.devfox.recipes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import static com.devfox.items.ItemNames.*;

public class RecipeEvaluatorTest {
    private Recipe[] plateRecipeTestSet;

    @Before
    public void initialise() throws IOException, SAXException, ParserConfigurationException {
        plateRecipeTestSet = RecipeGenerator.readRecipesFromXMLFile(new File(ClassLoader.getSystemResource("recipes.xml").getPath()));
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
        String[] actualComponents = new String[]{IRON_INGOT,IRON_ORE};
        assertArrayEquals(actualComponents,components);
    }
}

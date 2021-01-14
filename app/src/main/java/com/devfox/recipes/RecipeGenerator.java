package com.devfox.recipes;

import com.devfox.items.ItemStack;
import com.devfox.items.Items;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class RecipeGenerator {
    private static final String RECIPE_TAG = "recipe";
    private static final String RECIPE_NAME_ATTRIBUTE = "name";
    private static final String RECIPE_TIME_TAKEN_SECS = "timetakensecs";
    private static final String RECIPE_INPUT_TAG = "input";
    private static final String RECIPE_OUTPUT_TAG = "output";
    private static final String RECIPE_STACK_TAG = "stack";
    private static final String RECIPE_COUNT_ATTRIBUTE = "count";

    /**
     * The recipe set made before implementing a better storage method
     * @return
     */
    public static Recipe[] GetFirstRecipeSet(){
        List<Recipe> recipeList = new ArrayList<>();

        recipeList.add(new BaseRecipe("NormalIronOre",new ItemStack(Items.IRON_ORE,1),1.0f));
        recipeList.add(new OneToOneRecipe("IronIngot",new ItemStack(Items.IRON_ORE,1),new ItemStack(Items.IRON_INGOT,1),2.0f));
        recipeList.add(new OneToOneRecipe("IronPlate",new ItemStack(Items.IRON_INGOT,3),new ItemStack(Items.IRON_PLATE,2),6.0f));
        recipeList.add(new OneToOneRecipe("CopperIngot",new ItemStack(Items.COPPER_ORE,1),new ItemStack(Items.COPPER_INGOT,1),2.0f));
        recipeList.add(new Recipe("ReinforcedIronPlate",new ItemStack[]{new ItemStack(Items.IRON_PLATE,6),new ItemStack(Items.SCREW,12)},new ItemStack(Items.REINFORCED_IRON_PLATE,1.0f),12.0f));

        return recipeList.toArray(new Recipe[0]);
    }

    public static Recipe[] readRecipesFromXMLFile(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        NodeList recipeNodes = doc.getElementsByTagName("");
    }
}

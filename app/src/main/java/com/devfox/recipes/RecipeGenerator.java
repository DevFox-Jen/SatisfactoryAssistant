package com.devfox.recipes;

import com.devfox.items.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
    private static final Logger logger = LogManager.getLogger(RecipeGenerator.class);

    public static Recipe[] readRecipesFromXMLFile(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        List<Recipe> recipeList = new ArrayList<>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        NodeList recipeNodes = doc.getElementsByTagName(RECIPE_TAG);

        //For each node with the recipe tag
        for(int i = 0;i < recipeNodes.getLength();i++){
            Node node = recipeNodes.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element recipeNode = (Element) node;
                String recipeName = recipeNode.getAttribute(RECIPE_NAME_ATTRIBUTE);
                float recipeTimeTakenSecs = Float.parseFloat(recipeNode.getAttribute(RECIPE_TIME_TAKEN_SECS));

                logger.trace("Creating recipe called: " + recipeName);
                Element outputNode = (Element)recipeNode.getElementsByTagName(RECIPE_OUTPUT_TAG).item(0); //Assume the node exists since there should always be output for recipe
                Element outputStackNode = (Element)outputNode.getElementsByTagName(RECIPE_STACK_TAG).item(0); //We assume there is only 1 output and its an element node
                ItemStack outputStack = new ItemStack(outputStackNode.getTextContent(),Float.parseFloat(outputStackNode.getAttribute(RECIPE_COUNT_ATTRIBUTE)));


                Element inputNode = (Element)recipeNode.getElementsByTagName(RECIPE_INPUT_TAG).item(0); //Assume the input tag exists

                List<ItemStack> inputStacks = new ArrayList<>();
                for(int j = 0;j < inputNode.getElementsByTagName(RECIPE_STACK_TAG).getLength();j++){
                    Element inputStackNode = (Element)inputNode.getElementsByTagName(RECIPE_STACK_TAG).item(j);
                    String itemName = inputStackNode.getTextContent();
                    ItemStack inputStack = new ItemStack(itemName,Float.parseFloat(inputStackNode.getAttribute(RECIPE_COUNT_ATTRIBUTE)));
                    inputStacks.add(inputStack);
                }

                Recipe recipe = new Recipe(recipeName,inputStacks.toArray(new ItemStack[0]),outputStack,recipeTimeTakenSecs);
                recipeList.add(recipe);
            }
        }
        return recipeList.toArray(new Recipe[0]);
    }
}

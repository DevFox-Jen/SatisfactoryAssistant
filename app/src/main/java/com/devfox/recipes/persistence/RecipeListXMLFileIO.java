package com.devfox.recipes.persistence;

import com.devfox.items.ItemStack;
import com.devfox.recipes.Recipe;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.devfox.recipes.persistence.XMLRecipeFileKeyWords.*;

public class RecipeListXMLFileIO implements RecipeListIO{

    @Override
    public void saveList(Recipe[] recipeList, File location) throws RecipeListIOException {

    }

    @Override
    public Recipe[] loadList(File location) throws RecipeListIOException {
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document document = builder.parse(location);

            NodeList recipeNodeList = document.getElementsByTagName(RECIPE_TAG); //Find all recipe tags
            List<Recipe> recipeList = new ArrayList<>();
            for(int index = 0;index < recipeNodeList.getLength();index++){
                recipeList.add(parseDOMElementToRecipe((Element)recipeNodeList.item(index)));
            }
            return recipeList.toArray(new Recipe[0]);
        }catch(Exception e){
            throw new RecipeListIOException(e.getMessage(), e);
        }
    }

    private Recipe parseDOMElementToRecipe(Element element){
        if(!element.getNodeName().equals(RECIPE_TAG))
            throw new IllegalArgumentException("The DOM node must be a " + RECIPE_TAG + " node");

        String recipeName = element.getAttribute(NAME_ATTR);
        float processTime = Float.parseFloat(element.getAttribute(PROCESS_TIME_ATTR));

        Element inputElement = (Element)element.getElementsByTagName(INPUT_TAG).item(0); //WARNING - Assumes there is an input element
        ItemStack[] inputItemStacks = parseDOMElementToItemStackArray(inputElement);

        Element outputElement = (Element)element.getElementsByTagName(OUTPUT_TAG).item(0); //WARNING - Assumes there is an output element
        ItemStack outputItemStack = parseDOMElementToItemStackArray(outputElement)[0]; //WARNING - Assumes there is only 1 output item and ignores all others
        return new Recipe(recipeName,inputItemStacks,outputItemStack,processTime);
    }

    /**
     * Parse a {@link XMLRecipeFileKeyWords#INPUT_TAG} or {@link XMLRecipeFileKeyWords#OUTPUT_TAG} node to an array of ItemStack
     * @param element
     * @return An array containing 0 or more ItemStack objects that were encoded in the DOM element
     */
    private ItemStack[] parseDOMElementToItemStackArray(Element element){
        if(!element.getNodeName().equals(INPUT_TAG) && !element.getNodeName().equals(OUTPUT_TAG))
            throw new IllegalArgumentException("The DOM node must be a " + INPUT_TAG + " node or a " + OUTPUT_TAG + " node");

        NodeList stackNodeList = element.getElementsByTagName(STACK_TAG);
        List<ItemStack> itemStacks = new ArrayList<>();
        for(int index = 0;index < stackNodeList.getLength();index++){
            itemStacks.add(parseDOMElementToItemStack((Element)stackNodeList.item(index)));
        }
        return itemStacks.toArray(new ItemStack[0]);
    }

    /**
     * Parse a {@link XMLRecipeFileKeyWords#STACK_TAG} element to an ItemStack
     * @param element the stack element to parse
     * @return The corresponding item stack
     */
    private ItemStack parseDOMElementToItemStack(Element element){
        if(!element.getNodeName().equals(STACK_TAG))
            throw new IllegalArgumentException("The element must be a " + STACK_TAG + " node");
        float count = Float.parseFloat(element.getAttribute(COUNT_ATTR));
        String itemID = element.getTextContent();
        return new ItemStack(itemID,count);
    }
}

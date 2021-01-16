package com.devfox.recipes.persistence;

import com.devfox.items.ItemStack;
import com.devfox.recipes.Recipe;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.devfox.recipes.persistence.XMLRecipeFileKeyWords.*;

public class RecipeListXMLFileIO implements RecipeListIO{

    @Override
    public void saveList(Recipe[] recipeList, File location) throws RecipeListIOException {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document document = builder.newDocument();

            Element rootElement = document.createElement(ROOT_TAG);
            document.appendChild(rootElement);

            for(Recipe recipe : recipeList){
                rootElement.appendChild(parseRecipeToDOMElement(recipe,document));
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            FileOutputStream fileOutputStream = new FileOutputStream(location);
            StreamResult result = new StreamResult(fileOutputStream);
            transformer.transform(source,result);
        }catch(Exception e){
            throw new RecipeListIOException(e.getMessage(),e);
        }
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

    /**
     * Converts a recipe to an XML element and uses the provided document to create a new recipe element in that document.
     * WARNING - This operates on the assumption that the returned element will only belong in the document provided to the method.
     * @param recipe the recipe to parse
     * @param document The document to use
     * @return the new element
     */
    private Element parseRecipeToDOMElement(Recipe recipe,Document document){
        Element recipeElement = document.createElement(RECIPE_TAG);
        recipeElement.setAttribute(NAME_ATTR,recipe.getName());
        recipeElement.setAttribute(PROCESS_TIME_ATTR,String.valueOf(recipe.getTimeTakenSecs()));

        //Parse the input item stacks
        Element inputElement = document.createElement(INPUT_TAG);
        for(Element element : parseItemStackArrayToStackElements(recipe.getInputItemStacks(),document)){
            inputElement.appendChild(element);
        }
        recipeElement.appendChild(inputElement);

        //Parse the output item stacks
        Element outputElement = document.createElement(OUTPUT_TAG);
        for(Element element : parseItemStackArrayToStackElements(new ItemStack[]{recipe.getOutputItemStack()},document)){
            outputElement.appendChild(element);
        }
        recipeElement.appendChild(outputElement);

        return recipeElement;
    }

    /**
     * Given an array of item stacks, parses them to a set of elements with the {@link XMLRecipeFileKeyWords#STACK_TAG} tag and populated fields
     * @param itemStacks the item stacks to parse
     * @param document the document to use when creating the elements
     * @return An array of stack elements associated with the provided document
     */
    private Element[] parseItemStackArrayToStackElements(ItemStack[] itemStacks, Document document){
        Element[] elements = new Element[itemStacks.length];
        for(int index = 0;index < elements.length;index++){
            elements[index] = parseItemStackToStackElement(itemStacks[index],document);
        }
        return elements;
    }

    private Element parseItemStackToStackElement(ItemStack itemStack, Document document){
        Element stackElement = document.createElement(STACK_TAG);
        stackElement.setAttribute(COUNT_ATTR,String.valueOf(itemStack.getCount()));
        stackElement.setTextContent(itemStack.getItemID());
        return stackElement;
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

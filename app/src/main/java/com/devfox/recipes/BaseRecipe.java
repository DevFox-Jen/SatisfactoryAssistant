package com.devfox.recipes;

import com.devfox.items.ItemStack;
import com.devfox.items.Items;

/**
 * A recipe with no inputs
 */
public class BaseRecipe extends Recipe{
    public BaseRecipe(String name, ItemStack outputItemStack,float timeTakenSecs){
        super(name,new ItemStack[0],outputItemStack,timeTakenSecs);
    }
}

package com.devfox.recipes;

import com.devfox.items.ItemStack;

/**
 * OneToOne recipes are one to one mappings. One input, one output
 */
public class OneToOneRecipe extends Recipe{
    public OneToOneRecipe(String name, ItemStack inputItemStack, ItemStack outputItemStack, float timeTaken){
        super(name,new ItemStack[]{inputItemStack},outputItemStack,timeTaken);
    }
}

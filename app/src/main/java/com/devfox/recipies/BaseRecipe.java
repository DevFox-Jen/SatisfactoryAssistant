package com.devfox.recipies;

import com.devfox.items.Items;

/**
 * A recipe with no inputs
 */
public class BaseRecipe extends Recipe{
    public BaseRecipe(String name, Items outputItem){
        super(name,null,outputItem);
    }
}

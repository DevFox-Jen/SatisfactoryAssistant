package com.devfox.items;

/**
 * Consists of an item and a count
 */
public class ItemStack {
    private float count;
    private String itemID;
    public ItemStack(String itemID, float count){
        this.count = count;
        this.itemID = itemID;
    }

    public float getCount(){
        return count;
    }

    public String getItemID(){
        return itemID;
    }
}

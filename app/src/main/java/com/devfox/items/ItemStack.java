package com.devfox.items;

/**
 * Consists of an item and a count
 */
public class ItemStack {
    private int count;
    private Items item;
    public ItemStack(Items item, int count){
        this.count = count;
        this.item = item;
    }

    public int getCount(){
        return count;
    }

    public Items getItem(){
        return item;
    }
}

package com.devfox.items;

import java.util.Objects;

/**
 * Consists of an item and a count
 */
public class ItemStack {
    private float count;
    private String itemID;
    public ItemStack(String itemID, float count){
        this.count = count;
        this.itemID = itemID.toLowerCase().strip();
    }

    public float getCount(){
        return count;
    }

    public String getItemID(){
        return itemID;
    }

    @Override
    public int hashCode(){
        return Objects.hash(count,itemID);
    }

    /**
     * Two Item stacks are equal if they have identical {@linkplain #itemID} and an identical count value
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof ItemStack){
            ItemStack otherItemStack = (ItemStack)obj;
            if(otherItemStack.itemID.equals(itemID)){
                if(otherItemStack.count == count){
                    return true;
                }
            }
        }
        
        return false;
    }
}

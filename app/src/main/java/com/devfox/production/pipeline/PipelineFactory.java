package com.devfox.production.pipeline;

import com.devfox.SatisfactoryAssistant.MethodNotImplementedException;
import com.devfox.items.ItemStack;
import com.devfox.recipes.Recipe;

import java.util.Map;

/**
 * The PipelineFactory aims to create a tree in the form of chained {@link PipelineNode}.
 */
public abstract class PipelineFactory {
    private String outputItem;

    public static final float NO_LIMIT = -1;
    private float outputTargetPerMinute = NO_LIMIT;

    /**
     *
     * @param targetItem the item that the factory should produce
     */
    public PipelineFactory(String targetItem){
        setOutputItem(targetItem);
    }

    public void setOutputItem(String itemName){
        outputItem = itemName;
    }

    public String getOutputItem(){
        return outputItem;
    }

    /**
     * Attempts to construct a pipeline to produce the set target item. if {@link #setTargetOutputPerMinute} has not been set, then the pipeline will aim to produce as much of the set target item as possible.
     * @param recipes The set of recipes to use for creating the pipeline. If there are duplicate recipes then the builder will use the first recipe it finds to produce an item.
     * @return The root pipeline node for the resulting pipeline
     */
    public PipelineNode buildPipeline(Recipe[] recipes){
        throw new MethodNotImplementedException();
    }

    /**
     * To be implemented by the extending class. Called when {@link #buildPipeline} is called.
     * @param recipes The set of recipes to use for creating the pipeline. If there are duplicate recipes then the builder will use the first recipe it finds to produce an item.
     * @return The root pipeline node for the resulting pipeline
     */
    public abstract PipelineNode onBuildPipeline(Recipe[] recipes);

    /**
     * Sets the amount of the output item to produce per minute
     * @param amountPerMinute amount to produce of the item set in {@link #setOutputItem}
     */
    public void setTargetOutputPerMinute(float amountPerMinute){
        outputTargetPerMinute = amountPerMinute;
    }
}

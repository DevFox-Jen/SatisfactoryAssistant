package com.devfox.production.pipeline;

import com.devfox.SatisfactoryAssistant.MethodNotImplementedException;
import com.devfox.recipes.Recipe;

public class PipelineFactory {
    public enum Type{
        XML
    }
    /**
     * Attempts to construct a pipeline to produce the target item at the target output rate
     * @param recipes The set of recipes to use for creating the pipeline. If there are duplicate recipes then the builder will use the first recipe it finds
     * @param targetItem The output item to produce
     * @param targetOutputPerMin The amount of the item to produce per minute
     * @param pipelineType how the underlying pipeline representation is implemented
     * @return The root pipeline node for the resulting pipeline
     */
    public PipelineNode buildPipeline(Recipe[] recipes, String targetItem, float targetOutputPerMin,Type pipelineType){

    }

    /**
     * Attempts to construct a pipeline to produce the maximum amount of the targetItem when the input of an item is limited to a certain amount per minute.
     * @param recipes The set of recipes to construct the pipeline using
     * @param targetItem The item to produce
     * @param limitingInputItem The item who's input rate is limited
     * @param limitingItemInputPerMin The input limit of the limiting item
     * @param pipelineType how the underlying pipeline representation is implemented
     * @return the root pipeline node for the resulting pipeline
     */
    public PipelineNode buildPipeline(Recipe[] recipes, String targetItem, String limitingInputItem, float limitingItemInputPerMin,Type pipelineType){
        throw new MethodNotImplementedException();
    }
}

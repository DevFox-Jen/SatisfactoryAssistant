package com.devfox.production.pipeline;

import com.devfox.MethodNotImplementedException;
import com.devfox.recipes.Recipe;

/**
 * A mock of the {@link PipelineFactory} that has no implementation for the extended methods. Used for testing the methods implemented in the abstract class.
 */
public class MockPipelineFactory extends PipelineFactory{
    public MockPipelineFactory(String targetItem) {
        super(targetItem);
    }

    @Override
    public Pipeline onBuildPipeline(Recipe[] recipes) {
        throw new MethodNotImplementedException();
    }
}

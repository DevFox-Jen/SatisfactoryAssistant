package com.devfox.production.pipeline;

import java.util.Iterator;

/**
 * A pipeline is a set of machines that feed into one another to form a tree. A pipeline can be constructed using the {@link PipelineFactory} implementations.
 * The tree formed is acyclic and directional although each {@link PipelineNode} contains a reference to both its parent and children.
 * The pipeline models the flow of items from the output of one machine, to the input of another.
 */
public class Pipeline implements Iterable<PipelineNode> {
    private PipelineNode rootNode;

    public Pipeline(PipelineNode rootNode){
        this.rootNode = rootNode;
    }

    @Override
    public Iterator<PipelineNode> iterator() {
        return new BFSPipelineIterator(rootNode);
    }
}

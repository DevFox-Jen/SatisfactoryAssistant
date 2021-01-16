package com.devfox.production.pipeline;

import com.devfox.production.Machine;

/**
 * {@linkplain PipelineNode} class represents a node in a tree which represents a manufacturing pipeline.
 * The root node of the tree is the final output item and every subsequent node is a manufacturing operation in the pipeline
 * Each {@linkplain PipelineNode} contains a {@link Machine} which produces some output.
 * It can be inferred that the output of one machine feeds into another with the exception of the root node and all leaf nodes.
 * Leaf nodes represent inputs into the pipeline that have no crafting recipe such as miners or storage containers
 */
public class PipelineNode {
    private PipelineNode childNode;
    private PipelineNode parentNode;
    private Machine machine;

    public PipelineNode(Machine machine){
        this.machine = machine;
    }

    public PipelineNode getChildNode(){
        return parentNode;
    }

    public PipelineNode getParentNode(){
        return childNode;
    }

    public Machine getMachine(){
        return machine;
    }

    public void setChildNode(PipelineNode childNode){
        this.childNode = childNode;
    }

    public void setParentNode(PipelineNode parentNode){
        this.parentNode = parentNode;
    }

    public boolean isRootNode(){
        return getParentNode() == null;
    }

    public boolean isLeafNode(){
        return getChildNode() == null;
    }
}

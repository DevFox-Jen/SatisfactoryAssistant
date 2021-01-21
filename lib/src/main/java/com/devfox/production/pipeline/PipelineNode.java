package com.devfox.production.pipeline;

import com.devfox.production.Machine;

import java.util.ArrayList;
import java.util.List;

/**
 * {@linkplain PipelineNode} class represents a node in a tree which represents a manufacturing pipeline.
 * The root node of the tree is the final output item and every subsequent node is a manufacturing operation in the pipeline
 * Each {@linkplain PipelineNode} contains a {@link Machine} which produces some output.
 * It can be inferred that the output of one machine feeds into another with the exception of the root node and all leaf nodes.
 * Leaf nodes represent inputs into the pipeline that have no crafting recipe such as miners or storage containers
 */
public class PipelineNode {
    private List<PipelineNode> childNodes = new ArrayList<>();
    private PipelineNode parentNode;
    private Machine machine;

    public PipelineNode(Machine machine){
        this.machine = machine;
    }

    public PipelineNode[] getChildNodes(){
        return childNodes.toArray(new PipelineNode[0]);
    }

    public PipelineNode getParentNode(){
        return parentNode;
    }

    public Machine getMachine(){
        return machine;
    }
    public void setMachine(Machine machine){
        this.machine = machine;
    }

    public void addChildNode(PipelineNode childNode){
        childNodes.add(childNode);
    }

    public void removeChildNode(PipelineNode childNode){
        childNodes.remove(childNode);
    }

    public void setParentNode(PipelineNode parentNode){
        this.parentNode = parentNode;
    }

    public boolean isRootNode(){
        return getParentNode() == null;
    }

    public boolean isLeafNode(){
        return getChildNodes().length == 0;
    }
}

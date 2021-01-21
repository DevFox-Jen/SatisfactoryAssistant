package com.devfox.production.pipeline;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implements an iterator for a {@link Pipeline} using a Breadth-First search
 */
public class BFSPipelineIterator implements Iterator<PipelineNode> {
    private final Queue<PipelineNode> bfsQueue = new LinkedList<>();

    public BFSPipelineIterator(PipelineNode rootNode){
        bfsQueue.add(rootNode);
    }

    @Override
    public boolean hasNext() {
        return !bfsQueue.isEmpty();
    }

    @Override
    public PipelineNode next() {
        PipelineNode nextNode = bfsQueue.poll();
        if(nextNode != null){
            bfsQueue.addAll(Arrays.asList(nextNode.getChildNodes()));
        }
        return nextNode;
    }
}

package com.shaunz.structure.graph.factory;

import com.shaunz.structure.graph.Graph;

public interface GraphFactory<T> {
    static enum GraphType{
        DIRECTED_GRAPH,DIRECTED_WEIGHT_GRAPH,DIRECTED_AROUND_WEIGHT_GRAPH
    }

    Graph<T> build(GraphType graphType);

    GraphType getGraphType(Class clazz);
}

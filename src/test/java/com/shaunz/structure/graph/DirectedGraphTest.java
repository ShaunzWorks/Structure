package com.shaunz.structure.graph;

public class DirectedGraphTest {
    private AbstractDirectedGraph<String> directedGraph = new DirectedWeightGraph<>();

    public void prepareGraph(){
        directedGraph.addVertex("A");
        directedGraph.addVertex("B");
        directedGraph.addVertex("C");
        directedGraph.addVertex("D");
        directedGraph.addVertex("E");

        directedGraph.addEdge("A","B");
        directedGraph.addEdge("B","C");
        directedGraph.addEdge("C","D");
        directedGraph.addEdge("D","C");
        directedGraph.addEdge("D","E");
        directedGraph.addEdge("A","D");
        directedGraph.addEdge("C","E");
        directedGraph.addEdge("E","B");
        directedGraph.addEdge("A","E");
    }
}

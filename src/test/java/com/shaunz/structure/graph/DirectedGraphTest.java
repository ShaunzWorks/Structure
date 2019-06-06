package com.shaunz.structure.graph;

import com.shaunz.structure.graph.vertex.Vertex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DirectedGraphTest {
    private DirectedGraph<String> directedGraph = new DirectedGraph<>();

    @Before
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

    @Test
    public void oppositeDirection(){
        Vertex<String> pA = directedGraph.getVertices().get("A");
        Vertex<String> pB = directedGraph.getVertices().get("B");
        Vertex<String> pC = directedGraph.getVertices().get("C");
        Vertex<String> pD = directedGraph.getVertices().get("D");
        Vertex<String> pE = directedGraph.getVertices().get("E");

        DirectedGraph<String> oppositeDirectionGraph = (DirectedGraph)directedGraph.oppositeDirection();

        Vertex<String> nA = oppositeDirectionGraph.getVertices().get("A");
        Vertex<String> nB = oppositeDirectionGraph.getVertices().get("B");
        Vertex<String> nC = oppositeDirectionGraph.getVertices().get("C");
        Vertex<String> nD = oppositeDirectionGraph.getVertices().get("D");
        Vertex<String> nE = oppositeDirectionGraph.getVertices().get("E");

        Assert.assertEquals(directedGraph.traverse(pA,pE,pB,pC,pD),oppositeDirectionGraph.traverse(nD,nC,nB,nE,nA),0.0);
    }
}

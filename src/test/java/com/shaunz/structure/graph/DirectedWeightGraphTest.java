package com.shaunz.structure.graph;

import com.shaunz.structure.graph.vertex.Vertex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DirectedWeightGraphTest {

    private DirectedWeightGraph<String> directedWeightGraph = new DirectedWeightGraph<>();

    @Before
    public void prepareGraph(){
        directedWeightGraph.addVertex("A");
        directedWeightGraph.addVertex("B");
        directedWeightGraph.addVertex("C");
        directedWeightGraph.addVertex("D");
        directedWeightGraph.addVertex("E");

        directedWeightGraph.addEdge("A","B",5.0);
        directedWeightGraph.addEdge("B","C",4.0);
        directedWeightGraph.addEdge("C","D",8.0);
        directedWeightGraph.addEdge("D","E",6.0);
        directedWeightGraph.addEdge("A","D",5.0);
        directedWeightGraph.addEdge("C","E",2.0);
        directedWeightGraph.addEdge("A","E",7.0);
    }
    @Test
    public void shortestPath(){
        //Stack<String> path = new Stack<>();
        //double weight = directedWeightGraph.getShortestPath("A","C",path);
        //Assert.assertEquals(9,weight,0.0);
    }

    public void traverse(){
        Vertex<String> A = directedWeightGraph.getVertices().get("A");
        Vertex<String> B = directedWeightGraph.getVertices().get("B");
        Vertex<String> C = directedWeightGraph.getVertices().get("C");
        Vertex<String> D = directedWeightGraph.getVertices().get("D");
        Vertex<String> E = directedWeightGraph.getVertices().get("E");
        Assert.assertEquals(9,directedWeightGraph.traverse(A,B,C),0.0);
        Assert.assertEquals(5,directedWeightGraph.traverse(A,D),0.0);
        Assert.assertEquals(13,directedWeightGraph.traverse(A,D,C),0.0);
        Assert.assertEquals(22,directedWeightGraph.traverse(A,E,B,C,D),0.0);
        Assert.assertEquals(Double.POSITIVE_INFINITY,directedWeightGraph.traverse(A,E,D),0.0);
    }

}

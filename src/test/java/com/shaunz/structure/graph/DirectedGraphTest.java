package com.shaunz.structure.graph;

import com.shaunz.structure.graph.vertex.Vertex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DirectedGraphTest {
    private DirectedGraph<String> directedGraph = new DirectedGraph<>();
    private DirectedGraph<String> directedGraph2 = new DirectedGraph<>();

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

        directedGraph.init();



        directedGraph2.addVertex("0");
        directedGraph2.addVertex("1");
        directedGraph2.addVertex("2");
        directedGraph2.addVertex("3");
        directedGraph2.addVertex("4");
        directedGraph2.addVertex("5");
        directedGraph2.addVertex("6");
        directedGraph2.addVertex("7");
        directedGraph2.addVertex("8");
        directedGraph2.addVertex("9");
        directedGraph2.addVertex("10");
        directedGraph2.addVertex("11");
        directedGraph2.addVertex("12");

        directedGraph2.addEdge("0","1");
        directedGraph2.addEdge("0","5");
        directedGraph2.addEdge("2","0");
        directedGraph2.addEdge("2","3");
        directedGraph2.addEdge("3","2");
        directedGraph2.addEdge("3","5");
        directedGraph2.addEdge("4","2");
        directedGraph2.addEdge("4","3");
        directedGraph2.addEdge("5","4");
        directedGraph2.addEdge("6","0");
        directedGraph2.addEdge("6","4");
        directedGraph2.addEdge("6","8");
        directedGraph2.addEdge("6","9");
        directedGraph2.addEdge("7","6");
        directedGraph2.addEdge("7","9");
        directedGraph2.addEdge("8","6");
        directedGraph2.addEdge("9","10");
        directedGraph2.addEdge("9","11");
        directedGraph2.addEdge("10","12");
        directedGraph2.addEdge("11","4");
        directedGraph2.addEdge("11","12");
        directedGraph2.addEdge("12","9");

        directedGraph2.init();
    }

    @Test
    public void oppositeDirection(){
        Vertex<String> pA = directedGraph.getVertices().get("A");
        Vertex<String> pB = directedGraph.getVertices().get("B");
        Vertex<String> pC = directedGraph.getVertices().get("C");
        Vertex<String> pD = directedGraph.getVertices().get("D");
        Vertex<String> pE = directedGraph.getVertices().get("E");


        DirectedGraph<String> oppositeDirectionGraph = (DirectedGraph)directedGraph.getOppositeDirection();

        Vertex<String> nA = oppositeDirectionGraph.getVertices().get("A");
        Vertex<String> nB = oppositeDirectionGraph.getVertices().get("B");
        Vertex<String> nC = oppositeDirectionGraph.getVertices().get("C");
        Vertex<String> nD = oppositeDirectionGraph.getVertices().get("D");
        Vertex<String> nE = oppositeDirectionGraph.getVertices().get("E");

        Assert.assertEquals(directedGraph.traverse(pA,pE,pB,pC,pD),oppositeDirectionGraph.traverse(nD,nC,nB,nE,nA),0.0);
    }

    @Test
    public void getStrongComponents(){
        Map<String,Integer> result = directedGraph2.getStrongComponents();
        result.entrySet().forEach(r -> {
            System.out.println(r.getKey() + " : " + r.getValue());
        });
    }

    @Test
    public void getAllPath(){
        List<Stack<String>> paths = directedGraph.getAllPath("A","C");
        paths.stream().forEach(path -> {
            StringBuffer pathString = new StringBuffer();
            while (!path.isEmpty()){
                pathString.append(path.pop());
            }
            System.out.println(pathString.toString());
        });

        paths = directedGraph2.getAllPath("7","5");
        paths.stream().forEach(path -> {
            StringBuffer pathString = new StringBuffer();
            while (!path.isEmpty()){
                pathString.append(path.pop());
            }
            System.out.println(pathString.toString());
        });

        paths = directedGraph.getAllPath("B","B");
        paths.stream().forEach(path -> {
            StringBuffer pathString = new StringBuffer();
            while (!path.isEmpty()){
                pathString.append(path.pop());
            }
            System.out.println(pathString.toString());
        });
    }
}

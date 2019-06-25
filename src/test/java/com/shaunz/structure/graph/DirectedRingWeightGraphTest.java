package com.shaunz.structure.graph;

import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

public class DirectedRingWeightGraphTest {
    private DirectedRingWeightGraph<String> directedRingWeightGraph = new DirectedRingWeightGraph<>();
    private DirectedRingWeightGraph<String> directedRingWeightGraph2 = new DirectedRingWeightGraph<>();

    @Before
    public void prepare(){
        directedRingWeightGraph.addVertex("A");
        directedRingWeightGraph.addVertex("B");
        directedRingWeightGraph.addVertex("C");
        directedRingWeightGraph.addVertex("D");
        directedRingWeightGraph.addVertex("E");

        directedRingWeightGraph.addEdge("A","B",5.0);
        directedRingWeightGraph.addEdge("B","C",4.0);
        directedRingWeightGraph.addEdge("C","D",8.0);
        directedRingWeightGraph.addEdge("D","C",8.0);
        directedRingWeightGraph.addEdge("D","E",6.0);
        directedRingWeightGraph.addEdge("A","D",5.0);
        directedRingWeightGraph.addEdge("C","E",2.0);
        directedRingWeightGraph.addEdge("E","B",3.0);
        directedRingWeightGraph.addEdge("A","E",7.0);
    }

    @Test
    public void getShortestPath(){
        Stack<String> path = new Stack<>();
        StringBuffer pathStr = new StringBuffer();
        Double distance = directedRingWeightGraph.getShortestPath("A","C",path);
        if(path != null){
            while (!path.isEmpty()){
                pathStr.append(path.pop());
                if(!path.isEmpty()){
                    pathStr.append(" -> ");
                }
            }
        }
        System.out.println("Shortest route from A to C: " + pathStr.toString() +" with total distance: "+distance);

        path = new Stack<>();
        pathStr = new StringBuffer();
        distance = directedRingWeightGraph.getShortestPath("B","B",path);
        if(path != null){
            while (!path.isEmpty()){
                pathStr.append(path.pop());
                if(!path.isEmpty()){
                    pathStr.append(" -> ");
                }
            }
        }
        System.out.println("Shortest route from B to B: " + pathStr.toString() +"("+distance+")");
    }
}

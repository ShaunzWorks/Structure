package com.shaunz.structure.graph;

import com.shaunz.structure.graph.vertex.Vertex;

import java.util.*;

public class DirectedAroundWeightGraph<T> extends AbstractDirectedGraph<T> {

    public DirectedAroundWeightGraph() {
        vertices = new LinkedHashMap<T, Vertex<T>>();
    }

    @Override
    public boolean addEdge(T begin, T end, Double edgeWeight) {
        boolean result = false;
        Vertex<T> beginVertex = vertices.get(begin);
        Vertex<T> endVertex = vertices.get(end);

        if(beginVertex != null && endVertex != null);
        result = beginVertex.connect(endVertex,edgeWeight);
        if(result)
            edgeCount.getAndIncrement();
        return result;
    }

    @Override
    @Deprecated
    public boolean addEdge(T begin, T end) {
        return false;
    }

    /**
     * Get the shortest path from begin to end
     *
     * @param begin
     * @param end
     * @param path
     * @return
     */
    @Override
    public Double getShortestPath(T begin, T end, Stack<T> path) {
        resetVertices();
        Queue<Vertex<T>> vertexQueue = new LinkedList<Vertex<T>>();
        Vertex<T> beginVertex = vertices.get(begin);
        Vertex<T> endVertex = vertices.get(end);

        beginVertex.visit();
        vertexQueue.offer(beginVertex);
        while (!vertexQueue.isEmpty()){
            Vertex<T> frontVertex = vertexQueue.poll();
            Iterator<Vertex<T>> neighbors = frontVertex.getNeighborIterator();
            while (neighbors.hasNext()){
                Vertex<T> neighbor = neighbors.next();
                if(neighbor.isVisited()){
                    double toNeighborWeight = frontVertex.getCost()+frontVertex.getWeigh2Neighbor(neighbor);
                    double currentMinWeight = neighbor.getCost();
                    if(toNeighborWeight < currentMinWeight){
                        neighbor.setPredecessor(frontVertex);
                        neighbor.setCost(toNeighborWeight);
                    }
                } else {
                    neighbor.setPredecessor(frontVertex);
                    neighbor.setCost(frontVertex.getCost() + frontVertex.getWeigh2Neighbor(neighbor));
                }

                vertexQueue.offer(neighbor);
                neighbor.visit();
            }
        }

        path.push(endVertex.getLabel());

        Vertex<T> vertex = endVertex;
        while (vertex.hasPredecessor()){
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        }

        return endVertex.getCost();
    }

}
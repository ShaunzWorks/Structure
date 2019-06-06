package com.shaunz.structure.graph;

import com.shaunz.structure.graph.Factory.GraphFactory;
import com.shaunz.structure.graph.Factory.GraphFactoryImpl;
import com.shaunz.structure.graph.edge.EdgeImpl;
import com.shaunz.structure.graph.vertex.Vertex;
import com.shaunz.structure.graph.vertex.VertexImpl;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractDirectedGraph<T> implements Graph<T>, Serializable {
    private static final long serialVersionUID = -1190160436962485575L;

    protected Map<T, Vertex<T>> vertices;
    protected AtomicInteger edgeCount = new AtomicInteger(0);

    public void addVertex(T vertxLabel){
        vertices.put(vertxLabel, new VertexImpl<>(vertxLabel));
    }

    public abstract boolean addEdge(T begin, T end, Double edgeWeight);

    public abstract boolean addEdge(T begin, T end);

    /**
     * Get the shortest path from begin to end
     *
     * @param begin
     * @param end
     * @param path
     * @return
     */
    public abstract Double getShortestPath(T begin, T end, Stack<T> path);

    public void setVertices(Map<T, Vertex<T>> vertices) {
        this.vertices = vertices;
        vertices.forEach((t,vertex) -> {
            edgeCount.getAndAdd(vertex.getNeighborsCnt());
        });
    }

    /**
     * Get all the path from begin to end
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public List<Stack<T>> getAllPath(T begin, T end) {
        resetVertices();
        Queue<Vertex<T>> vertexQueue = new LinkedList<>();
        List<Stack<T>> result = new ArrayList<>();
        Vertex<T> beginVertex = vertices.get(begin);
        Vertex<T> endVertex = vertices.get(end);

        beginVertex.visit();
        return result;
    }

    /**
     * BFS
     *
     * @param origin vertex which start
     * @return
     */
    public Queue<T> getBreadthFirstTraversal(T origin) {
        resetVertices();
        Queue<Vertex<T>> vertexQueue = new LinkedList<Vertex<T>>();
        Queue<T> traversalOrder = new LinkedList<T>();
        Vertex<T> originVertex = this.vertices.get(origin);
        originVertex.visit();
        traversalOrder.offer(originVertex.getLabel());
        vertexQueue.offer(originVertex);

        while (!vertexQueue.isEmpty()){
            Vertex<T> frontVertex = vertexQueue.poll();
            Iterator<Vertex<T>> neighbors = frontVertex.getNeighborIterator();
            neighbors.forEachRemaining(neighbor -> {
                if(!neighbor.isVisited()){
                    neighbor.visit();
                    traversalOrder.offer(neighbor.getLabel());
                    vertexQueue.offer(neighbor);
                }
            });
        }
        return traversalOrder;
    }

    /**
     * DFS
     *
     * @param origin
     * @return
     */
    public Queue<T> getDepthFirstTraversal(T origin) {
        resetVertices();
        LinkedList<Vertex<T>> vertexStack = new LinkedList<>();
        Queue<T> traversalOrder = new LinkedList<>();

        Vertex<T> originVertex = vertices.get(origin);
        originVertex.visit();
        vertexStack.push(originVertex);
        traversalOrder.offer(originVertex.getLabel());

        while (!vertexStack.isEmpty()){
            Vertex<T> topVertex = vertexStack.peek();
            Vertex<T> nextNeighbor = topVertex.getUnvisitedNeighbor();

            if(nextNeighbor != null){
                nextNeighbor.visit();
                vertexStack.push(nextNeighbor);
                traversalOrder.offer(nextNeighbor.getLabel());
            } else {
                vertexStack.pop();
            }
        }
        return traversalOrder;
    }

    public Stack<T> getTopologySort() {
        resetVertices();

        Stack<T> vertexStack = new Stack<>();
        int numberofVertices = vertices.size();

        for(int counter = 1; counter <= numberofVertices; counter ++){
            Vertex<T> nextVertex = getNextTopologyOrder();
            if(nextVertex != null){
                nextVertex.visit();
                vertexStack.push(nextVertex.getLabel());
            }
        }
        return vertexStack;
    }

    /**
     * Opposite direction for this graph
     *
     * @return
     */
    public Graph<T> oppositeDirection() {
        Map<T,Vertex<T>> oppositedVertices = new LinkedHashMap<>();

        vertices.keySet().stream().forEach(t ->{
            Vertex<T> frontVertex = vertices.get(t);
            frontVertex.getNeighborIterator().forEachRemaining(backVertex -> {
                Vertex<T> newFrontVertex;
                Double weight = frontVertex.getWeigh2Neighbor(backVertex);
                if(oppositedVertices.containsKey(backVertex.getLabel())){
                    newFrontVertex = oppositedVertices.get(backVertex.getLabel());
                    newFrontVertex.addEdge(new EdgeImpl<>(frontVertex,weight));
                } else {
                    newFrontVertex = new VertexImpl<>(backVertex);
                    newFrontVertex.addEdge(new EdgeImpl<>(frontVertex,weight));
                    oppositedVertices.put(newFrontVertex.getLabel(),newFrontVertex);
                }
            });
        });

        Graph<T> oppositeGraph = createNewGraph(oppositedVertices);
        return oppositeGraph;
    }

    protected Graph<T> createNewGraph(Map<T,Vertex<T>> vertices){
        GraphFactory graphFactory = GraphFactoryImpl.getInstance();
        graphFactory.build(graphFactory.getGraphType(this.getClass()));
        Graph<T> graph = new DirectedAroundWeightGraph<>();
        graph.setVertices(vertices);
        return graph;
    }

    public Map<Vertex<T>, List<Vertex<T>>> getStrongComponents() {
        return null;
    }

    public Double traverse(Vertex<T>... vertexes) {
        Double result = 0.0;
        Vertex<T> vertex;
        if (vertexes.length > 1) {
            for (int i=1,size=vertexes.length;i<size;i++){
                vertex = vertexes[i];
                double weight = vertexes[i-1].getWeigh2Neighbor(vertexes[i]);
                result += weight;
            }
        }
        return result;
    }

    /**
     * Reset some parameters in vertex list
     */
    public void resetVertices() {
        this.vertices.keySet().stream().forEach(key -> {
            Vertex<T> vertex = this.vertices.get(key);
            vertex.unVisit();
            vertex.setCost(0.0);
            vertex.setPredecessor(null);
        });
    }

    public Map<T, Vertex<T>> getVertices() {
        return vertices;
    }

    public AtomicInteger getEdgeCount() {
        return edgeCount;
    }

    public int getVertexCount(){
        return vertices == null?0:vertices.size();
    }

    private Vertex<T> getNextTopologyOrder(){
        Vertex<T> nextVertex = null;
        Iterator<Vertex<T>> iterator = vertices.values().iterator();
        boolean found = false;
        while (!found && iterator.hasNext()){
            nextVertex = iterator.next();

            if(!nextVertex.isVisited() && nextVertex.getUnvisitedNeighbor() == null){
                found = true;
            }
        }
        return nextVertex;
    }
}
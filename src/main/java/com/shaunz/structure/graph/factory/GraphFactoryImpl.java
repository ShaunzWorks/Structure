package com.shaunz.structure.graph.factory;

import com.shaunz.structure.graph.Graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GraphFactoryImpl<T> implements  GraphFactory<T>{
    private static GraphFactoryImpl ourInstance = new GraphFactoryImpl();
    private static Map<GraphType,String> graphTypes = new HashMap<>();

    public static GraphFactoryImpl getInstance() {
        return ourInstance;
    }

    private GraphFactoryImpl() {
        graphTypes.put(GraphType.DIRECTED_GRAPH,"com.shaunz.structure.graph.DirectedGraph");
        graphTypes.put(GraphType.DIRECTED_WEIGHT_GRAPH,"com.shaunz.structure.graph.DirectedWeightGraph");
        graphTypes.put(GraphType.DIRECTED_AROUND_WEIGHT_GRAPH,"com.shaunz.structure.graph.DirectedAroundWeightGraph");
    }

    public Graph<T> build(GraphType graphType) {
        String className = graphTypes.get(graphType);
        Object newInstance = null;
        try {
            newInstance = getClass(className).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (Graph<T>)newInstance;
    }

    public GraphType getGraphType(Class clazz) {
        GraphType graphType = null;
        String clazzName = clazz.getName();
        Iterator<GraphType> graphTypeKeys = graphTypes.keySet().iterator();
        while (graphTypeKeys.hasNext()){
            graphType = graphTypeKeys.next();
            if(graphTypes.get(graphTypeKeys.next()).equals(clazzName)){
                break;
            }
        }
        return graphType;
    }

    private Class getClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }
}

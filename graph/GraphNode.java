package graph;

import java.util.HashMap;
import java.util.Map;

public class GraphNode {
    private String value;
    private Map<GraphNode, Integer> adjacent;

    public GraphNode(String value) {
        this.value = value;
        this.adjacent = new HashMap<>();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addEdge(GraphNode to, int weight) {
        adjacent.put(to, weight);
    }

    public void removeEdge(GraphNode to) {
        adjacent.remove(to);
    }

    public Map<GraphNode, Integer> getAdjacentNodes() {
        return adjacent;
    }

    public void printNeighbors() {
        System.out.println("All edges from <" + value + "> are:");
        if (adjacent.isEmpty()) {
            System.out.println("- There is no edge from <" + value + ">.");
        } else {
            for (GraphNode neighbor : adjacent.keySet()) {
                System.out.println("- Edge to <" + neighbor.getValue() + ">, with weight " + adjacent.get(neighbor) + ".");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof GraphNode)) return false;
        GraphNode other = (GraphNode) o;
        return this.value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}


package graph;

import java.util.*;

public class DiGraphImpl implements DiGraph {

    private Map<String, GraphNode> nodes;

    public DiGraphImpl() {
        nodes = new HashMap<>();
    }

    @Override
    public Boolean addNode(GraphNode node) {
        if (nodes.containsKey((String) node.getValue())) return false;
        nodes.put((String) node.getValue(), node);
        return true;
    }

    @Override
    public Boolean removeNode(GraphNode node) {
        if (!nodes.containsKey((String) node.getValue())) return false;
        nodes.remove((String) node.getValue());
        for (GraphNode n : nodes.values()) {
            n.removeEdge(node);
        }
        return true;
    }

    @Override
    public Boolean setNodeValue(GraphNode node, String newNodeValue) {
        if (!nodes.containsKey((String) node.getValue())) return false;
        node.setValue(newNodeValue);
        return true;
    }

    @Override
    public String getNodeValue(GraphNode node) {
        return (String) node.getValue();
    }

    @Override
    public Boolean addEdge(GraphNode fromNode, GraphNode toNode, Integer weight) {
        if (!nodes.containsValue(fromNode) || !nodes.containsValue(toNode)) return false;
        fromNode.addEdge(toNode, weight);
        return true;
    }

    @Override
    public Boolean removeEdge(GraphNode fromNode, GraphNode toNode) {
        if (!fromNode.getAdjacentNodes().containsKey(toNode)) return false;
        fromNode.removeEdge(toNode);
        return true;
    }

    @Override
    public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
        if (!fromNode.getAdjacentNodes().containsKey(toNode)) return false;
        fromNode.getAdjacentNodes().put(toNode, newWeight);
        return true;
    }

    @Override
    public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
        return fromNode.getAdjacentNodes().getOrDefault(toNode, -1);
    }

    @Override
    public List<GraphNode> getAdjacentNodes(GraphNode node) {
        return new ArrayList<>(node.getAdjacentNodes().keySet());
    }

    @Override
    public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
        return fromNode.getAdjacentNodes().containsKey(toNode);
    }

    @Override
    public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {
        Set<GraphNode> visited = new HashSet<>();
        Queue<GraphNode> queue = new LinkedList<>();
        queue.add(fromNode);

        while (!queue.isEmpty()) {
            GraphNode current = queue.poll();
            if (current.equals(toNode)) return true;
            visited.add(current);
            for (GraphNode neighbor : current.getAdjacentNodes().keySet()) {
                if (!visited.contains(neighbor)) queue.add(neighbor);
            }
        }
        return false;
    }

    @Override
    public Boolean hasCycles() {
        Set<GraphNode> visited = new HashSet<>();
        Set<GraphNode> recursionStack = new HashSet<>();

        for (GraphNode node : nodes.values()) {
            if (detectCycle(node, visited, recursionStack)) return true;
        }
        return false;
    }

    private boolean detectCycle(GraphNode node, Set<GraphNode> visited, Set<GraphNode> stack) {
        if (stack.contains(node)) return true;
        if (visited.contains(node)) return false;

        visited.add(node);
        stack.add(node);

        for (GraphNode neighbor : node.getAdjacentNodes().keySet()) {
            if (detectCycle(neighbor, visited, stack)) return true;
        }

        stack.remove(node);
        return false;
    }

    @Override
    public List<GraphNode> getNodes() {
        return new ArrayList<>(nodes.values());
    }

    @Override
    public GraphNode getNode(String nodeValue) {
        return nodes.get(nodeValue);
    }

    @Override
    public int fewestHops(GraphNode fromNode, GraphNode toNode) {
        Map<GraphNode, Integer> dist = new HashMap<>();
        Queue<GraphNode> queue = new LinkedList<>();
        queue.add(fromNode);
        dist.put(fromNode, 0);

        while (!queue.isEmpty()) {
            GraphNode curr = queue.poll();
            if (curr.equals(toNode)) return dist.get(curr);
            for (GraphNode neighbor : curr.getAdjacentNodes().keySet()) {
                if (!dist.containsKey(neighbor)) {
                    dist.put(neighbor, dist.get(curr) + 1);
                    queue.add(neighbor);
                }
            }
        }
        return -1;
    }

    @Override
    public List<GraphNode> getFewestHopsPath(GraphNode fromNode, GraphNode toNode) {
        Map<GraphNode, GraphNode> prev = new HashMap<>();
        Set<GraphNode> visited = new HashSet<>();
        Queue<GraphNode> queue = new LinkedList<>();
        queue.add(fromNode);
        visited.add(fromNode);

        while (!queue.isEmpty()) {
            GraphNode curr = queue.poll();
            if (curr.equals(toNode)) break;
            for (GraphNode neighbor : curr.getAdjacentNodes().keySet()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    prev.put(neighbor, curr);
                    queue.add(neighbor);
                }
            }
        }

        if (!prev.containsKey(toNode)) return null;

        LinkedList<GraphNode> path = new LinkedList<>();
        for (GraphNode at = toNode; at != null; at = prev.get(at)) {
            path.addFirst(at);
            if (at.equals(fromNode)) break;
        }
        return path;
    }

    @Override
    public int shortestPath(GraphNode fromNode, GraphNode toNode) {
        Map<GraphNode, Integer> dist = new HashMap<>();
        PriorityQueue<PathNode> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.cost));
        pq.add(new PathNode(fromNode, 0));
        dist.put(fromNode, 0);

        while (!pq.isEmpty()) {
            PathNode current = pq.poll();
            if (current.node.equals(toNode)) return current.cost;

            for (Map.Entry<GraphNode, Integer> entry : current.node.getAdjacentNodes().entrySet()) {
                GraphNode neighbor = entry.getKey();
                int weight = entry.getValue();
                int newDist = current.cost + weight;
                if (newDist < dist.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    dist.put(neighbor, newDist);
                    pq.add(new PathNode(neighbor, newDist));
                }
            }
        }
        return -1;
    }

    @Override
    public List<GraphNode> getShortestPath(GraphNode fromNode, GraphNode toNode) {
        Map<GraphNode, Integer> dist = new HashMap<>();
        Map<GraphNode, GraphNode> prev = new HashMap<>();
        PriorityQueue<PathNode> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.cost));
        pq.add(new PathNode(fromNode, 0));
        dist.put(fromNode, 0);

        while (!pq.isEmpty()) {
            PathNode current = pq.poll();
            if (current.node.equals(toNode)) break;

            for (Map.Entry<GraphNode, Integer> entry : current.node.getAdjacentNodes().entrySet()) {
                GraphNode neighbor = entry.getKey();
                int weight = entry.getValue();
                int newDist = current.cost + weight;
                if (newDist < dist.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    dist.put(neighbor, newDist);
                    prev.put(neighbor, current.node);
                    pq.add(new PathNode(neighbor, newDist));
                }
            }
        }

        if (!prev.containsKey(toNode)) return null;

        LinkedList<GraphNode> path = new LinkedList<>();
        for (GraphNode at = toNode; at != null; at = prev.get(at)) {
            path.addFirst(at);
            if (at.equals(fromNode)) break;
        }
        return path;
    }

    private static class PathNode {
        GraphNode node;
        int cost;

        PathNode(GraphNode node, int cost) {
            this.node = node;
            this.cost = cost;
        }
    }
}

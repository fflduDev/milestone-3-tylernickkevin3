package graph;

import java.util.ArrayList;
import java.util.List;

public class GraphTester {

    private static DiGraph graph;

    public static void printNodesValues(List<GraphNode> nodeList) {
        System.out.println("Nodes are:");
        if (nodeList == null) System.out.println("---- no nodes ----");
        else nodeList.forEach((n) -> System.out.println(n.getValue()));
    }

    public static void printPath(List<GraphNode> path) {
        System.out.println("Path is:");
        if (path == null) System.out.println("---- no path found ----");
        else {
            List<String> pathStr = new ArrayList<>();
            path.forEach((node) -> pathStr.add((String) node.getValue()));
            System.out.println(String.join(" -> ", pathStr));
        }
    }

    public static void main(String[] args) {
        graph = new DiGraphImpl();

        graph.addNode(new GraphNode("A"));
        graph.addNode(new GraphNode("B"));
        graph.addNode(new GraphNode("C"));
        graph.addNode(new GraphNode("D"));
        graph.addNode(new GraphNode("E"));
        graph.addNode(new GraphNode("F"));
        graph.addNode(new GraphNode("G"));
        graph.addNode(new GraphNode("H"));

        graph.addEdge(graph.getNode("A"), graph.getNode("B"), 5);
        graph.addEdge(graph.getNode("B"), graph.getNode("C"), 5);
        graph.addEdge(graph.getNode("C"), graph.getNode("D"), 1);
        graph.addEdge(graph.getNode("E"), graph.getNode("F"), 1);
        graph.addEdge(graph.getNode("F"), graph.getNode("A"), 1);
        graph.addEdge(graph.getNode("C"), graph.getNode("F"), 2);
        graph.addEdge(graph.getNode("D"), graph.getNode("B"), 15);
        graph.addEdge(graph.getNode("G"), graph.getNode("C"), 5);
        graph.addEdge(graph.getNode("G"), graph.getNode("E"), 8);

        printNodesValues(graph.getNodes());
        graph.getNodes().forEach(GraphNode::printNeighbors);

        System.out.print("F is reachable to E: ");
        System.out.println(graph.nodeIsReachable(graph.getNode("F"), graph.getNode("E")));

        System.out.print("F is reachable to D: ");
        System.out.println(graph.nodeIsReachable(graph.getNode("F"), graph.getNode("D")));

        System.out.print("Graph has cycles: ");
        System.out.println(graph.hasCycles());

        System.out.println("Fewest hop from G to B is: " + graph.fewestHops(graph.getNode("G"), graph.getNode("B")));
        printPath(graph.getFewestHopsPath(graph.getNode("G"), graph.getNode("B")));

        System.out.println("Shortest from G to B is: " + graph.shortestPath(graph.getNode("G"), graph.getNode("B")));
        printPath(graph.getShortestPath(graph.getNode("G"), graph.getNode("B")));
    }
}


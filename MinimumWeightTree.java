package Project4;
import java.util.*;

/**
 * 4/29/2017.
 */
public class MinimumWeightTree {
    ArrayList<Edge> MSTEdges = new ArrayList<>();
    Graph oldGraph;
    public MinimumWeightTree(Graph g) {
        PriorityQueue<Edge> orderedEdges = new PriorityQueue<>();
        for (Edge edge : g.edges) {
            orderedEdges.add(edge);
        }

        Graph S = new Graph(g.nodes);
        while (!orderedEdges.isEmpty()) {
            Edge testEdge = orderedEdges.poll();
            if (!isConnected(S,testEdge)){
                S.edges.add(testEdge);
            }
        }

        for (Edge edge : S.edges){
            MSTEdges.add(edge);
            System.out.println(edge);
        }

    }


    boolean isConnected(Graph S,Edge testEdge) {
        //      System.out.println("testing");
        try {
            S.DijkstraAlgorithm(testEdge.start, testEdge.end);
        } catch (NullPointerException e) {
            //System.out.println("Not connected");
            return false;
        }
        //System.out.println("Connected");
        return true;
    }






}
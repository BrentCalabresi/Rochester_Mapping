package Project4;

import java.util.ArrayList;

/**
 * @author BAC on 4/12/2017.
 */
public class Node {
    String data;//intersection name
    double longitude,latitude;
    ArrayList<Edge> edges;

    public Node(String data, double longitude, double latitude){
       this.data = data;
       this.latitude = latitude;
       this.longitude = longitude;
       edges = new ArrayList<Edge>();
    }
    public Node(){
        edges = new ArrayList();
    }

    public Node(String data, double longitude, double latitude, ArrayList e){
        this.data = data;
        edges = e;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    Edge isConnected(Node n){
        for (Edge e: this.edges){
            for (Edge e2: n.edges){
                if (e.equals(e2))
                    return e;
            }
        }
        return null;
    }
    Edge findCheapestEdge(){
        double min = this.edges.get(0).weight;
        Edge minEdge=null;
        for(Edge e: edges){
            if (e.weight < min){
                min = e.weight;
                minEdge = e;
            }
        }
        return minEdge;
    }
    boolean equals(Node n){
        if (this.data.equals(n.data))
            return true;
        return false;
    }


    public String toString(){
        return data+": Lat: "+latitude+" Lon: "+longitude+"\n";
    }
}

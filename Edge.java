package Project4;

/**
 * @author BAC on 4/12/2017.
 */
public class Edge {
    String name;
    Node start,end;
    double weight;

    public Edge(String name,Node start, Node end){
        this.start = start;
        this.end = end;
        this.name = name;
        weight = Math.sqrt((Math.pow((start.latitude - end.latitude),2) + Math.pow((start.longitude - end.longitude), 2)));
    }

    public Edge(){}

    boolean equals(Edge e){
        if (this.name.equals(e.name))
            return true;
        return false;
    }

    @Override
    public String toString(){
        return name+": Start: "+start+", End: "+end+"\n";
    }
}

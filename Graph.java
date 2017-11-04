package Project4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author BAC on 4/12/2017.
 *
 */
public class Graph {

    File file;
    Hashtable<String,Integer> intersections = new Hashtable<>();//support quick lookup of intersections(nodes) by name
    ArrayList<Node> nodes = new ArrayList<>();
    LinkedList<Node>[] adjList;
    double maxLong = -Double.MAX_VALUE, minLong = Double.MAX_VALUE;
    double maxLat = -Double.MAX_VALUE, minLat = Double.MAX_VALUE;
    double[] distances; //= new double[adjList.length];
    HashSet<Node> visited= new HashSet<>(),unvisited = new HashSet<>();
    HashMap<Node,Node> predecessors = new HashMap<>();
    ArrayList<Node> dijkstra = new ArrayList<>();//all nodes used in Dijkstra's algorithm
    ArrayList<Edge> edges = new ArrayList<>();//set of all edges

    public Graph(ArrayList nodes){
        this.nodes = nodes;
    }
    public Graph(String filepath) throws FileNotFoundException {
        file = new File(filepath);
        Scanner fileScanner = new Scanner(file);
        Scanner lineScanner;
        while (fileScanner.hasNext()){//while more lines exist
            Node newNode = new Node();
            lineScanner = new Scanner(fileScanner.nextLine());
            while (lineScanner.hasNext()){//parse each line individually
                if (lineScanner.next().equals("i")){//intersection
                    newNode.data = lineScanner.next();
                    newNode.latitude = lineScanner.nextDouble();
                    newNode.longitude = lineScanner.nextDouble();
                    nodes.add(newNode);
                    unvisited.add(newNode);
                    if (newNode.latitude > maxLat)
                        maxLat = newNode.latitude;
                    if (newNode.longitude > maxLong)
                        maxLong = newNode.longitude;
                    if (newNode.latitude < minLat)
                        minLat = newNode.latitude;
                    if (newNode.longitude < minLong)
                        minLong = newNode.longitude;
                }
                else
                    break;//don't need to keep reading after intersections are finished
            }

        }
        adjList = new LinkedList[nodes.size()];
        for (Node n: nodes){//transfer from arraylist to adjacency list
            adjList[nodes.indexOf(n)]= new LinkedList<>();
            adjList[nodes.indexOf(n)].add(n);

            try {
                intersections.put(n.data, nodes.indexOf(n));//we now have the name of the node linked to an index of the array
            }catch (NullPointerException ex){}              //nodes can now be accessed by value (array) or by name (hashtable)
        }                                                   //to access by #: directly access adjlist[#]
                                                            //to access by name: array[intersections.get("Name")]

        fileScanner = new Scanner(file);
        while (fileScanner.hasNext()){
            lineScanner = new Scanner(fileScanner.nextLine());
           while (lineScanner.hasNext()){
               if (lineScanner.next().equals("r")){// make sure it's a road
                   String name = lineScanner.next();
                   String node1Name = lineScanner.next();
                   String node2Name = lineScanner.next();
                   Edge newEdge = new Edge(name,adjList[intersections.get(node1Name)].getFirst(),adjList[intersections.get(node2Name)].getFirst());
                   edges.add(newEdge);
                   for (int i =0;i<adjList.length;i++){
                       if (adjList[i].getFirst().data.equals(node1Name) || adjList[i].getFirst().data.equals(node2Name)){//
                           if (!adjList[i].getFirst().edges.contains(newEdge))
                               adjList[i].getFirst().edges.add(newEdge);
                       }
                   }
               }
            }
        }
        for (int i=0;i<adjList.length;i++){
            for (Edge e: adjList[i].getFirst().edges){

                if (!e.start.equals(adjList[i].getFirst()))
                    adjList[i].add(e.start);
                if (!e.end.equals(adjList[i].getFirst()))
                    adjList[i].add(e.end);
            }
        }
        distances = new double[adjList.length];
    }

    Node getLowestNode(){//returns next node to go to
        double min = Double.MAX_VALUE;
        int indexOfLowest=-1;

        for(int i=0;i<distances.length;i++){
            if (distances[i] <= min && !visited.contains(adjList[i].getFirst())){
                min = distances[i];
                indexOfLowest = i;
            }
        }
        return adjList[indexOfLowest].getFirst();
    }

    LinkedList<Node> makePath(Node end){
        LinkedList<Node> path = new LinkedList<>();
        Node step = end;
        if (predecessors.get(step) == null)
            return null;
        path.add(step);
        while (predecessors.get(step) != null){
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        LinkedList<Node> temp = path;
        for (Node n :temp)
            dijkstra.add(n);
        return path;
    }

    void updateNeighbors(Node node){
        double newDistance = 0;
        Node current = new Node();
        for (Edge e: node.edges){
            if (e.start.equals(node))
                current = e.end;
            else
                current = e.start;

            newDistance = e.weight+distances[intersections.get(node.data)];
            if (newDistance < distances[intersections.get(current.data)]){
                distances[intersections.get(current.data)] = newDistance;
                predecessors.put(current,node);
            }

        }
    }
    LinkedList<Node> DijkstraAlgorithm(Node one, Node two){
        for (int i=0;i<distances.length;i++)
            distances[i] = Double.MAX_VALUE;
        distances[intersections.get(one.data)]=0;
        Node evalNode;

        while (!unvisited.isEmpty()){
            evalNode = getLowestNode();
            unvisited.remove(evalNode);
            visited.add(evalNode);
            updateNeighbors(evalNode);
        }
        return makePath(two);
    }

    ArrayList<Edge> PrimAlgorithm(){
        ArrayList<Node> primNodes = new ArrayList<>();
        ArrayList<Edge> primEdges = new ArrayList<>();
        Node start = adjList[0].getFirst();
        primNodes.add(start);
        int counter=0;
        while(primEdges.size() < 124){
            Edge min = new Edge();
            min.weight = Double.MAX_VALUE;
            for(int i=0;i<primNodes.size();i++){
                for (int j=0;j<primNodes.get(i).edges.size();j++){
                    if((primNodes.get(i).edges.get(j).weight < min.weight) && (primNodes.contains(primNodes.get(i).edges.get(j).start))
                            ^ (primNodes.contains(primNodes.get(i).edges.get(j).end))){
                        min = primNodes.get(i).edges.get(j);
                    }
                }
            }
            primEdges.add(min);
            counter++;
            if (primNodes.contains(min.end) && !primNodes.contains(min.start)){
                primNodes.add(min.start);
            }
            else if (primNodes.contains(min.start) && !primNodes.contains(min.end)){
                primNodes.add(min.end);
            }
        }

//        System.out.println("PrimNodes: "+primNodes.size());
//        System.out.println("PrimEdges: "+primEdges.size());
//        System.out.println(counter);

        return primEdges;
    }
}

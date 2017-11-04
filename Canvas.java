package Project4;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Canvas extends JComponent{
    Graph myGraph;
    int height, width;
    static String intersection1 = "GILBERT-LONG";//input starting and ending locations here
    static String intersection2 = "HOPEMAN";

    public Canvas(String str) throws FileNotFoundException {
        super();
        myGraph = new Graph(str);

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(2));
        height = getHeight(); //912 (longitude coordinates)
        width = getWidth(); //968 (latitude coordinates)

        for (int i = 0; i < myGraph.adjList.length; i++) {
            for (int j = 1; j < myGraph.adjList[i].size(); j++) {
                g2d.drawLine(applyLatScaling(myGraph.adjList[i].getFirst().latitude), applyLongScaling(myGraph.adjList[i].getFirst().longitude),
                        applyLatScaling(myGraph.adjList[i].get(j).latitude), applyLongScaling(myGraph.adjList[i].get(j).longitude));
            }
        }
        ArrayList<Edge> primEdges = myGraph.PrimAlgorithm();
        System.out.println("Minimum Weight Spanning Tree: "+"\n"+primEdges);
        g2d.setColor(Color.MAGENTA);
        try{
        for (int i = 0; i < primEdges.size(); i++) {
            g2d.drawLine(applyLatScaling(primEdges.get(i).start.latitude), applyLongScaling(primEdges.get(i).start.longitude),
                    applyLatScaling(primEdges.get(i).end.latitude), applyLongScaling(primEdges.get(i).end.longitude));
        }}catch (NullPointerException ex){}

        System.out.println("Dijkstra's Path: "+"\n"+myGraph.DijkstraAlgorithm(myGraph.adjList[myGraph.intersections.get(intersection1)].getFirst(),
                myGraph.adjList[myGraph.intersections.get(intersection2)].getFirst())+"\n");

        g2d.setColor(Color.red);
        for (int i = 0; i < myGraph.dijkstra.size()-1; i++){
            try {
                Node p1 = myGraph.dijkstra.get(i);
                Node p2 = myGraph.dijkstra.get(i + 1);

                g2d.drawLine(applyLatScaling(p1.latitude),applyLongScaling(p1.longitude),applyLatScaling(p2.latitude),applyLongScaling(p2.longitude));

            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Final Node Reached");
            }
        }
    }

    public int applyLongScaling (double value)
    {
        return ((int) ((value - myGraph.minLong)*(height / (myGraph.maxLong - myGraph.minLong))));
}
    public int applyLatScaling (double value)
    {
        return ((int) ((value - myGraph.minLat)*(width / (myGraph.maxLat - myGraph.minLat))));
    }
}
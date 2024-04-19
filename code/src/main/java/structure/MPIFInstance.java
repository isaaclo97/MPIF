package structure;

import grafo.optilib.structure.Instance;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MPIFInstance implements Instance {

    private String name;
    private int nodes,edges,facilities=-1,distance;
    private long startTime;
    private int graph[][];
    private int mapValues[];

    public MPIFInstance(String path) {
        if(path.contains("pmed") || path.contains("Eucl"))
            readInstance(path);
        else
            readInstanceKMedian(path);

        startTime = System.nanoTime();
    }

    public void readInstanceKMedian(String path) {
        this.name = path.substring(path.lastIndexOf('\\') + 1);
        int minDistance = Integer.MAX_VALUE;
        FileReader fr= null;
        try {
            fr = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br=new BufferedReader(fr);

        // read line by line
        String line;
        try{
            line = br.readLine();
            line = br.readLine();
            String[] l = line.split(" ");
            nodes = Integer.parseInt(l[0]);
            edges = nodes*nodes;
            facilities = Integer.parseInt(l[2]);
            distance = Integer.parseInt(l[3]);
            graph = new int[nodes][nodes];
            mapValues = new int[nodes];

            for(int i=0; i<nodes;i++){
                line = br.readLine();
                l = line.split(" ");
                for(int j=0; j<l.length-2;j++){
                    int start = i;
                    int end = j;
                    int cost = Integer.parseInt(l[j+2]);
                    graph[start][end]=cost;
                    graph[end][start]=cost;
                    if(cost!=0)
                        minDistance = Math.min(cost,minDistance);
                }
            }
            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void readInstance(String path) {
        this.name = path.substring(path.lastIndexOf('\\') + 1);
        FileReader fr= null;
        try {
            fr = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br=new BufferedReader(fr);

        // read line by line
        String line;
        try{
            line = br.readLine();
            String[] l = line.split(" ");
            nodes = Integer.parseInt(l[1]);
            edges = Integer.parseInt(l[2]);
            facilities = Integer.parseInt(l[3]);
            mapValues = new int[nodes];
            distance = Integer.parseInt(l[4]);
            graph = new int[nodes][nodes];
            for(int i=0; i<nodes;i++){
                Arrays.fill(graph[i],0x3f3f3f3f);
            }

            while ((line = br.readLine()) != null) {
                l = line.split(" ");
                int start = Integer.parseInt(l[1])-1;
                int end = Integer.parseInt(l[2])-1;
                int cost = Integer.parseInt(l[3]);
                graph[start][end]=cost;
                graph[end][start]=cost;
            }
            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        FloydWarshall();
    }

    public void FloydWarshall(){
        for (int k = 0; k < nodes; k++)
            for (int i = 0; i < nodes; i++)
                for (int j = 0; j < nodes; j++)
                    graph[i][j]=Math.min(graph[i][j],graph[i][k]+graph[k][j]);
    }

    public long getStartTime() {
        return startTime;
    }

    public String getName() {
        return name;
    }

    public int getNodes() {
        return nodes;
    }

    public int getFacilities() {
        return facilities;
    }

    public int[][] getGraph() {
        return graph;
    }

    public int getDistance(){
        if(distance==0){
            System.out.println("Error in distance");
        }
        return distance;
    }

    public int[] getMapValues() {
        return mapValues;
    }

    public void setMapValues(int id, int value) {
        this.mapValues[id] = value;
    }
}

package mcctp;

import java.util.ArrayList;

/**
 *
 * @author Alek_G12
 */
public class Vehicle {
    private double latency;
    private double maxLatency;
    private ArrayList<Integer> route = new ArrayList<>();
    private int lastNode = 0;

    public Vehicle() {
        route.add(0);
    }
    
    public double getLatency() {
        return latency;
    }

    public double getMaxLatency() {
        return maxLatency;
    }

    public ArrayList<Integer> getRoute() {
        return route;
    }

    public int getLastNode() {
        return lastNode;
    }

    private void setLastNode(int lastNode) {
        this.lastNode = lastNode;
    }
    
    private void addLatency(double latency){
        this.latency += latency; 
    }
    
    public void updateRoute(int node, double distance){
        route.add(node);
        setLastNode(node);
        addLatency(distance);
    }
    
}

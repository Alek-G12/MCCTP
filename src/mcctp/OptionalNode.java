package mcctp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Alek_G12
 */
public class OptionalNode implements Comparable<OptionalNode>{
    private int index;
    private boolean inRoute;
    private int vehicleNumber;
    private int uNodesCovered;
    private HashMap<Integer, UnreachableNode> covers = new HashMap<>();

    public OptionalNode(int index,  HashMap<Integer, UnreachableNode> covers, int uNodesCovered) {
        this.index = index;
        this.covers = covers;
        this.inRoute = false;
        this.uNodesCovered = uNodesCovered;
        
    }

    public int getIndex() {
        return index;
    }

    public boolean isCovered() {
        return inRoute;
    }

    public void setCovered(boolean isCovered) {
        this.inRoute = isCovered;
    }

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public int getuNodesCovered() {
        return uNodesCovered;
    }

    public void setuNodesCovered(int uNodesCovered) {
        this.uNodesCovered = uNodesCovered;
    }

    public  HashMap<Integer, UnreachableNode> getCovers() {
        return covers;
    }

    @Override
    public int compareTo(OptionalNode o) {
        return o.uNodesCovered - this.uNodesCovered;
    }
    
}

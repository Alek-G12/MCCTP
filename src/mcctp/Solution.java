package mcctp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alek_G12
 */
public class Solution {

    public int kVehicles;
    public long maxDuration;
    public int tMandatoryLocations;
    public int optionalLocations;
    public int unreachableLocations;

    public HashMap<String, Double> distanceMap;
    public HashMap<Integer, ArrayList<Integer>> matrix;

    public HashMap<Integer, Vehicle> vehicleSet = new HashMap<>();
    public HashMap<Integer, MandatoryNode> MandatoryNodeSet = new HashMap<>();
    public ArrayList<OptionalNode> optionalNodeSet= new ArrayList<>();
    public HashMap<Integer, UnreachableNode> unreachableNodeSet = new HashMap<>();

    public Solution(int kVehicles, long maxDuration, int tMandatoryLocations, int optionalLocations, int unreachableLoctions, HashMap<String, Double> distanceMap, HashMap<Integer, ArrayList<Integer>> matrix) {
        this.kVehicles = kVehicles;
        this.maxDuration = maxDuration;
        this.tMandatoryLocations = tMandatoryLocations;
        this.optionalLocations = optionalLocations;
        this.distanceMap = distanceMap;
        this.unreachableLocations = unreachableLoctions;
        this.matrix = matrix;
    }

    public void initialize() {
        for (int i = 0; i < kVehicles; i++) {
            vehicleSet.put(i, new Vehicle());
        }
        for (int i = 0; i < tMandatoryLocations; i++) {
            MandatoryNodeSet.put(i, new MandatoryNode(i));
        }
        for (int i = tMandatoryLocations; i < optionalLocations + tMandatoryLocations; i++) {
             HashMap<Integer, UnreachableNode> covers = new HashMap<>();
            for (int j = 1; j <= unreachableLocations; j++) {
                if (matrix.get(i).get(j - 1) == 1) {
                    covers.put(j, new UnreachableNode(j));
                }
            }
            optionalNodeSet.add(new OptionalNode(i, covers, covers.size()));
        }
        Collections.sort(optionalNodeSet);

        for (int i = 1; i <= unreachableLocations; i++) {
            unreachableNodeSet.put(i, new UnreachableNode(i));
        }
    }

    public void departFromDepot() {
        int mandatoryLocationIndex = 0;
        MandatoryNodeSet.get(0).setAvailable(false);
        for (int i = 0; i < kVehicles; i++) {
            if(tMandatoryLocations < kVehicles){
                break;
            }
            mandatoryLocationIndex++;
            double distance = distanceMap.get("0," + mandatoryLocationIndex);
            vehicleSet.get(i).updateRoute(mandatoryLocationIndex, distance);
            MandatoryNodeSet.get(mandatoryLocationIndex).setAvailable(false);
        }
        goToMandatoryNodes();
    }

    public void goToMandatoryNodes() {
        double minDistance = 9999999;
        int vehicleIndex = 0;
        for (int i = kVehicles + 1; i < tMandatoryLocations; i++) {
            for (int j = 0; j < kVehicles; j++) {
                int lastNode = vehicleSet.get(j).getLastNode();
                if (MandatoryNodeSet.get(i).isAvailable()) {
                    double distance = distanceMap.get(lastNode + "," + i);
                    if (distance < minDistance) {
                        minDistance = distance;
                        vehicleIndex = j;
                    }
                }
            } // Finished testing vehicles
            vehicleSet.get(vehicleIndex).updateRoute(i, minDistance);
            MandatoryNodeSet.get(i).setAvailable(false);
        } // Finished assigning Mandatory nodes
    }

    public void goToOptionalNodes() {
        for (int i = 1; i <= unreachableLocations; i++) {
            for (int j = 0; j < optionalLocations; j++) {
                double minDistance = 99999999;
                int vehicleIndex = 0;
                if(!unreachableNodeSet.get(i).isCovered()){ //If unreacheable is not covered, then
                    if(optionalNodeSet.get(j).getCovers().containsKey(i) && !optionalNodeSet.get(j).isCovered()){
                        //System.out.println("Optional node "+optionalNodeSet.get(j).getIndex()+" Covers Unreachable location "+unreachableNodeSet.get(i).getIndex());
                        for (int k = 0; k < kVehicles; k++) {
                            int lastNode = vehicleSet.get(k).getLastNode();
                            double distance = distanceMap.get(lastNode + "," + optionalNodeSet.get(j).getIndex());
                            if(distance < minDistance && vehicleSet.get(k).getLatency() + distance <=  maxDuration){
                                minDistance = distance;
                                vehicleIndex = k;
                            }
                        }
                        vehicleSet.get(vehicleIndex).updateRoute(optionalNodeSet.get(j).getIndex(), minDistance);
                        optionalNodeSet.get(j).setCovered(true);
                        optionalNodeSet.get(j).setVehicleNumber(vehicleIndex);
                        optionalNodeSet.get(j).getCovers().keySet().forEach((key) ->{
                            unreachableNodeSet.get(key).setCovered(true);
                        });
                    }
                    
                }
            }
            
        }
    }

    public void printSets() {
        System.out.println("Vehicle Routes");
        for (Map.Entry<Integer, Vehicle> entry : vehicleSet.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().getLatency() + " " + entry.getValue().getRoute());
        }
        System.out.println("Mandatory Locations Left");
        for (Map.Entry<Integer, MandatoryNode> entry : MandatoryNodeSet.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().isAvailable());
        }
        System.out.println("Optional Locations Visited");
        for (OptionalNode optionalNode : optionalNodeSet) {
            System.out.println(optionalNode.getIndex()+"\t"+optionalNode.isCovered()+"\t"+optionalNode.getuNodesCovered());
        }
        System.out.println("Unreachable Locations Covered");
        for (Map.Entry<Integer, UnreachableNode> entry : unreachableNodeSet.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue().isCovered());
        }
    }

    public void printTotalLatency() {
        double totalLatency = 0;
        for (Map.Entry<Integer, Vehicle> entry : vehicleSet.entrySet()) {
            totalLatency += entry.getValue().getLatency();

        }
        System.out.println("Total Latency: " + totalLatency);
    }

}

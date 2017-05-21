package mcctp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alek_G12
 */
public class Instance {

    private final File file;

    public int vNodes; //Number of nodes that can be visited **May or may not be visited**
    public int wCustomersToCover; //Number of customers we hae to cover
    public int tNodes; //Number of nodes that must be visited **Mandatory**
    public int vehicles; //Number of vehicles available
    public int upperBound; //An upper bound??

    public HashMap<String, Double> distanceMap = new HashMap<>(); //Map stores distance between node pairs
    public HashMap<Integer, ArrayList<Integer>> matrix = new HashMap<>(); //Covering matrix binary

    public String route = "0";
    public double latency = 0;

    public Instance(File file) {
        this.file = file;
    }

    public void getInstanceVariables() {
        try {
            Scanner scanner = new Scanner(file);

            String line = scanner.nextLine();
            String[] splitted = line.split(" ");

            vNodes = Integer.parseInt(splitted[0]);
            wCustomersToCover = Integer.parseInt(splitted[1]);
            tNodes = Integer.parseInt(splitted[2]);
            vehicles = Integer.parseInt(splitted[3]);
            try{
                upperBound = Integer.parseInt(splitted[4]);
            } catch(IndexOutOfBoundsException ex){
                upperBound = 0;
            }
            getDistanceTable(scanner);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getDistanceTable(Scanner scanner) {
        //Distance between nodes table into HashMap
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("")) {
                break;
            }
            String node1 = line.substring(0, 3);
            String node2 = line.substring(3, 7);
            String fordward = node1.trim() + "," + node2.trim();
            String backward = node2.trim() + "," + node1.trim();
            //Map Key Format: node1,node2 
            String distance = line.substring(8);
            distanceMap.put(fordward, Double.parseDouble(distance));
            distanceMap.put(backward, Double.parseDouble(distance));
        }
        getCoveringMatrix(scanner);
    }

    public void getCoveringMatrix(Scanner scanner) {
        //Read Covering Matrix
        for (int i = tNodes; i < vNodes+tNodes; i++) {
            int key = scanner.nextInt();
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int j = 0; j < wCustomersToCover; j++) {
                        numbers.add(scanner.nextInt());
            }
            matrix.put(key, numbers);
        }
    }

    public void printMatrix() {
        for (Map.Entry<Integer, ArrayList<Integer>> entry : matrix.entrySet()) {
            System.out.print(entry.getKey()+"\t");
            System.out.print(entry.getValue().toString());
            System.out.println("");
        }
    }
    public void printVariables(){
        System.out.println("Optional Nodes: "+ vNodes);
        System.out.println("Customers to cover: "+ wCustomersToCover);
        System.out.println("Mandatory Nodes: "+ tNodes);
        System.out.println("Number of vehicles: "+ vehicles);
        System.out.println("UpperBound: "+ upperBound);
    }
    
    public void printDistanceMap(){
        for (Map.Entry<String, Double> entry : distanceMap.entrySet()) {
            System.out.println(entry.getKey()+"  "+entry.getValue());
        }
    }

}

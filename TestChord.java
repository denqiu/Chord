package chord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

/**
 * This class executes the chord program and create a new chord based on the B-Bit and number of nodes given.
 * @author DennisQiu
 */

public class TestChord {

	final private static boolean showTables = false;
	
	public static void main(String[] args) {
		TestChord.executeChord();
	}
		
	/**
	 * Executes the chord program. 
	 */
	public static void executeChord() {
		 try {
	        @SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);  
	        String bBitRange, nodesRange, bBitNumber, node; int bBit, n; boolean checkB, checkNode;
	        bBitRange = "a B-bit number from 5 to 10";
			nodesRange = "the number of nodes from 5 to 15";
	        System.out.println("Enter " + bBitRange + ": ");
	        bBitNumber = scanner.nextLine();  
	        System.out.println("Enter " + nodesRange + ": ");   
	        node = scanner.nextLine();  
	        bBit = Integer.valueOf(bBitNumber);
	        n = Integer.valueOf(node);
			FingerTables.collect(showTables, n);
	        displayInfo(showTables, bBit, n, null);
	        checkB = bBit < 5 || bBit > 10;
	        checkNode = n < 5 || n > 15;
        	if (checkB || checkNode) {
            	if (checkB)
            		System.out.println("Please enter " + bBitRange);
            	if (checkNode)
            		System.out.println("Please enter " + nodesRange);
            	System.out.println(); 
            	executeChord();
            } else {
            	ArrayList<Integer> nodes = createNodes(bBit, n);
            	displayInfo(showTables, null, null, nodes);
            	createFingerTables(showTables, bBit, nodes);
            	int selectedKey = createSelectedKey(bBit);
            	int startNode = nodes.get(1); 
            	System.out.println("\nSelected key: K" + selectedKey + "\nNstart: N" + startNode);
            	visitingNodes(bBit, startNode, selectedKey, nodes);	
            }
        } catch (NumberFormatException e) {
        	System.out.println("PLEASE ENTER A NUMERICAL VALUE\n");
        	executeChord();
        } 
	}
	
	/**
	 * Allows the user to display information entered. They can choose whether they want to see the information or not.
	 * @param displayInfo determines whether the user wants extra information to be shown or not
	 * @param nodes displays all the nodes in the arrayList of nodes
	 * @param bBit displays the bBit number that the user has chosen
	 * @param n displays the number of nodes that the user has chosen
	 */
	public static void displayInfo(boolean displayInfo, Integer bBit, Integer n, ArrayList<Integer> nodes) {
		if (displayInfo) {
			if (nodes == null) {
				System.out.println("B-Bit = " + bBit); 
				System.out.println("Number of nodes = " + n + "\n");
			} else {
            	System.out.println("Nodes created: " + nodes);
            	System.out.println("Size = " + nodes.size() + "\n");
			}
		}
	}

	/**
	 * Creates a random arrayList of nodes. 
	 * @param bBit used set the maximum node ID using the power of 2
	 * @param n the total number of nodes that will be created
	 * @return A sorted random arrayList of nodes.
	 */
	private static ArrayList<Integer> createNodes(int bBit, int n) {
		HashSet<Integer> addNodes = new HashSet<Integer>();
		int maxNode = (int) Math.pow(2, bBit);
		Random random = new Random();
		while (addNodes.size() < n) {
			int randomNode = random.nextInt((maxNode - 1) + 1) + 1;
			addNodes.add(randomNode);
		}
		ArrayList<Integer> sortedNodes = new ArrayList<Integer>(addNodes);
		Collections.sort(sortedNodes);
		return sortedNodes;
	}
	
	/**
	 * Creates a randomly selected key ID.
	 * @param bBit used to set the maximum key ID using the power of 2
	 * @return The randomly selected key ID.
	 */
	private static int createSelectedKey(int bBit) {
		Random random = new Random();
		int maxKey = (int) Math.pow(2, bBit);
		int randomKey = random.nextInt((maxKey - 1) + 1) + 1;	
		return randomKey;
	}
	
	/**
	 * Recursively creates finger tables.
	 * @param showTables determines whether the user wants to see the finger tables of each node in the nodes arrayList or not
	 * @param bBit used to set the maximum node ID
	 * @param nodes the arrayList of nodes that was created
	 */
	private static void createFingerTables(boolean showTables, int bBit, ArrayList<Integer> nodes) {
    	FingerTables.createFingerTables(showTables, bBit, nodes);
	}
	
	/**
	 * Recursively creates the visiting order of nodes and their finger tables using the selected key ID and Nstart.
	 * @param bBit used to set the maximum node ID
	 * @param startNode the starting node of the visiting order of nodes
	 * @param selectedKey the key to look up in the arrayList of nodes
	 * @param nodes the arrayList of nodes that was created
	 */
	private static void visitingNodes(int bBit, int startNode, int selectedKey, ArrayList<Integer> nodes) {
		FingerTables.checkFingerTable(bBit, startNode, selectedKey, nodes);	
	}
}

package chord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 * This class creates the finger tables for each node in the chord, including the visiting nodes.
 * @author DennisQiu
 */

public class FingerTables {
	private static ArrayList<Integer> keys = new ArrayList<Integer>();
	private static ArrayList<Integer> originalNodes = new ArrayList<Integer>();
	private static ArrayList<Integer> duplicateNodes = new ArrayList<Integer>();
	private static ArrayList<Integer> visitingNodes = new ArrayList<Integer>();
	private static int bMax = 0;
	private static int size = 0;
	private static int n = 0;
	private static boolean foundKey = false;
	private static boolean showTables = false;
	
	/**
	 * Creates the finger tables of each node in the arrayList of nodes.
	 * @param showTables determines whether the user wants to see the finger tables of each node in the nodes arrayList or not
	 * @param bBit used to create the maximum node ID
	 * @param nodes the arrayList of nodes that was created
	 */ 
	public static void createFingerTables(boolean showTables, int bBit, ArrayList<Integer> nodes) {
		bMax = (int) Math.pow(2, bBit); 
		if (showTables) {
			size = nodes.size(); int bResult, newNode, i;
			System.out.println("FINGER TABLES:");
			System.out.println("POWER OF 2");
			for (i = 0; i <= bBit; i++) {
				bResult = (int) Math.pow(2, i);
				System.out.println("2^" + i + " = " + bResult);
			}
			System.out.println("\n+" + bMax);
			for (i = 0; i < size; i++) {
				newNode = nodes.get(i) + bMax;
				System.out.println("N" + nodes.get(i) + " = N" + newNode);
			}
			for (i = 0; i < size; i++) {
				checkFingerTable(bBit, nodes.get(i), 0, nodes);
			}
		}		
	}
	
	/**
	 * Creates a finger table associated with it's node ID.
	 * @param bBit used to create the maximum node ID
	 * @param node the current node ID to be used to create the finger table
	 * @param selectedKey the key ID that was selected to look up
	 * @param nodes the arrayList of nodes that was created
	 */
	private static void fingerTable(int bBit, int node, int selectedKey, ArrayList<Integer> nodes) {
		int bResult, nodeSuccessor, key, originalNode = 0, i;
		String original = "";
		for (i = 0; i <= bBit; i++) {
			bResult = (int) Math.pow(2, i);
			key = node + bResult;
			nodeSuccessor = findClosest(key, nodes, "n");
			if (nodeSuccessor > bMax) {
				originalNode = nodeSuccessor - bMax;
				original = " = N" + originalNode;
				if (selectedKey != 0)
					originalNodes.add(originalNode);
			}
			if (selectedKey != 0) {
				if (node > bMax)
					node = node - bMax;
				keys.add(key); visitingNodes.add(node);
			}
			System.out.println("N" + node + " + " + bResult + " = K" + key + " -- N" + nodeSuccessor + original);
		}
		if (selectedKey != 0) {
			Collections.sort(keys); Collections.sort(originalNodes);
			visitingNodes(bBit, selectedKey, nodes);
		}
	}
	
	/**
	 * Finds the node successor or the next key ID to select.
	 * @param key the key being compared with to find either the node successor or the next key ID
	 * @param keysOrNodes either the arrayList of keys or nodes in the finger table
	 * @param closest determines which methods to use for finding the node successor or the next key ID
	 * @return The node successor or the next key ID.
	 */
	private static int findClosest(int key, ArrayList<Integer> keysOrNodes, String closest) {
		size = keysOrNodes.size(); int i = 0, mid = 0, newClosestNode; 
        while (i < size) { 
			mid = (i + size) / 2;
        	if (closest.equalsIgnoreCase("n")) {
        		while (key > keysOrNodes.get(size - 1)) {
            		newClosestNode = keysOrNodes.get(i) + bMax; i++;		
    				if (key <= newClosestNode) 
    					return newClosestNode;	
        		} 
        	} else if (closest.equalsIgnoreCase("k")) {
        		if (key < keysOrNodes.get(0)) {
        			if (key < originalNodes.get(0)) {
        				return 0;
        			} else {
        				size = originalNodes.size();
						while (i < size) {
            				newClosestNode = originalNodes.get(i); i++;
            				if (key <= newClosestNode) 
            					return originalNodes.get(originalNodes.indexOf(newClosestNode) - 1);
            			}
						return 0;
        			}		
        		} else {
        			for (Integer k : keysOrNodes) {
                		if (k == key)
                			return k;
                	}
        		}
        	} 	
        	if (key < keysOrNodes.get(mid)) {
				if (closest.equalsIgnoreCase("n")) {
					if (mid > 0 && key > keysOrNodes.get(mid - 1))
						return keysOrNodes.get(mid);
					size = mid;
				} else if (closest.equalsIgnoreCase("k")) {
					if (mid > 0 && key > keysOrNodes.get(mid - 1))
						return keysOrNodes.get(mid - 1);
					size = mid - 1;
				}
			} else if (key == keysOrNodes.get(mid)) {
        		return keysOrNodes.get(mid);
			} else {
				if (closest.equalsIgnoreCase("n")) {
					if (mid < size - 1 && key < keysOrNodes.get(mid + 1))
						return keysOrNodes.get(mid + 1);
					i = mid + 1;
				} else if (closest.equalsIgnoreCase("k")) {
					if (mid < size - 1 && key < keysOrNodes.get(mid + 1))
						return keysOrNodes.get(mid);
					else if (mid < size && key < keysOrNodes.get(mid + 1))
						return keysOrNodes.get(mid);
					i = mid;
				}
			}
		}
        return 0;
	}
	
	/**
	 * Collects the values from TestChord about whether the user wants to see finger tables or not and the number of nodes given by the user.
	 * @param tables determines whether the user wants to see the finger tables or not
	 * @param node uses the number of nodes that the user has given
	 */
	public static void collect(boolean tables, int node) {
		showTables = tables; n = node;
	}
	
	/**
	 * Determines whether the final node has been found or not.
	 * @param bBit used to create the maximum node ID
	 * @param selectedKey the key that was selected to look up
	 * @param nodes the arrayList of nodes that was created
	 */
	private static void visitingNodes(int bBit, int selectedKey, ArrayList<Integer> nodes) {
		size = visitingNodes.size(); int closestKey, nextNode = 0;
		if (foundKey) {
			System.out.println("\nThe final node, N" + visitingNodes.get(size - 1) + ", associated with key, K" + selectedKey + ", has been found");
			printVisitingNodes(bBit);
			if (showTables)
				reRun();
			else
				displayFingerTables(bBit, nodes);
		} else {
			if (selectedKey < keys.get(0)) {
				nextNode = findClosest(selectedKey, keys, "k");
				checkFingerTable(bBit, nextNode, selectedKey, nodes);
			} else {
				closestKey = findClosest(selectedKey, keys, "k");
				nextNode = findClosest(closestKey, nodes, "n");
				if (nextNode > bMax)
					nextNode = nextNode - bMax;
				foundKey = closestKey == selectedKey;
				checkFingerTable(bBit, nextNode, selectedKey, nodes);
				System.out.println();
			}
		}	
	}
	
	/**
	 * Checks to see if the visiting order of nodes will continue in an infinite loop or not. Also checks if a selected key ID is used or not.
	 * @param bBit used to create the maximum node ID
	 * @param node the current node ID to be used to create the finger table
	 * @param selectedKey the selected key that was either used or not used 
	 * @param nodes the arrayList of nodes that was created
	 */
	public static void checkFingerTable(int bBit, int node, int selectedKey, ArrayList<Integer> nodes) {
		try {
			String nodeString = "\nNODE " + node;
			if (node == 0) 
				nodeString = "\nThe final node associated with the selected key, K" + selectedKey + ", could not be found";
			System.out.println(nodeString);
			if (node != 0) {
				if (selectedKey != 0) {
					duplicateNodes.add(node);
					HashSet<Integer> uniqueNodes = new HashSet<Integer>(duplicateNodes);
					size = duplicateNodes.size(); int uniqueSize = uniqueNodes.size();
					if (uniqueSize < size) {
						if (foundKey) {
							
						} else {
							System.out.println("The final node associated with the selected key, K" + selectedKey + ", could not be found");
							for (int i = 0; i < nodes.size(); i++) {
								if (selectedKey == nodes.get(i))
									System.out.println("The selected key, K" + selectedKey + ", equals node, N" + nodes.get(i) + " in the nodes arrayList");	
							}		
							printVisitingNodes(bBit);
							clearAndExecute();
						}
					} 
				}
				keys.clear(); originalNodes.clear();
				fingerTable(bBit, node, selectedKey, nodes);
			} else {
				printVisitingNodes(bBit);
				clearAndExecute();
			}	
		} catch (ArrayIndexOutOfBoundsException a) {
			System.out.println("\nThe selected key, K" + selectedKey + ", equals Nstart, N" + nodes.get(1));
			visitingNodes.add(nodes.get(1));
			printVisitingNodes(bBit);
			clearAndExecute();
		}
	}
	
	/**
	 * Prints out the visiting order of nodes.
	 * @param bBit has a relationship with the frequency of each visiting node, in which the frequency of a visiting node always equals bBit plus one
	 */
	private static void printVisitingNodes(int bBit) {
		String repeatNode = "";
		LinkedHashSet<Integer> visitors = new LinkedHashSet<Integer>(visitingNodes);
		for (Integer v : visitors) {
			boolean repeatingNodes = Collections.frequency(visitingNodes, v) / (bBit + 1) > 1;
			if (repeatingNodes) 
				repeatNode = " --> N" + v;
		}
		String visitingOrder = "The visiting order of nodes is: " + visitors.toString().replace("[", "N").replace(", ", " --> N").replace("]", "");
		System.out.println(visitingOrder + repeatNode + "\n");
	}
	
	/**
	 * Asks if user wants to see the finger tables of the chord or not.
	 * @param bBit displays the B-Bit number that the user previously inputted
	 * @param nodes displays the nodes created in the arrayList of nodes
	 */
	private static void displayFingerTables(int bBit, ArrayList<Integer> nodes) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);  
		System.out.println("Do you want to see the finger tables of this chord? (Answer yes or no)");
		String answer = scanner.nextLine();
		if (answer.equalsIgnoreCase("yes")) {
	        TestChord.displayInfo(true, bBit, n, null);
        	TestChord.displayInfo(true, null, null, nodes);
			createFingerTables(true, bBit, nodes);
			System.out.println();
			reRun();
		} else if (answer.equalsIgnoreCase("no")) {
			System.out.println();
			reRun();
		} else {
			System.out.println("Please answer yes or no");
			displayFingerTables(bBit, nodes);
		}
	}
	
	/**
	 * Asks to re-run the chord program if a final node was successfully found.
	 */
	private static void reRun() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);  
		System.out.println("Do you want to test a new chord? (Answer yes or no)");
		String answer = scanner.nextLine();
		if (answer.equalsIgnoreCase("yes")) {
			clearAndExecute();
		} else if (answer.equalsIgnoreCase("no")) {
			System.exit(0);
		} else {
			System.out.println("Please answer yes or no");
			reRun();
		}
	}
		
	/**
	 * Clears all the declared arrayLists and resets all declared values back to their default values before executing the executeChord method.
	 */
	private static void clearAndExecute() {
		keys.clear(); originalNodes.clear(); visitingNodes.clear(); duplicateNodes.clear();
		bMax = 0; size = 0; foundKey = false;
		TestChord.executeChord();
	}
}

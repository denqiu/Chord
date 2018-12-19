========================================PROGRAMMING PROJECT 1: CHORD -- IMPLEMENTATION by Dennis Qiu==============================================================
*NOTE: If you aren't exactly sure how to follow the instructions or are not familiar with Java, 
       my exact implementations will be there to help. 
       - Along the way, if you see a red line while coding, you most likely have not created a method for that line of code yet. 
         You will create the method eventually. Just keep following the instructions. Although, you don't actually need to 
         follow the instructions in the exact order below.
       - DON'T FORGET TO ADD ";" to the end of each line of code!
       
1. If you haven't already, download Eclipse Oxygen version here: https://www.eclipse.org/downloads/packages/release/oxygen/r/eclipse-ide-java-developers
   It should be okay to proceed to the next step if your Eclipse IDE version is not the Oxygen version.
   When you open Eclipse, create a folder in Project Explorer (found in Window >> Show View) called Project 1. 
   Make sure you can see the Console (Window >> Show View).
   In the "src" folder in Project 1, create a package called chord, using the brown box icon near the top right corner.
   Create two public classes - TestChord and FingerTables, under package "chord" using the icon next to the "New Java Package" icon (has a letter C). 

2. TestChord will have a public static void main method to execute this program. Make sure to select the checkbox for it.
   The method will contain something like this: TestChord.executeChord();
   In case you forgot to select the checkbox, here's the implementation:
   
						   public static void main(String[] args) {
								TestChord.executeChord();
						   }
							
   FingerTables will not have a public static void main method. You do not need to select the checkbox for it.
   - MAKE SURE BOTH CLASSES HAVE: package chord;
   
3. In TestChord, declare a final private static boolean variable named showTables. Make sure to initialize it. 
   - MAKE SURE TestChord imports these:
	    import java.util.ArrayList;
		import java.util.Collections;
		import java.util.HashSet;
		import java.util.Random;
		import java.util.Scanner;	    
		        OR
		You can simply import this:
	        import java.util.*; 	          
   - Implementation: final private static boolean showTables = false; 
   - Use this variable to control whether or not you want to ONLY see the visiting nodes and their finger tables concerning 
     the selected key and Nstart (start node). 
   
   Next create the following methods:
   		a. Call this method executeChord. This method will execute the chord program. It is a public static void method.
   		   - Create a try-catch NumberFormatException. Everything will be inside this try-catch.
   		     If the exception is caught, display an error message about numbers and recursively call executeChord.
   		   - Use a Scanner class to allow the user to input information into the Console.
   		   - The user will enter the B-Bit number from 5-10 and the number of nodes from 5-15
   		   - Transfer the variables showTable and n over to class FingerTables
   		   - Call a void method called displayInfo, which is controlled by the value you decided for showTables.
   		   - Create boolean variables checkB and checkNode, which will check if the user inputs the numbers for B-Bit and # of nodes within range.
   		     Create an if-else statement to check using checkB and checkNode. 
   		     If the inputted value is out of range, recursively call executeChord
   		     else, here we will assume the user has inputted B-Bit and # of nodes within range.
   		       - Call an ArrayList<Integer> method and name it nodes.
   		       - Call displayInfo.
   		       - Call createFingerTables.
   		       - Create a selected key and Nstart (using the second smallest node) and DISPLAY them.
   		       - Call visitingNodes.
   		   - Implementation:
   		   
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
   		   		
   		b. Call this method displayInfo. It will display some information based on showTables boolean value. It is a public static void method.
   		   - Create parameters >> boolean displayInfo, Integer bBit, Integer n, ArrayList<Integer> nodes
   		   - Use an if statement with displayInfo.
   		     If true, it will display some info. Otherwise display none if false.
   		       - There will be two groups of info, bBit & n AND nodes.
   		       - The simplest way to figure out which group is to use the if statement nodes is null. 
   		         It is simpler than saying if bBit and n are not null, which reduces errors your if statement may create. 
   		   - Implementation:
   		   
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
	
   		c. Call this method createNodes. It will return a sorted arrayList of nodes. It is a private static ArrayList<Integer> method.
   		   - Create parameters >> int bBit, int n
   		   - This method will use Math.pow to create the maximum node ID
   		   - It will create an unique, random set of nodes using Random class, added to a HashSet 
   		     while the size of the HashSet is less than the given number of nodes
   		   - Create an arraylist with the same numbers from the HashSet to be able to sort the nodes in increasing order.
   		   - Click this link if you don't understand exactly why the correct range is coded the way it's coded: https://www.mkyong.com/java/java-generate-random-integers-in-a-range/
   		   - Implementation: 
   		   
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
   		   
   		d.  Call this method createSelectedKey. It will return a random key ID using Math.pow and Random class. It is a private static int method.
   		    - The maximum key ID is coded the same as the maximum node ID
   		    - Implementation:
   		       
	   		        private static int createSelectedKey(int bBit) {
						Random random = new Random();
						int maxKey = (int) Math.pow(2, bBit);
						int randomKey = random.nextInt((maxKey - 1) + 1) + 1;	
						return randomKey;
					}
   		    
   		e. createFingerTables will be recursive. 
   		
		   		private static void createFingerTables(boolean showTables, int bBit, ArrayList<Integer> nodes) {
		    		FingerTables.createFingerTables(showTables, bBit, nodes);
				}
   		
   		f. nodeFingerTable will also be recursive.
   		
		   		private static void visitingNodes(int bBit, int startNode, int selectedKey, ArrayList<Integer> nodes) {
					FingerTables.checkFingerTable(bBit, startNode, selectedKey, nodes);	
				}
   		
4. In FingerTables, declare the following variables. They are all private static variables: 
   - ArrayList<Integer> >> keys, originalNodes, checkDuplicateNodes, visitingNodes
   - int >> bMax (maximum node ID of B-Bit), size (the size of the arrayList), n
   - boolean >> foundKey (initialize false), showTables (initialize false)
   - MAKE SURE FingerTables imports these:
		    import java.util.ArrayList;
			import java.util.Collections;
			import java.util.HashSet;
			import java.util.LinkedHashSet;
			import java.util.Scanner;
			         OR
		You can simply import this:
		    import java.util.*;
   - Implementation: 
	    private static ArrayList<Integer> keys = new ArrayList<Integer>();
		private static ArrayList<Integer> originalNodes = new ArrayList<Integer>();
		private static ArrayList<Integer> duplicateNodes = new ArrayList<Integer>();
		private static ArrayList<Integer> visitingNodes = new ArrayList<Integer>();
		private static int bMax = 0;
		private static int size = 0;
		private static int n = 0;
		private static boolean foundKey = false;
		private static boolean showTables = false;
		
   Next create the following methods:
   		a. Call this method createFingerTables. It will create the finger tables of each node in the nodes arrayList. It is a public static void method.
   		   - Create parameters >> boolean showTables, int bBit, ArrayList<Integer> nodes
   		   - Use Math.pow to create bMax
   		   - Place the rest of the code inside showTables if statement
   		   - Size will be the size of the nodes arrayList. Create int variables called bResult, newNode, i
   		   - Create three for-loop statements to display the powers of 2, the sum of nodes and bMax if key is greater than the biggest node, 
   		     and the finger tables of each node in the nodes arrayList.
   		   - Implementation: 
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
					
		 b. Call this method fingerTable. It will create a finger table for the given node. It is a private static void method.
		 	- Create parameters >> int bBit, int node, int selectedKey, ArrayList<Integer> nodes
		 	- Create variables >> int bResult, nodeSuccessor, key, originalNode (initialize to 0), i; String original (initialize to "")	
		 	- Create a for-loop statement that will generate each finger in the finger table, from the minimum bBit to the maximum (user-inputted) bBit
		 	- Make sure to calculate if a nodeSuccessor is greater than the last node of the chord	 
		 	- Create several selectedKey if-statements if the selected key exists
		 	- Make sure to add the variables to their corresponding arrayLists
		 	- Make sure to sort ONLY keys and originalNodes and call visitingNodes
		    - Implementation:		    
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
		    
		 c.  Call this method findClosest. It will return either the node successor or the next key ID. It is a private static int method.
		     - Create parameters >> int key, ArrayList<Integer> keysOrNodes, String closest
		     - Create variables >> size (size of keysOrNodes), int i, mid, newClosestNode (initialize i and mid to 0)
		     - This whole code will be inside a while loop, while i is less than size.
		     - Calculate the middle of the arrayList
		     - Create an if-else if statement between using "n" and "k", respectively. 
		         - if "n", create a while loop, while the key is greater than the last node. 
		           Generate all the new closest nodes greater than bMax. 
		           if the key is equal to or less than the new closest node, return the key to the new closest node.
		         - if "k", create an if-else statement to check if the key is less than the first key created.
		           If so, check if the key is less than the smallest original node. 
		                - If so, return 0.
			            - else, size equals the size of originalNodes. Use a while loop, similar to the while loop in "n". 
			                 - return the closest node that is smaller than the selected key. 
			                 - we know key is --> a < key < b where a is smaller closest node and b is greater closest node
			                 - the index of a always equals the index of b minus one, thanks to using a sorted arrayList
			                 - return a's index
			                 - if a's index cannot be returned, return 0. This usually means there's only one original node in originalNodes.
			       else, if key is greater than the smallest original node, use a for loop to find if the key can be found	       
			 - return mid, mid - 1, or mid + 1 depending on "n" or "k" and if the key is to the left or right of the middle of the arrayList
			 - if the key equals the middle, return it
			 - otherwise return 0
		 	 - Implementation:
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
		 
		 d. Call this method collect. It will collect variables showTables and n from class TestChord. It is a public static void method.
		    - Create parameters >> boolean tables, int node (you cannot give them the same name (showTables, n) in a static context
		    - Implementation: 	    
				    public static void collect(boolean tables, int node) {
						showTables = tables; n = node;
					}
					
		 e. Call this method visitingNodes. It will check if the key has been found or not. Otherwise, continue to keep visit some more nodes.
		    It will visit nodes depending on whether the selected key is less than or greater than the smallest key created in the finger table. 
		    It is a private static void method.
		    - size is the size of the visiting nodes. The variables are the closest key and the next node (initialize next node to 0)
		    - if key is found (foundKey), display the visiting order of nodes and determine if finger tables have been shown or not.
		      else, check if the selected key is less than or greater than the smallest key created in the finger table
		          - if less than, the next node will call findClosest using the parameters for the keys arrayList
		            Call checkFingerTable.
		          - else, next node will call findClosest using parameters for the nodes arrayList
		            Make sure to subtract the next node from bMax if it is greater than bMax.
		            Create a boolean expression to check if the closest key equals the selected key.
		            Call checkFingerTable.
		    - Implementation: 
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
		    
		 f. Call this method checkFingerTable. It will check for any strange things that may happen to the visiting order of nodes. It is a public static void method.
		    - Create parameters >> int bBit, int node, int selectedKey, ArrayList<Integer> nodes
		    - Envelope all code inside a try-catch ArrayIndexOutOfBoundsException. 
		      If exception is caught, print out a message that the selected key equals Nstart.
		      Add Nstart to visitingNodes and call printVisitingNodes and clearAndExecute.
		    - Create String nodeString, which defaults to the "name" of the node
		      If the node is 0, display an error message.
		    - if node is not 0, continue on
		         - if selected key is not 0, 
		                - first add the node to duplicateNodes arrayList.
		                - create a HashSet called uniqueNodes using duplicateNodes
		                - compare the sizes of the HashSet and arrayList to determine if a repeating node exists
		                  *NOTE: the HashSet contains ONLY unique values
		                    - if the minute the size of the HashSet is less than the size of the arrayList, 
		                         - check if the key has been found.
		                             - If so, it will automatically execute the foundKey code in visitingNodes method. It might be a little weird,
		                               but you actually don't need any code inside it. Leave it empty.
		                             - else, display a message that the key cannot be found. Also display another message showing that while the 
		                               key was not found, it did equal another node in the nodes arrayList. It is an odd, interesting situation.
		                               Call printVisitingNodes and clearAndExecute.
		         - if no errors are found, the finger table for the node will be created. Make sure to clear the keys and originalNodes arrayLists.
		      else, call printVisitingNodes and clearAndExecute
		    - Implementation:
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
				    		System.out.println("The selected key, K" + selectedKey + ", equals Nstart, N" + nodes.get(1));
							visitingNodes.add(nodes.get(1));
							printVisitingNodes(bBit);
							clearAndExecute();
				    	}						
					}
					
		 g. Call this method printVisitingNodes. It will print out the visiting order of nodes. It is a private static void 
		    - Initialize a String variable called repeateNode.
		    - Create a LinkedHashSet called visitors using the visitingNodes arrayList. The linked hash set will retain the insertion order 
		      and like a HashSet, will ONLY contain unique variables inside.
		    - Create a for loop using the LinkedHashSet
		        - Create a boolean variable called repeatNodes, which will return true 
		          IF the relationship of the frequency of each node in the visitingNodes arrayList and bBit is greater than 1.
		          - Example: Let's say bBit = 5 --> There will always be 6 same frequencies of each node in the visitingNodes arrayList,
		                                            which can be calculated as (bBit + 1). 
		                     An unique node will always have a result of 1 between this frequency-bBit relationship.
		                     If a node has appeared twice, it will show in visitingNodes but not visitors. 
		                     This repeating node's result will always be greater than 1. 
		                     
		                     Let's say visitingNodes = {5,5,5,6,6,6,7,7,7,5,5,5} (this is will not exist in the chord program)
		                     bBit = 2 --> frequency of each node = 2 + 1 = 3
		                     visitors = {5,6,7}
		                     In this case, 5 appears 6 times --> so 6 / 3 = 2 > 1 --> 5 is a repeating node
		                     The visiting order should end up like this: 5 --> 6 --> 7 --> 5 where repeatNode = " --> N" + 5
		    - print out the LinkedHashSet as a String and replace "[" with "N", replace ", " with " --> N", and replace "]" with "". Call this visitingOrder.
		      Make sure to add the repeat node after visitingOrder.
		    - Implementation:
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
					
		 h. Call this method displayFingerTables. It will ask if you want to display the finger tables if you have set showTables to false. 
		    It is a private static void method.
		    - Use Scanner class to get the user's input (answer yes or no).
		    - if "yes", the first parameter in displayInfo and createFingerTables is "true". Call reRun.
		    - else if "no", call reRun.
		    - else, this means the user entered something different other than "yes" or "no". 
		      Prompt the user again by calling this method recursively.
		    - Implementation:
				    private static void displayFingerTables(int bBit, ArrayList<Integer> nodes) {
						@SuppressWarnings("resource")
						Scanner scanner = new Scanner(System.in);  
						System.out.println("Do you want to see the finger tables of this chord? (Answer yes or no)");
						String answer = scanner.nextLine();
						if (answer.equalsIgnoreCase("yes")) {
					        TestChord.displayInfo(true, bBit, n, null);
				        	TestChord.displayInfo(true, null, null, nodes);
							createFingerTables(true, bBit, nodes);
							reRun();
						} else if (answer.equalsIgnoreCase("no")) {
							reRun();
						} else {
							System.out.println("Please answer yes or no");
							displayFingerTables(bBit, nodes);
						}
					}
					
		 i. Call this method reRun. It allows user to tell the chord program to run itself without having manually run the program.
		    It is a private static void method.
		    - Similar to displayFingerTables method.
		    - if "yes", call clearAndExecute.
		    - else if "no", terminates the chord program.
		    - else, the user has entered something different other than "yes" or "no". Call reRun again.
		    - Implementation:
				    private static void reRun() {
						@SuppressWarnings("resource")
						Scanner scanner = new Scanner(System.in);  
						System.out.println("\nDo you want to test a new chord? (Answer yes or no)");
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
			
		 j. Call this method clearAndExecute. It will clear all the declared arrayLists and reset all declared variables back to their default values.
		    Then it will call the executeChord method from class TestChord. It is a private static void method.
		    - Implementation:
				    private static void clearAndExecute() {
						keys.clear(); originalNodes.clear(); visitingNodes.clear(); duplicateNodes.clear();
						bMax = 0; size = 0; foundKey = false;
						TestChord.executeChord();
					}
				   
For this Chord project, showTables is set to false as default to ONLY show the selected key, Nstart, the visiting nodes, and their finger tables.
Set showTables in TestChord to true if you want to see the finger tables of each node. Although, it doesn't really matter. 
You can choose to see the finger tables before or after execution of each new created chord if you want to check the results of the visiting nodes' finger tables.
---------------------------------------------------------------DO NOT LOOK UNLESS YOU ABSOLUTELY NEED TO-------------------------------------------------------------
***If somehow you find that you are still not able to implement this correctly and absolutely need help, look below at my implementation again***
---------------------------------------------------------------------------------------------------------------------------------------------------------------------
*
*
*
*
*
*
*
=======TestChord============
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

=======FingerTables============
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
package main;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graph {
	//TODO: implement the fun parts (djikstras/prims/kruskals)
	// TOOD?: Better way to define a graph from scratch (GUIs?)
	Scanner input = new Scanner(System.in);
	
	private int nodesAmount; // Defines how many nodes we have
	private List<Arc> arcs;
	
	public static boolean compare(Graph graph1, Graph graph2) {  // TODO: Technically the node numbers dont actually mean anything, so make sure even if the nodes have different numbers if the graphs are still the same return true
		if(graph1.getNodesAmount() != graph2.getNodesAmount()) return false; // Checks if graphs have the same amount of nodes

		List<Integer> matched = new ArrayList<Integer>(); // Creates list of Arcs we've checked for graph2
		for(Arc arc1 : graph1.getArcs()) { // Iterates through graph1's arcs
			boolean paired = false; // Creates boolean to find if we've paired arc1 to arc2
			for(int i = 0; i < graph2.getArcs().size(); i++) {
				for(int match : matched) if(i == match) continue; // If we've already used this arc2 skip
				Arc arc2 = graph2.getArcs().get(i); // Get arc2 from graph2 bc we're not iterating the same way through this
				
				boolean nodesMatch = false;

				if(arc1.directional && arc2.directional) { // If they dont have the same reversible property try a different arc
					for(int node1 : arc1.nodes){
						boolean sameNodes = false;
						for(int node2 : arc2.nodes) {
						if(node1 == node2) sameNodes = true;
						}
						if(sameNodes) nodesMatch = true;
						else {
							nodesMatch = false;
							break;
						}
					}
				}
				else {
					nodesMatch = arc1.nodes[0] == arc2.nodes[0] && arc1.nodes[1] == arc2.nodes[1]; // TODO: related to aforementioned point 
				}
				
				boolean weightsMatch = arc1.weight == arc2.weight; // Checks the weights are equal
				
				
				if(nodesMatch && weightsMatch) { // Checks all properties of arcs to see if they are the same
					paired = true; // we've now paired arc1 to something
					matched.add(i); // making sure we're not going to reuse this arc2
					break; // Stop the inner loop, we've already found the arc again
				}
			}
			if(!paired) return false; // If we didnt find the same arc, the graphs cannot match
		}
		return true; //everything checks out, the graphs match!
	}
	
	public Graph() {
		nodesAmount = newNodeAmount();
		arcs = newArcs();
	}
	public Graph(String stringArcs) {
		this.arcs = new ArrayList<Arc>();
		if(stringArcs == null) return;
		
		if(stringArcs.charAt(1) == '\t' && stringArcs.charAt(2) == '|') { // Matrix confirmed
			if(stringArcs.charAt(0) == 'a') { // Adjacency matrix confirmed
				String[] mainBody = stringArcs.substring(stringArcs.indexOf('\n')+1).split("\r\n");
				this.nodesAmount = mainBody.length;
				for(int node1 = 0; node1 < nodesAmount; node1++) {
					
					String line = mainBody[node1];
					String[] temp = line.substring(line.indexOf('|')+1).split("\t");
					
					for(int node2 = 0; node2 < temp.length; node2++) {
						boolean connected = false;
						
						Scanner getConnected = new Scanner(temp[node2]);
						while(getConnected.hasNext()) {
							if(getConnected.hasNextInt()) {
								connected = getConnected.nextInt() >= 1;
								break;
							}
							else {
								getConnected.next();
							}
						}
						getConnected.close();
						
						if (connected) {
							int[] nodes = {node1, node2};
							this.arcs.add(new Arc(nodes, -1.0, true));
						}
					}
					
				}
			}
			else if (stringArcs.charAt(0) == 'd') { // Distance matrix confirmed
				String[] mainBody = stringArcs.substring(stringArcs.indexOf('\n')+1).split("\r\n");
				this.nodesAmount = mainBody.length;
				for(int node1 = 0; node1 < nodesAmount; node1++) {
					
					String line = mainBody[node1];
					String[] temp = line.substring(line.indexOf('|')+1).split("\t");
					
					for(int node2 = 0; node2 < temp.length; node2++) {
						double distance = -1.0;
						
						Scanner getDistance = new Scanner(temp[node2]);
						while(getDistance.hasNext()) {
							if(getDistance.hasNextDouble()) {
								distance = getDistance.nextDouble();
								break;
							}
							else {
								getDistance.next();
							}
						}
						getDistance.close();
						
						if (distance != -1.0) {
							int[] nodes = {node1, node2};
							this.arcs.add(new Arc(nodes, distance, true));
						}
					}
				}
			}
		}
		
		if(stringArcs.contains("-")) {
			String[] Arcs = stringArcs.split(", ");
			String allNodes = "";
			for(String arc : Arcs) {
				allNodes += (int) (arc.charAt(0) - 65) + "" + (int) (arc.charAt(1) - 65);
				int nodes[] = {(int) arc.charAt(0) - 65, (int) arc.charAt(1) - 65};
				double weight = -1;
				
				String weightSubstring = arc.substring(arc.indexOf('-') + 1);
				try(Scanner weightScanner = new Scanner(weightSubstring)){
					while(weightScanner.hasNext()) {
						if(weightScanner.hasNextDouble()) weight = weightScanner.nextDouble();
						else weightScanner.next();
					}
				}				
				
				this.arcs.add(new Arc(nodes, weight, false));
			}
			// loop through each node, check if theres a new largest node, (eg: E - 5 nodes, B - 2 nodes, etc)
			int largestNode = -1;
			for(int i = 0; i < allNodes.length(); i++) {
				if((allNodes.charAt(i)-47) > largestNode) largestNode = (allNodes.charAt(i)-47);
			}
			this.nodesAmount = largestNode;
		}
		
	}
	
	private int newNodeAmount() {
		System.out.print("Please enter amount of nodes: ");
		return input.nextInt();
	}
	
	public void addNewNode() {
		this.nodesAmount++;
	}
	
	public void addNewNodes(int amountOfNewNodes) {
		this.nodesAmount += amountOfNewNodes;
	}
	
	public void addNewArcs() {
		System.out.print("Input initial node: ");
		while(input.hasNext()) {
			if(input.hasNextInt()) {
				int temp = input.nextInt();
				System.out.print("Input connecting node: ");
				while(input.hasNext()) {
					if(input.hasNextInt()) {
						int temp2 = input.nextInt();
						if(temp < nodesAmount || temp2 < nodesAmount) {
							int[] nodes = {temp, temp2};
							this.arcs.add(new Arc(nodes, -1.0, true));
							break;
						}
					}
					input.next();
				}
				input.next();
			}
		}
	}
	
	private List<Arc> newArcs(){
		List<Arc> tempArcs = new ArrayList<Arc>();
		for(int i = 0; i < nodesAmount; i++) {
			for(int j = 0; j < nodesAmount; j++) {
				System.out.print("Is " + i + " connected to " + j + "? (y/n)");
				if(input.next().toLowerCase().equals("y") || input.next().toLowerCase().equals("yes")) {
					int[] nodes = {i, j};
					tempArcs.add(new Arc(nodes, -1.0, true));
				}
			}
		}
		return tempArcs;
	}
	
	public int getNodesAmount() {
		return this.nodesAmount;
	}
	
	public List<Arc> getArcs(){
		return this.arcs;
	}
	
	
	public boolean hasArc(int node1, int node2) {
		for(int i = 0; i < arcs.size(); i++) {
			int[] nodes = arcs.get(i).nodes;
			if (!arcs.get(i).directional) {
				if((nodes[0] == node1 && nodes[1] == node2) || (nodes[0] == node2 && nodes[1] == node1)) {
					return true;
				}
			} else {
				if(nodes[0] == node1 && nodes[1] == node2) {
					return true;
				}
			}
		}
		return false;
	}
	
	public double shortestArcLength(int node1, int node2) {
		for(int i = 0; i < arcs.size(); i++) {
			int[] nodes = arcs.get(i).nodes;
			if (!arcs.get(i).directional) {
				if((nodes[0] == node1 && nodes[1] == node2) || (nodes[0] == node2 && nodes[1] == node1)) {
					return arcs.get(i).weight;
				}
			} else if(nodes[0] == node1 && nodes[1] == node2) {
				return arcs.get(i).weight;
			}
		}
		return -1;
	}
	
	public void addArc(int node1, int node2) {
		int[] nodes = {node1, node2};
		this.arcs.add(new Arc(nodes, -1.0, true));
	}
	
	public void addArcs(int[] node1s, int[] node2s) {
		for(int i = 0; i < node1s.length; i++) {
			addArc(node1s[i], node2s[i]);
		}
	}
	
	public String toString() {
		String stringArcs = "";
		
		for(int i = 0; i < this.arcs.size(); i++) {
			Arc arc = this.arcs.get(i);
			stringArcs += (char) (arc.nodes[0] + 65) + "" + (char) (arc.nodes[1] + 65) + "-" + arc.weight;
			if(i != this.arcs.size()-1) stringArcs += ", ";
		}
		
		return stringArcs;
	}
	
	public String toAdjacencyMatrixString() {
		String matrixString = "a";
		
		for(int i = 0; i < nodesAmount; i++) { // for loop for the table header
			if(i == 0) matrixString += "\t|";
			matrixString += i;
			if(i != nodesAmount-1) matrixString += "\t";
			else matrixString += "\n";
		}
		
		for(int i = 0; i < nodesAmount; i++) {
			for(int j = 0; j < nodesAmount; j++) {
				if(j == 0) matrixString += i + "\t|";
				if(hasArc(i, j)) matrixString += "1\t";
				else matrixString += "0\t";
			}
			if(i != nodesAmount-1) {
				matrixString += "\n";
			}
		}
		
		return matrixString;
	}
	
	public void displayAdjacencyMatrix() {
		DisplayAdjacencyMatrix.main(null, this);
	}
	
	public String toDistanceMatrixString() {
		String distanceMatrix = "d";
		
		List<List<Double>> nodeDistance = new ArrayList<List<Double>>(); // Creates nested List, outer list represents each node, inner list represents distances between nodes
		
		for(int i = 0; i < nodesAmount; i++) {
			List<Double> distances = new ArrayList<Double>();
			for(int j = 0; j < nodesAmount; j++) {
				if(hasArc(i, j)) distances.add(shortestArcLength(i, j));
				else distances.add(null);
			}
			nodeDistance.add(distances);
		}
		
		for(int i = 0; i < nodesAmount; i++) { // for loop for the table header
			if(i == 0) distanceMatrix += "\t|";
			distanceMatrix += i;
			if(i != nodesAmount-1) distanceMatrix += "\t";
			else distanceMatrix += "\n";
		}
		
		for(int i = 0; i < nodesAmount; i++) {
			for(int j = 0; j < nodesAmount; j++) {
				if(j == 0) distanceMatrix += i + "\t|";
				if(nodeDistance.get(i).get(j) != null) distanceMatrix += nodeDistance.get(i).get(j) + "\t";
				else distanceMatrix += "-\t";
			}
			if(i != nodesAmount-1) {
				distanceMatrix += "\n";
			}
		}
		
		return distanceMatrix;
	}

	public void displayDistanceMatrix() {
		DisplayDistanceMatrix.main(null, this);
	}

}

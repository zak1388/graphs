package tests;

import main.Graph;

public class guis {

	public static void main(String[] args) {
		Graph test = new Graph("d	|0	1	2\r\n"
				+ "0	|10.0	8.0	9.0	\r\n"
				+ "1	|4.0	2.0	-	\r\n"
				+ "2	|6.0	-	5.0"); 
		
		test.displayAdjacencyMatrix();
		test.displayDistanceMatrix();
	}

}

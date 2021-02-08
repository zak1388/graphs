package tests;

import main.Graph;

public class Test {

	public static void main(String[] args) {
		// OLD dont use
		Graph john = new Graph("a	|0	1	2	\r\n"
				+ "0	|1	1	1	\r\n"
				+ "1	|1	1	0	\r\n"
				+ "2	|1	0	1");
		System.out.println(john.toString());
		System.out.println(john.toAdjacencyMatrixString());
		john.displayAdjacencyMatrix();
	}

}

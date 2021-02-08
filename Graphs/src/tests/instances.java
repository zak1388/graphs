package tests;

import main.Graph;

public class instances {

	public static void main(String[] args) {
		Graph test = new Graph("a	|0	1	2	\r\n"
				+ "0	|1	1	1	\r\n"
				+ "1	|1	1	0	\r\n"
				+ "2	|1	0	1");
		
		Graph test2 = new Graph("0 - 0\r\n"
				+ "0 - 1\r\n"
				+ "0 - 2\r\n"
				+ "1 - 0\r\n"
				+ "1 - 1\r\n"
				+ "2 - 0\r\n"
				+ "2 - 2");
		
		System.out.println("Success both graphs were created without error!"
				+ "\nTesting if they fundamentally match");
		System.out.println(Graph.compare(test, test2)? "Success! They are the same" : "Error! Something has gone wrong!");
		System.out.println("Exiting...");
		
		
	}

}

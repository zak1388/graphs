package tests;

import main.Graph;

public class matrices {

	public static void main(String[] args) {
		Graph test = new Graph("a	|0	1	2	\r\n"
				+ "0	|1	1	1	\r\n"
				+ "1	|1	1	0	\r\n"
				+ "2	|1	0	1");
		
		Graph test2 = new Graph("d	|0	1	2\r\n"
				+ "0	|10.0	8.0	9.0	\r\n"
				+ "1	|4.0	2.0	-	\r\n"
				+ "2	|6.0	-	5.0"); // TODO: put distance matrix here, prove it works
		
		System.out.println("Success both graphs were created without error!"
				+ "\nPrinting graphs");
		System.out.println(test.toAdjacencyMatrixString() + "\n\n" + test2.toDistanceMatrixString());
	}

}

package tests;

import main.Graph;

public class toStrings {

	public static void main(String[] args) {
		Graph test = new Graph("AA-10.0, AB-8.0, AC-9.0, BA-4.0, BB-2.0, CA-6.0, CC-5.0");
		
		test.displayDistanceMatrix();
		
		System.out.println(test.toString());
	}

}

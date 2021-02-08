package main;

public class Arc {
	public int[] nodes;
	public double weight;
	public boolean directional;
	
	public Arc(int[] nodes, double weight, boolean directional) {
		this.nodes = nodes;
		this.weight = weight;
		this.directional = directional;
	}
	
	public void switchDirection() {
		int temp = this.nodes[0];
		this.nodes[0] = this.nodes[1];
		this.nodes[1] = temp;
	}
}

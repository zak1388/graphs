package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;

public class DisplayAdjacencyMatrix {

	private JFrame frame;
	private JTable table;
	private Graph graph;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Graph graph) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DisplayAdjacencyMatrix window = new DisplayAdjacencyMatrix(graph);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DisplayAdjacencyMatrix(Graph graph) {
		this.graph = graph;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		table = new JTable(graph.getNodesAmount()+1, graph.getNodesAmount()+1);
		frame.getContentPane().add(table, BorderLayout.CENTER);
		for(int i = 0; i < graph.getNodesAmount(); i++) table.setValueAt(i, 0, i+1);
		for(int i = 0; i < graph.getNodesAmount(); i++) table.setValueAt(i, i+1, 0);
		for(int i = 0; i < graph.getNodesAmount(); i++) {
			for(int j = 0; j < graph.getNodesAmount(); j++) {
				table.setValueAt((graph.hasArc(i, j))? 1 : 0, i+1, j+1);
			}
		}
	}

}

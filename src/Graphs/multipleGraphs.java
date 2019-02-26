package Graphs;

import java.util.ArrayList;

public class multipleGraphs implements Runnable {
	private ArrayList<Graph> graphs;
	private int nodeAmt;
	private boolean barbasi;
	private int prob;
	int graphAmt;

	public multipleGraphs(boolean barbasi, int graphAmt, int nodeAmt, ArrayList<Graph> graphs) {
		this.nodeAmt = nodeAmt;
		this.graphAmt = graphAmt;
		this.barbasi = barbasi;
		this.graphs = graphs;
	}

	@Override
	public void run() {
		Graph graph = createGraph();
		graphs.add(graph);
		System.out.println(graphs.size());
	}

	public Graph createGraph() {
		Graph graph = new Graph();

		if (barbasi)
			graph.Barbasi(nodeAmt);
		else
			graph.randomFill(nodeAmt, prob);

		return graph;
	}

	public ArrayList<Graph> getGraphs() {
		return graphs;
	}

}

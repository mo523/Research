package Graphs;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class multipleGraphs {
	private ArrayList<Graph> graphs = new ArrayList<Graph>();
	private int nodeAmt;
	private boolean barbasi;
	private int prob;
	private int graphAmt;
	private ExecutorService threadPool;

	public multipleGraphs(boolean barbasi, int graphAmt, int nodeAmt, int threadAmt) {
		this.nodeAmt = nodeAmt;
		this.graphAmt = graphAmt;
		this.barbasi = barbasi;
		threadPool = Executors.newFixedThreadPool(threadAmt);

	}

	public void execute() {

		for (int i = 0; i < graphAmt; i++) {
			threadPool.submit(() -> {
				Graph graph = createGraph();
				graphs.add(graph);
			});

		}

		threadPool.shutdown();
		while (!threadPool.isTerminated()) {

		}
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

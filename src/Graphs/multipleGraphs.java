package Graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class multipleGraphs {
	private ArrayList<Graph> graphs = new ArrayList<Graph>();
	private int nodeAmt;
	private boolean barbasi;
	private int prob;
	private int graphAmt;
	private ExecutorService threadPool;
	private double[] avgStats = new double[10];
	private int edgeAmt;

	public multipleGraphs(boolean barbasi, int graphAmt, int nodeAmt, int threadAmt,int edgeAmt) {
		this.nodeAmt = nodeAmt;
		this.graphAmt = graphAmt;
		this.barbasi = barbasi;
		threadPool = Executors.newFixedThreadPool(threadAmt);
		this.edgeAmt=edgeAmt;
	}

	public void execute() {

		for (int i = 0; i < graphAmt; i++) {
			threadPool.submit(() -> {
				graphs.add(createGraph());
			});

		}

		threadPool.shutdown();
		while (!threadPool.isTerminated()) {

		}
		System.out.println("Graphs Created!!");

	}

	public Graph createGraph() {

		Graph graph = new Graph();
		if (barbasi)
			graph.Barbasi(nodeAmt, edgeAmt);
		else
			graph.randomFill(nodeAmt, prob);
		return graph;
	}

	public ArrayList<Graph> getGraphs() {
		return graphs;
	}

	public void subGraph(double p, boolean ran, int threadAmt) {
		threadPool = Executors.newFixedThreadPool(threadAmt);
		for (Graph graph : graphs) {
			threadPool.submit(() -> {
				graph.randomVac(p, ran);
			});

		}

		threadPool.shutdown();
		while (!threadPool.isTerminated()) {

		}

	}

	public double totalSubgraphAvg() {
		int subgraph = 0;
		for (Graph graph : graphs)
			subgraph += graph.getSubgraphs().size();
		return subgraph / graphs.size();
	}

	public double largestSubgraphAvg() {
		int subgraph = 0;
		for (Graph graph : graphs)
			subgraph += Collections.max(graph.getSubgraphs());
		return (subgraph / graphs.size());

	}

	public double[] getStats() {

		for (int i = 0; i < 10; i++) {
			{
				for (Graph graph : graphs)
					avgStats[i] += graph.getStats()[i];
				avgStats[i] /= graphs.size();
			}

		}

		threadPool.shutdown();
		while (!threadPool.isTerminated()) {

		}
		return avgStats;
	}
}

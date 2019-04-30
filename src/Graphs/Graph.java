
package Graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Graph
{
	private Random ran = new Random();
	private HashMap<Integer, Node> nodes = new HashMap<>();
	private HashSet<Tuple> allEdges = new HashSet<>();
	private ArrayList<Double> afis;
	private ArrayList<Double> gfis;
	private double[] stats;

	public Graph(HashSet<Tuple> graph)
	{
		allEdges = graph;
		rebuildGraph();
	}

	public Graph()
	{
	}

	private boolean addNode(int ID)
	{
		return nodes.putIfAbsent(ID, new Node(ID)) == null;
	}

	private void addEdge(Node n1, Node n2)
	{
		Tuple t = new Tuple(n1.getID(), n2.getID());
		allEdges.add(t);
		n1.addEdge(n2);
		n2.addEdge(n1);
	}

	// Used only for graph rebuilding (doesn't add tuples to set)
	private void addEdge(int k1, int k2)
	{
		Node n1 = nodes.get(k1);
		Node n2 = nodes.get(k2);
		n1.addEdge(n2);
		n2.addEdge(n1);
	}

	public void rebuildGraph()
	{
		stats = null;
		for (Tuple t : allEdges)
		{
			int k1 = t.getN1();
			int k2 = t.getN2();
			addNode(k1);
			addNode(k2);
			addEdge(k1, k2);
		}
	}

	// Public random fill which is passed onto private fills
	public void randomFill(boolean er, int nodeAmt, double prob)
	{
		if (er)
			fillER(nodeAmt, prob);
		else
			fillBA(nodeAmt, (int) prob);

	}

	private void fillER(int nodeAmt, double prob)
	{
		// Fill up the graph
		for (int i = 0; i < nodeAmt; i++)
			nodes.put(i, new Node(i));

		// Make some connections
		for (int i = 0; i < nodes.size() - 1; i++)
			for (int j = i + 1; j < nodes.size(); j++)
				if (ran.nextDouble() <= prob)
					addEdge(nodes.get(i), nodes.get(j));
	}

	private void fillBA(int nodeAmt, int edgeAmt)
	{
		ArrayList<Integer> probability;
		int index;
		for (int i = 0; i < edgeAmt; i++)
			nodes.put(i, new Node(i));

		index = ran.nextInt(nodes.size());
		for (Node n : nodes.values())
			if (n.getID() != index)
				addEdge(nodes.get(index), n);

		for (int nodeNumber = edgeAmt; nodeNumber < nodeAmt; nodeNumber++)
		{
			Node n = new Node(nodeNumber);

			probability = getNodeProbability();
			for (int edgeNumber = 0; edgeNumber < edgeAmt; edgeNumber++)
			{
				if (probability.size() - edgeNumber > 0)
					index = ran.nextInt(probability.size() - edgeNumber);
				else
					index = 0;
				final int secondNodeId = probability.get(index);
				addEdge(n, nodes.get(secondNodeId));
				probability.removeIf(id -> id.equals(secondNodeId));

			}
			nodes.put(nodeNumber, n);
		}
	}

	private ArrayList<Integer> getNodeProbability()
	{
		ArrayList<Integer> nodeProbabilityList = new ArrayList<Integer>();
		for (Node n : nodes.values())
			for (int i = 0; i <= n.getEdgeCount(); i++)
				nodeProbabilityList.add(n.getID());
		return nodeProbabilityList;
	}

	// Vaccination algorithm
	public void vaccinate(double prob, int friend)
	{
		ArrayList<Node> tempNodeList = new ArrayList<>();
		for (Node n : nodes.values())
			tempNodeList.add(n);
		Collections.shuffle(tempNodeList);
		int vacAmt = (int) (prob * tempNodeList.size());
		tempNodeList.stream().limit(vacAmt).forEach(n -> pickNode(n, friend));
	}

	private void pickNode(Node n, int friend)
	{
		if (friend == -1)
			friend = randomFriend();
		for (int i = 0; i < friend; i++)
			n = n.getBiggestFriend();
		quarNode(n);
	}
	
	private int randomFriend()
	{
		return ran.nextInt(7) + 3;
	}
	
	private void quarNode(Node n)
	{
		if (n == null)
			System.err.println("Error! vaccinating a null node for some reason");
		else
		{
			for (Node m : n.getEdges())
				m.removeEdge(n);
			n.resetEdges();
		}
	}

	// Public getters
	public ArrayList<Integer> getSubgraphs()
	{
		ArrayList<Integer> subgraphs = new ArrayList<>();
		Queue<Node> q = new LinkedList<>();
		for (Node n : nodes.values())
		{

			if (!n.colored())
			{
				q.add(n);
				int total = 0;
				while (!q.isEmpty())
				{
					Node e = q.poll();
					if (!e.colored())
					{
						e.color();
						total++;
						q.addAll(n.getEdges());
					}
				}
				subgraphs.add(total);
			}
		}

		nodes.values().forEach(n -> n.uncolor());
		Collections.sort(subgraphs);
		return subgraphs;
	}

	public int getTotalNodeCount()
	{
		return nodes.size();
	}

	public int getTotalEdgeCount()
	{
		// For a complete graph, edge count should be n(n-1)/2
		return allEdges.size();
	}

	public double getCurrentEdgeCount()
	{
		double total = 0;
		for (Node n : nodes.values())
			total += n.getEdgeCount();
		return total;

	}

	public double[] getStats()
	{
		if (stats == null)
			calculateStats();
		return stats;
	}

	private void calculateStats()
	{
		int nodeCount = nodes.size();
		Node min = nodes.values().iterator().next();
		Node max = min;
		double afi = 0;
		double gfi = 0;
		double m = Math.pow(allEdges.size(), -1);
		double sum1 = 0;
		double sum2 = 0;
		double sum3 = 0;
		afis = new ArrayList<>();
		gfis = new ArrayList<>();
		for (Node n : nodes.values())
		{
			int ne = n.getEdgeCount();
			double ta = n.getFi();
			afi += ta;
			afis.add(ta);
			ta = Math.log(ta);
			gfi += ta;
			gfis.add(ta);

			if (n.getEdgeCount() < min.getEdgeCount())
				min = n;
			if (n.getEdgeCount() > max.getEdgeCount())
				max = n;

			for (Node e : n.getEdges())
			{
				int ce = e.getEdgeCount();
				sum1 += ne * ce;
				sum2 += ne + ce;
				sum3 += Math.pow(ne, 2) + Math.pow(ce, 2);
			}
		}
		sum1 = sum1 * m * .5;
		sum2 = Math.pow((sum2 * .25 * m), 2);
		sum3 = sum3 * .25 * m;
		double topSum = sum1 - sum2;
		double botSum = sum3 - sum2;
		double asort = topSum / botSum;
		afi /= nodeCount;
		gfi /= nodeCount;

		stats = new double[10];
		stats[0] = nodeCount;
		stats[1] = allEdges.size();
		stats[2] = max.getID();
		stats[3] = max.getEdgeCount();
		stats[4] = min.getID();
		stats[5] = min.getEdgeCount();
		stats[6] = (double) allEdges.size() / nodeCount * 2;
		stats[7] = asort;
		stats[8] = afi;
		stats[9] = gfi;
	}

	public int getMaxEdges()
	{
		int max = 0;
		for (Node n : nodes.values())
			if (n.getEdgeCount() > max)
				max = n.getEdgeCount();
		return max;
	}

	public HashMap<Integer, Node> getNodes()
	{
		return nodes;
	}

	public HashSet<Tuple> getAllEdges()
	{
		return allEdges;
	}

	public ArrayList<Double> getAfis()
	{
		return afis;
	}

	public ArrayList<Double> getGfis()
	{
		return gfis;
	}
}

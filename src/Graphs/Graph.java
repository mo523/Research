
package Graphs;

import java.util.*;

public class Graph
{
	private Random ran = new Random();
	private HashMap<Integer, Node> nodes = new HashMap<>();
	private ArrayList<Double> afis;
	private ArrayList<Double> gfis;
	private HashSet<Tuple> allEdges = new HashSet<>();
	private static double[] stats;

	public Node addNode(int ID)
	{
		return nodes.putIfAbsent(ID, new Node(ID));
	}

	public void addEdge(Node n1, Node n2)
	{
		Tuple t = new Tuple(n1.getID(), n2.getID());
		allEdges.add(t);
		n1.addEdge(n2);
		n2.addEdge(n1);
	}

	public void addEdge(int k1, int k2)
	{
		Node n1 = nodes.get(k1);
		Node n2 = nodes.get(k2);
		addEdge(n1, n2);
	}

	public void rebuildGraph()
	{
		for (Tuple t : allEdges)
		{
			int k1 = t.getN1();
			int k2 = t.getN2();
			if (!nodes.containsKey(k1))
				addNode(k1);
			if (!nodes.containsKey(k2))
				addNode(k2);
			Node n1 = nodes.get(k1);
			Node n2 = nodes.get(k2);
			n1.addEdge(n2);
			n2.addEdge(n1);
		}

	}

	public HashMap<Integer, Node> getNodes()
	{
		return nodes;
	}

	public void randomFill(boolean er, int nodeAmt, double prob)
	{
		if (er)
			fillER(nodeAmt, prob);
		else
			fillBA(nodeAmt, (int) prob);

	}

	private void fillER(int nodeAmt, double prob)
	{
		for (int i = 0; i < nodeAmt; i++)
			nodes.put(i, new Node(i));
		for (Node n : nodes.values())
			for (Node c : nodes.values())
				if (!c.equals(n) && ran.nextDouble() <= prob)
					addEdge(n, c);
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

	public void vaccinate(double prob, boolean ran)
	{
		ArrayList<Node> tempNodeList = new ArrayList<>();
		for (Node n : nodes.values())
			tempNodeList.add(n);
		Collections.shuffle(tempNodeList);
		int vacAmt = (int) (prob * tempNodeList.size());
		tempNodeList.stream().limit(vacAmt).forEach(n -> quarNode(n, ran));
	}

	private void quarNode(Node n, boolean ran)
	{
		if (n != null)
		{
			if (ran)
			{
				for (Node e : n.getEdges())
					e.removeEdge(n);
				n.resetEdges();
			}
			else
			{
				Node m = new Node(-1);
				for (Node e : n.getEdges())
					if (e.getEdgeCount() > m.getEdgeCount())
						m = e;
				for (Node e : m.getEdges())
					e.removeEdge(m);
				m.resetEdges();
			}
		}
		else
			System.err.println("Error! vaccinating a null node for some reason");
	}

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

	/**
	 * @return Integer graph size Calculated by getting the HashMap size
	 */
	public int getTotalNodeCount()
	{
		return nodes.size();
	}

	/**
	 * @return Integer of total edge count which is calculated by the tuple
	 * collection
	 */
	public int getTotalEdgeCount()
	{
		return allEdges.size();
	}

	public double[] getStats()
	{
		int nodeCount = nodes.size();
		double[] stats = new double[10];
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
		return stats;
	}

	public ArrayList<Integer> getNodeProbability()
	{
		ArrayList<Integer> nodeProbabilityList = new ArrayList<Integer>();
		for (Node n : nodes.values())
			for (int i = 0; i <= n.getEdgeCount(); i++)
				nodeProbabilityList.add(n.getID());
		return nodeProbabilityList;
	}

	public ArrayList<Double> getAfis()
	{
		return afis;
	}

	public ArrayList<Double> getGfis()
	{
		return gfis;
	}

	public HashSet<Tuple> getAllEdges()
	{
		return allEdges;
	}

}
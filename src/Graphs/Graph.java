
package Graphs;

import java.util.*;

public class Graph {
	private Random ran = new Random();
	private HashMap<Integer, Node> nodes = new HashMap<>();

	public void addEdge(Node n1, Node n2) {
		n1.addEdge(n2);
		n2.addEdge(n1);

	}

	public void addNode(int ID) {
		nodes.put(ID, new Node(ID));
	}

	public HashMap<Integer, Node> getNodes() {
		return nodes;
	}

	public void randomFill(int nodeAmt, int prob) {
		for (int i = 0; i < nodeAmt; i++)
			nodes.put(i, new Node(i));
		System.out.print("here");
		for (Node n : nodes.values())
			for (Node c : nodes.values())
				if (!c.equals(n) && ran.nextInt(prob) == 0)
					addEdge(n, c);
	}

	public void Barbasi(int nodeAmt) {
		ArrayList<Integer> probability;
		int index;
		for (int i = 0; i < 3; i++)
			nodes.put(i, new Node(i));

		index = ran.nextInt(nodes.size());
		System.out.println(index);
		for (Node n : nodes.values())
			if (n.getID() != index)
				addEdge(nodes.get(index), n);

		for (int nodeNumber = 3; nodeNumber < nodeAmt; nodeNumber++) {
			Node n = new Node(nodeNumber);

			probability = getNodeProbability();
			for (int edgeNumber = 0; edgeNumber < 3; edgeNumber++) {
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

	public int getTotalNodeCount() {
		int total = 0;
		for (Node n : nodes.values())
			total += n.getEdgeCount();
		return total;
	}

	public int getTotalEdgeCount() {
		int total = 0;
		for (Node n : nodes.values())
			total += n.getEdgeCount();
		total /= 2;
		return total;
	}

	public double getAsort() {
		double m = Math.pow(getTotalEdgeCount(), -1);
		double sum1 = 0;
		double sum2 = 0;
		double sum3 = 0;

		for (Node n : nodes.values())
			for (Node c : n.getEdges()) {
				int ne = n.getEdgeCount();
				int ce = c.getEdgeCount();
				sum1 += ne * ce;
				sum2 += ne + ce;
				sum3 += Math.pow(ne, 2) + Math.pow(ce, 2);
			}

		sum1 /= 2;
		sum2 /= 2;
		sum3 /= 2;
		sum2 *= .5 * m;
		sum3 /= 2;
		sum2 = Math.pow(sum2, 2);
		double r = 0;
		r = m * sum1 - sum2;
		r /= (m * sum3 - sum2);
		return r;
	}

	public ArrayList<Integer> getNodeProbability() {
		{
			ArrayList<Integer> nodeProbabilityList = new ArrayList<Integer>();
			for (Node n : nodes.values())
				for (int i = 0; i <= n.getEdgeCount(); i++)
					nodeProbabilityList.add(n.getID());

			return nodeProbabilityList;
		}
	}
}

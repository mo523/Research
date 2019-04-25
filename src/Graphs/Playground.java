package Graphs;

import java.util.HashMap;

public class Playground
{
	public static void main(String args[])
	{
		// int total = 0;
		// for (int i = 0; i < 100; i++)
		// {
		// System.out.println("Starting graph " + i);
		// Graph graph = new Graph();
		// graph.randomFill(true, 100, .5);
		// total += graph.getTotalEdgeCount();
		// }
		// System.out.println(total / 100d);

		Graph graph = new Graph();
		graph.randomFill(true, 10000, .05);

		HashMap<Integer, Node> nodes = graph.getNodes();
		int total = 0;
		for (Node n : nodes.values())
			total += n.getEdgeCount();

		System.out.println(total / 2);
		System.out.println(graph.getTotalEdgeCount());
		Display.displayStats(graph.getStats());
		for (Node n : nodes.values())
			for (Node e : n.getEdges())
			{
				Tuple t = new Tuple(n.getID(), e.getID());
				if (!graph.getAllEdges().contains(t))
					System.out.println("Curr Node: " + n.getID() + "\t" + t);
			}
	}
}

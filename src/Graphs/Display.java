package Graphs;

import java.util.*;

public class Display
{
	static void displayGraph( HashSet<Node> nodes )
	{
		System.out.println("\nNode\t:\tEdges");
		for ( Node n : nodes )
		{
			System.out.print(n.getID() + "\t:\t " + n.getEdgeCount() + " { ");
			for ( Node c : n.getEdges() )
				System.out.print(c.getID() + ", ");
			System.out.println("}");
		}
	}
	
	static void displayStats(HashSet<Node> nodes, int totalNodes)
	{
		int nodeCount = 0;
		Node most;
		Node least = most = nodes.iterator().next();
		for (Node n : nodes)
		{
			if ( n.getEdgeCount() > most.getEdgeCount() )
				most = n;
			if ( n.getEdgeCount() < least.getEdgeCount() )
				least = n;
			nodeCount++;
		}
		System.out.println("\nMost Connections:\t" + most.getID() + "\t" + most.getEdgeCount());
		System.out.println("Least Connections:\t" + least.getID() + "\t" + least.getEdgeCount());
		System.out.println("Average Connections:\t" + ( (double) totalNodes / nodeCount ));
	}
}

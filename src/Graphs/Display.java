package Graphs;

//import java.util.*;

public class Display
{
	static void displayGraph( HashMapSet nodes )
	{
		System.out.println("\nNode\t:\tEdges");
		for ( Node n : nodes.getNodes() )
		{
			System.out.print(n.getID() + "\t:\t " + n.getEdgeCount() + " { ");
			for ( Node c : n.getEdges() )
				System.out.print(c.getID() + ", ");
			System.out.println("}");
		}
	}
	
	static void displayStats(HashMapSet nodes, int totalNodes)
	{
		int nodeCount = 0;
		Node most;
		Node least = most = nodes.getNodes().iterator().next();
		for (Node n : nodes.getNodes())
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

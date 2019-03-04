package Graphs;

import java.util.*;

public class Display
{
	static void displayGraph( HashMap<Integer, Node> nodes )
	{
		System.out.println("\nNode\t:\tEdges");
		for ( Node n : nodes.values() )
		{
			System.out.print(n.getID() + "\t:\t " + n.getEdgeCount() + " : " + n.getFi() + " { ");
			for ( Node c : n.getEdges() )
				System.out.print(c.getID() + ", ");
			System.out.println("}");
		}
	}

	static void displayStats( double[] stats )
	{
		System.out.println("\nTotal Nodes:\t\t" + stats[0]);
		System.out.println("Total Edges:\t\t" + stats[1]);
		System.out.println("Most Connections:\t" + stats[2] + "\t" + stats[3]);
		System.out.println("Least Connections:\t" + stats[4] + "\t" + stats[5]);
		System.out.println("Average Connections:\t" + stats[6]);
		System.out.println("Graph assortativity:\t" + stats[7]);
		System.out.println("AFI:\t\t\t" + stats[8]);
		System.out.println("GFI:\t\t\t" + stats[9]);

	}
}

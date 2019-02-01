package Graphs;

import java.util.*;

public class Trial
{
	public static void main( String[] args )
	{
		Scanner kb = new Scanner(System.in);
		LinkedHashSet<Node> nodes = new LinkedHashSet<>();
		Random ran = new Random();

		System.out.println("Node amount?");
		int nodeAmt = kb.nextInt();

		System.out.println("Connection Probability?");
		System.out.println("As 1/n  (1/10 = .1, 1/80 = .0125, etc.)");
		int prob = kb.nextInt();
		for ( int i = 0; i < nodeAmt; i++ )
			nodes.add(new Node("" + i));

		for ( Node n : nodes )
			for ( Node c : nodes )
				if ( !c.equals(n) && ran.nextInt(prob) == 0 )
				{
					n.addEdge(c);
					c.addEdge(n);
				}

		Node most = nodes.iterator().next();
		Node least = most;
		int total = 0;
		// System.out.println("Node\t:\tFi ratio\t:\tConnections");
		System.out.println("Node\t:\t Connections");
		for ( Node n : nodes )
		{
			if ( n.getEdgeCount() > most.getEdgeCount() )
				most = n;
			if ( n.getEdgeCount() < least.getEdgeCount() )
				least = n;
			total += n.getEdgeCount();

			// int neighbors = 0;
			// for ( Node c : n.getNodes() )
			// neighbors += c.getSize();

			System.out.print(n.getID() + "\t:\t " + n.getEdgeCount() + " ~ { ");
			for ( Node c : n.getEdges() )
				System.out.print(c.getID() + ", ");
			System.out.println("}");
		}

		// Spacer
		System.out.println("\n\n\n");
		// Recaps

		System.out.println("Most Connections:\t" + most.getID() + "\t" + most.getEdgeCount());
		System.out.println("Least Connections:\t" + least.getID() + "\t" + least.getEdgeCount());
		System.out.println("Average Connections:\t" + ( (double) total / nodeAmt ));

		kb.close();
	}
}

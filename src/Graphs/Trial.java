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
					n.addConnection(c);
					c.addConnection(n);
				}

		Node most = nodes.iterator().next();
		Node least = most;
		int total = 0;
//		System.out.println("Node\t:\tFi ratio\t:\tConnections");
		System.out.println("Node\t:\t Connections");
		for ( Node n : nodes )
		{
			if ( n.getSize() > most.getSize() )
				most = n;
			if ( n.getSize() < least.getSize() )
				least = n;
			total += n.getSize();

//			int neighbors = 0;
//			for ( Node c : n.getNodes() )
//				neighbors += c.getSize();

			System.out.print(
					n.getID() + "\t:\t " + n.getSize() + " ~ { ");
			for ( Node c : n.getNodes() )
				System.out.print(c.getID() + ", ");
			System.out.println("}");
		}

		// Spacer
		System.out.println("\n\n\n");
		// Recaps

		System.out.println("Most Connections:\t" + most.getID() + "\t" + most.getSize());
		System.out.println("Least Connections:\t" + least.getID() + "\t" + least.getSize());
		System.out.println("Average Connections:\t" + ( (double)total / nodeAmt ));
		
		
		
		kb.close();
	}
}

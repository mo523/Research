package Graphs;

import java.util.*;

public class Graph
{
	private boolean undirected;
	private Random ran = new Random();
	private HashSet<Node> nodes = new HashSet<>();

	public Graph( boolean undirected )
	{
		this.undirected = undirected;
	}

	public boolean addEdge( Node n1, Node n2 )
	{
		if ( undirected )
			n2.addEdge(n1);
		return n1.addEdge(n2);
	}

	public boolean removeEdge( Node n1, Node n2 )
	{
		if ( undirected )
			n2.removeEdge(n1);
		return n1.removeEdge(n2);
	}

	public boolean addNode( Node n )
	{
		return nodes.add(n);
	}

	public boolean removeNode( Node n )
	{
		return nodes.remove(n);
	}

	public HashSet<Node> getNodes()
	{
		return nodes;
	}

	public void randomFill( int nodeAmt, int prob )
	{
		for ( int i = 0; i < nodeAmt; i++ )
			nodes.add(new Node("" + i));
		for ( Node n : nodes )
			for ( Node c : nodes )
				if ( !c.equals(n) && ran.nextInt(prob) == 0 )
					addEdge(n, c);
	}

	public int getTotalNodeCount()
	{
		int total = 0;
		for (Node n : nodes)
			total += n.getEdgeCount();
		return total;
	}
}

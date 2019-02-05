package Graphs;

import java.util.*;

public class Graph
{
	private boolean undirected;
	private Random ran = new Random();
	private HashMapSet nodes = new HashMapSet();

	public Graph( boolean undirected )
	{
		this.undirected = undirected;
	}

	public void addEdge( Node n1, Node n2 )
	{

		n1.addEdge(n2);
		if ( undirected )
			n2.addEdge(n1);
	}

	public void removeEdge( String ID1, String ID2 )
	{
		nodes.getNode(ID1).removeEdge(ID2);
		if ( undirected )
			nodes.getNode(ID2).removeEdge(ID1);
	}

	public Node addNode( String ID )
	{
		return nodes.addNode(ID);
	}

	public void removeNode( String ID )
	{
		for ( Node n : nodes.getNodes() )
			n.removeEdge(ID);
		nodes.remove(ID);
	}

	public HashMapSet getNodes()
	{
		return nodes;
	}

	public void randomFill( int nodeAmt, int prob )
	{
		for ( int i = 1; i <= nodeAmt; i++ )
			nodes.addNode("" + i);
		System.out.print("here");
		for ( Node n : nodes.getNodes() )
			for ( Node c : nodes.getNodes() )
				if ( !c.equals(n) && ran.nextInt(prob) == 0 )
					addEdge(n, c);
	}

	public int getTotalNodeCount()
	{
		int total = 0;
		for ( Node n : nodes.getNodes() )
			total += n.getEdgeCount();
		return total;
	}
}

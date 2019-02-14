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

	public int getTotalEdgeCount()
	{
		int total = 0;
		for ( Node n : nodes.getNodes() )
			total += n.getEdgeCount();
		if ( undirected )
			total /= 2;
		return total;
	}

	public double getAsort()
	{
		double m = Math.pow(getTotalEdgeCount(), -1);
		double sum1 = 0;
		double sum2 = 0;
		double sum3 = 0;

		for ( Node n : nodes.getNodes() )
			for ( Node c : n.getEdges() )
			{
				int ne = n.getEdgeCount();
				int ce = c.getEdgeCount();
				sum1 += ne * ce;
				sum2 += ne + ce;
				sum3 += Math.pow(ne, 2) + Math.pow(ce, 2);
			}

		if ( undirected )
		{
			sum1 /= 2;
			sum2 /= 2;
			sum3 /= 2;
		}
		sum2 *= .5 * m;
		sum3 /= 2;
		sum2 = Math.pow(sum2, 2);
		double r = 0;
		r = m * sum1 - sum2;
		r /= ( m * sum3 - sum2 );
		return r;
	}
}

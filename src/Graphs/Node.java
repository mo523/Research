package Graphs;

import java.util.*;

public class Node
{
	private HashSet<Node> edges = new HashSet<>();
	private int ID;

	public Node( int ID )
	{
		this.ID = ID;
	}

	public int getID()
	{
		return ID;
	}

	/**
	 * Adds a directional edge to the specified node
	 *
	 * @param n
	 *            Reference of node to edge to
	 * @return
	 */
	public void addEdge( Node n )
	{
		edges.add(n);
	}

	/**
	 * Returns the HashSet of this node's edges
	 */
	public HashSet<Node> getEdges()
	{
		return edges;
	}

	/**
	 * Returns the edge count of this node Calculated by getting the size of the
	 * HashSet
	 */
	public int getEdgeCount()
	{
		return edges.size();
	}

	public double getFi()
	{
		int ee = 0;
		for ( Node n : edges )
		{
			ee += n.getEdgeCount();
		}
		return (double) ee / edges.size() / edges.size();
	}
}

package Graphs;

import java.util.*;

public class Node
{
	private HashSet<Node> edges = new HashSet<>();
	private int ID;
	private boolean color = false;
	// private int EEC = 0;

	/**
	 * Node constructor
	 * 
	 * @param ID
	 *            Integer to reference this node in the graphs HashMap
	 */
	public Node( int ID )
	{
		this.ID = ID;
	}

	/**
	 * 
	 * @return Integer ID
	 */
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
	public void addEdge( Node e )
	{
		edges.add(e);
	}

	public void removeEdge( Node e )
	{
		edges.remove(e);
	}

	public void resetEdges()
	{
		edges = new HashSet<>();
	}

	public void color()
	{
		color = true;
	}

	public void uncolor()
	{
		color = false;
	}

	public boolean colored()
	{
		return color;
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

	/**
	 * Calculates this nodes FI Iterates over the set of all it's edges and
	 * increments a counter based off their total edges (gotten by
	 * Node.getEdgeCount())
	 * 
	 * @return FI as a double
	 */
	public double getFi()
	{
		double EEC = 0;
		for ( Node n : edges )
			EEC += n.getEdgeCount();
		return (double) EEC / edges.size() / edges.size();
	}
}
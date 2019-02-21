package Graphs;

import java.util.*;

public class Node
{
	private HashSet<Node> edges = new HashSet<>();
	private int ID;
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
		// e.incrementEEC(this.getEdgeCount() - 1);
		// edges.forEach(n -> n.incrementEEC());
	}

	// public void incrementEEC()
	// {
	// EEC++;
	// }
	// public void incrementEEC(int i)
	// {
	// EEC += i;
	// }
	//
	// public int getEEC()
	// {
	// return EEC;
	// }

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
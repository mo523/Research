package Graphs;

import java.util.*;

public class Node
{
	private HashMapSet edges = new HashMapSet();
	private String ID;

	/**
	 * Constructs a Node with the specified ID
	 *
	 * @param ID
	 *            a string that will be used to identify the node
	 */
	public Node( String ID )
	{
		this.ID = ID;
	}

	/**
	 * Removes a directional edge with the specified ID
	 *
	 * @param ID
	 *            ID of node to be unedged
	 */
	public void removeEdge( String ID )
	{
		edges.remove(ID);
	}

	/**
	 * Adds a directional edge to the specified node
	 *
	 * @param n
	 *            Reference of node to edge to
	 */
	public void addEdge( Node n )
	{
		edges.addEdge(n);
	}

	/**
	 * Returns the HashSet of this node's edges
	 */
	public HashSet<Node> getEdges()
	{
		return edges.getNodes();
	}
	/**
	 * Returns the edge count of this node
	 * Calculated by getting the size of the HashSet
	 */
	public int getEdgeCount()
	{
		return edges.getSize();
	}
	/**
	 * Returns the ID of this node
	 */
	public String getID()
	{
		return ID;
	}

}

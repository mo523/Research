package Graphs;

import java.util.*;

public class Node
{
	private HashMapSet edges = new HashMapSet();
	private String ID;

	public Node( String ID )
	{
		this.ID = ID;
	}

	public void removeEdge( String ID )
	{
		edges.remove(ID);
	}

	public void addEdge( Node n )
	{
		edges.addEdge(n);
	}

	public HashSet<Node> getEdges()
	{
		return edges.getNodes();
	}

	public int getEdgeCount()
	{
		return edges.getSize();
	}

	public String getID()
	{
		return ID;
	}

}

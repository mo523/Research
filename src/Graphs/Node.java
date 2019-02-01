package Graphs;

import java.util.*;

@SuppressWarnings( "rawtypes" )
public class Node implements Comparable
{
	private HashSet<Node> edges = new HashSet<>();
	private String ID;
	private int edgeCount = 0;

	public Node( String ID )
	{
		this.ID = ID;
	}

	public boolean removeEdge( Node n )
	{
		boolean success = edges.remove(n);
		if ( success )
			edgeCount--;
		return success;
	}

	public boolean addEdge( Node n )
	{
		boolean success = edges.add(n);
		if ( success )
			edgeCount++;
		return success;
	}

	public HashSet<Node> getEdges()
	{
		return edges;
	}

	public String getID()
	{
		return ID;
	}

	public int getEdgeCount()
	{
		return edgeCount;
	}

	@Override
	public int compareTo( Object o )
	{
		return ID.compareTo(( (Node) o ).getID());
	}

}

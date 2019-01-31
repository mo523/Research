package Graphs;

import java.util.*;

@SuppressWarnings( "rawtypes" )
public class Node implements Comparable
{
	private TreeSet<Node> nodes = new TreeSet<>();
	private String ID;
	private int size = 0;

	public Node()
	{
		ID = "" + System.nanoTime();
	}

	public Node( String ID )
	{
		this.ID = ID;
	}

	public void removeConnection( Node n )
	{
		nodes.remove(n);
		size--;
	}

	public void addConnection( Node n )
	{
		nodes.add(n);
		size++;
	}

	public TreeSet<Node> getNodes()
	{
		return nodes;
	}

	public String getID()
	{
		return ID;
	}

	public int getSize()
	{
		return size;
	}

	@Override
	public int compareTo( Object o )
	{
		return ID.compareTo(((Node) o).getID());
	}

}

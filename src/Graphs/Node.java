package Graphs;

import java.util.*;

public class Node
{
	private HashSet<Node> edges = new HashSet<>();
	private int ID;
	private boolean color = false;
	private Node biggestFriend;
	private int lastNodeCount;

	public Node(int ID)
	{
		this.ID = ID;
	}

	public int getID()
	{
		return ID;
	}

	public Node getBiggestFriend()
	{
		if (biggestFriend == null || lastNodeCount != edges.size())
		{
			Node m = new Node(-1);
			for (Node n : edges)
				if (n.getEdgeCount() > m.getEdgeCount())
					m = n;
			biggestFriend = m;
			lastNodeCount = edges.size();
		}
		return biggestFriend;
	}

	public HashSet<Node> getEdges()
	{
		return edges;
	}

	public int getEdgeCount()
	{
		return edges.size();
	}

	public double getFi()
	{
		double EEC = 0;
		for (Node n : edges)
			EEC += n.getEdgeCount();
		return (double) EEC / edges.size() / edges.size();
	}

	public void addEdge(Node e)
	{
		edges.add(e);
	}

	public void removeEdge(Node e)
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

}
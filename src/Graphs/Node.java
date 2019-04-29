package Graphs;

import java.util.*;

public class Node
{
	private HashSet<Node> edges = new HashSet<>();
	private int ID;
	private boolean color = false;

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
		Node biggestFriend = new Node(-1);
		for (Node n : edges)
			if (n.getEdgeCount() > biggestFriend.getEdgeCount())
				biggestFriend = n;
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
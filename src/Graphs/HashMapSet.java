package Graphs;

import java.util.*;
public class HashMapSet
{
	private HashMap<String, Node> nodeIDs = new HashMap<>();
	private HashSet<Node> nodes = new HashSet<>();
	
	public Node addNode(String ID)
	{
		Node n = new Node(ID);
		nodes.add(n);
		nodeIDs.put(ID, n);
		return n;
	}
	public boolean addEdge(Node n)
	{
		nodeIDs.put(n.getID(), n);
		return nodes.add(n);
	}
	
	public boolean remove(String ID)
	{
		Node n = nodeIDs.remove(ID);
		return nodes.remove(n);
	}
	public Node getNode(String ID)
	{
		return nodeIDs.get(ID);
	}
	public HashSet<Node> getNodes()
	{
		return nodes;
	}
	public int getSize()
	{
		return nodes.size();
	}
}

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
	public void addEdge(Node n)
	{
		nodes.add(n);
		nodeIDs.put(n.getID(), n);
	}
	
	public void remove(String ID)
	{
		Node n = nodeIDs.remove(ID);
		nodes.remove(n);
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

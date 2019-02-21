package Graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class SaveFunc
{
	public static void createFile( String fileName, HashMap<Integer, Node> graph ) throws FileNotFoundException
	{
		PrintWriter file = new PrintWriter(fileName);
		for ( Node n : graph.values() )
		{
			file.print(n.getID());
			for ( Node c : n.getEdges() )
				file.print(" " + c.getID());
			file.println();
		}
		file.close();
	}

	public static Graph importFile( String fileName )
	{
		Graph g = new Graph();
		try
		{
			Scanner kb = new Scanner(new File(fileName));
			ArrayList<Integer[]> nes = new ArrayList<>();
			do
			{
				String[] temp = kb.nextLine().split(" ");
				nes.add(new Integer[temp.length]);
				for ( int i = 0; i < temp.length; i++ )
					nes.get(nes.size() - 1)[i] = Integer.parseInt(temp[i]);
			} while ( kb.hasNext() );

			for ( Integer[] sa : nes )
				g.addNode(sa[0]);

			for ( Integer[] sa : nes )
			{
				Node n1 = g.getNodes().get(sa[0]);
				for ( int i = 1; i < sa.length; i++ )
					g.addEdge(n1, g.getNodes().get(sa[i]));
			}

			kb.close();
		} catch ( FileNotFoundException e )
		{
			e.printStackTrace();
		}
		return g;
	}

	public static Graph importSNAP( String fName )
	{
		Graph graph = new Graph();
		Scanner kb = null;
		try
		{
			kb = new Scanner(new File(fName));

			do
			{
				String[] in = kb.nextLine().split("	");
				int n1 = Integer.parseInt(in[0]);
				int n2 = Integer.parseInt(in[1]);
				if ( !graph.getNodes().containsKey(n1) )
					graph.addNode(n1);
				if ( !graph.getNodes().containsKey(n2) )
					graph.addNode(n2);
				graph.addEdge(n1, n2);
			} while ( kb.hasNextLine() );

		} catch ( FileNotFoundException e )
		{
			e.printStackTrace();
		} finally
		{
			kb.close();
		}

		return graph;
	}

}
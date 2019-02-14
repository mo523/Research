package Graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class SaveFunc
{
	public static void createFile( String fileName, HashSet<Node> graph ) throws FileNotFoundException
	{
		fileName = "C:\\users\\moshe\\desktop\\g.txt";
		PrintWriter file = new PrintWriter(fileName);

		// file.println("{");
		// for ( Node n : graph )
		// {
		// file.println("\tID: " + n.getID());
		// file.println("\tEdges:");
		// file.println("\t{");
		// for ( Node c : n.getEdges() )
		// file.println("\t\t" + c.getID());
		// file.println("\t}");
		// }

		for ( Node n : graph )
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
		fileName = "C:\\users\\moshe\\desktop\\g.txt";
		Graph g = new Graph(true);
		try
		{
			Scanner kb = new Scanner(new File(fileName));
			ArrayList<String[]> nes = new ArrayList<>();
			do
			{
				nes.add(kb.nextLine().split(" "));
			} while ( kb.hasNext() );

			for ( String[] sa : nes )
				g.addNode(sa[0]);
				
			for ( String[] sa : nes )
			{
				Node n1 = g.getNodes().getNode(sa[0]);
				for ( int i = 1; i < sa.length; i++ )
					g.addEdge(n1, g.getNodes().getNode(sa[i]));
			}

			kb.close();
		} catch ( FileNotFoundException e )
		{
			e.printStackTrace();
		}
		return g;
	}

	public static Graph fbook()
	{
		Graph g = new Graph(true);

		for ( int i = 0; i < 4039; i++ )
			g.addNode("" + i);
		try
		{
			Scanner kb = new Scanner(new File("c:\\users\\moshe\\desktop\\fbook.txt"));

			do
			{
				String[] nn = kb.nextLine().split(" ");
				Node n1 = g.getNodes().getNode(nn[0]);
				Node n2 = g.getNodes().getNode(nn[1]);
				g.addEdge(n1, n2);
			} while ( kb.hasNext() );

			kb.close();
		} catch ( FileNotFoundException e )
		{
			e.printStackTrace();
		}

		return g;
	}
}
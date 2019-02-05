package Graphs;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class SaveFunc
{
	public static void createFile( String fileName, HashSet<Node> graph ) throws FileNotFoundException
	{
		PrintWriter file = new PrintWriter(fileName);

		file.println("{");
		for ( Node n : graph )
		{
			file.println("\tID: " + n.getID());
			file.println("\tEdges:");
			file.println("\t{");
			for ( Node c : n.getEdges() )
				file.println("\t\t" + c.getID());
			file.println("\t}");
		}
		file.close();
	}
}
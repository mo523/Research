package Graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class SaveFunc
{

	public static Graph importGraph()
	{
		Graph graph = new Graph();
		Scanner kb = new Scanner(System.in);

		String fName;
		do
		{
			fName = shortcut(kb.nextLine());
		} while (!Files.exists(Path.of(fName)));
		kb.close();
		
		try (Scanner file = new Scanner(new File(fName));)
		{

			do
			{
				String[] in = file.nextLine().split("\\s+");
				int n1 = Integer.parseInt(in[0]);
				int n2 = Integer.parseInt(in[1]);
				if (!graph.getNodes().containsKey(n1))
					graph.addNode(n1);
				if (!graph.getNodes().containsKey(n2))
					graph.addNode(n2);
				graph.addEdge(n1, n2);
			} while (kb.hasNextLine());

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return graph;
	}

	public static void saveTemp(HashSet<Tuple> edges, String fName)
	{
		fName = shortcut(fName);
		PrintWriter file;
		try
		{
			file = new PrintWriter(fName + "temp");
			for (Tuple t : edges)
				file.println(t.getN1() + " " + t.getN2());
			file.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void saveGraph(ArrayList<Double> afis, ArrayList<Double> gfis, String fName, double[] stats)
	{
		fName = shortcut(fName);
		PrintWriter file;

		try
		{
			file = new PrintWriter(fName + "_stats");

			file.println("\nTotal Nodes:\t\t" + stats[0]);
			file.println("Total Edges:\t\t" + stats[1]);
			file.println("Most Connections:\t" + stats[2] + "\t" + stats[3]);
			file.println("Least Connections:\t" + stats[4] + "\t" + stats[5]);
			file.println("Average Connections:\t" + stats[6]);
			file.println("Graph assortativity:\t" + stats[7]);
			file.println("AFI:\t\t\t" + stats[8]);
			file.println("GFI:\t\t\t" + stats[9]);
			file.close();

			file = new PrintWriter((fName + "_afis"));
			for (double d : afis)
				file.println(d);
			file.close();

			file = new PrintWriter((fName + "_gfis"));
			for (double d : gfis)
				file.println(d);
			file.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void saveMenu(Graph graph)
	{
		Scanner kb = new Scanner(System.in);
		System.out.println("Filename and location?");
		String fName = kb.nextLine();
		kb.close();
		SaveFunc.saveGraph(graph.getAfis(), graph.getGfis(), fName, graph.getStats());

	}

	private static String shortcut(String fName)
	{
		if (fName.charAt(0) == ',')
		{
			String m = "C:/Users/moshe/Documents/College/MCO 493 Special Research Project/Graphs";
			fName = fName.replace(',', '/');
			m += fName;
			fName = m;
		}
		return fName;
	}

}
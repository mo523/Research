package Graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Scanner;

public class SaveFunc
{
	private File directory;

	public SaveFunc(String directory)
	{
		directory = shortcut(directory);
		directory += new SimpleDateFormat("MM.dd.yy HH.mm a").format(Calendar.getInstance().getTime()) + "/";
		new File(directory).mkdir();
		this.directory = new File(directory);
		System.out.println("\nFiles will be saved to " + this.directory);
	}

	public void saveStats(Graph graph)
	{
		PrintWriter file;
		double[] stats = graph.getStats();
		try
		{
			file = new PrintWriter(directory + "/stats");

			file.println("\nTotal Nodes:\t\t" + stats[0]);
			file.println("Total Edges:\t\t" + stats[1]);
			file.println("Most Connections:\t" + stats[2] + "\t" + stats[3]);
			file.println("Least Connections:\t" + stats[4] + "\t" + stats[5]);
			file.println("Average Connections:\t" + stats[6]);
			file.println("Graph assortativity:\t" + stats[7]);
			file.println("AFI:\t\t\t" + stats[8]);
			file.println("GFI:\t\t\t" + stats[9]);
			file.close();

			file = new PrintWriter(directory + "/afis");
			for (double d : graph.getAfis())
				file.println(d);
			file.close();

			file = new PrintWriter(directory + "/gfis");
			for (double d : graph.getGfis())
				file.println(d);
			file.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	public void saveGraph(Graph graph)
	{
		try (PrintWriter file = new PrintWriter(directory + "/graph");)
		{
			for (Tuple t : graph.getAllEdges())
				file.println(t.getN1() + " " + t.getN2());
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	public static Graph importGraph(String fName)
	{
		HashSet<Tuple> graph = new HashSet<>();
		try (Scanner file = new Scanner(new File(fName));)
		{
			do
			{
				String[] in = file.nextLine().split("\\s+");
				int n1 = Integer.parseInt(in[0]);
				int n2 = Integer.parseInt(in[1]);
				graph.add(new Tuple(n1, n2));
			} while (file.hasNextLine());
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return new Graph(graph);
	}

	private static String shortcut(String directory)
	{
		if (directory.equalsIgnoreCase("m"))
			return "C:/Users/moshe/Documents/College/MCO 493 Special Research Project/Graphs/";
		else if (directory.equalsIgnoreCase("y"))
			return "C:/Users/ysontag?????????";
		else if (directory.equalsIgnoreCase("cd"))
			return System.getProperty("user.dir") + "\\";
		return directory;
	}

	public static boolean exists(String directory)
	{
		return Files.exists(Path.of(shortcut(directory)));
	}
}
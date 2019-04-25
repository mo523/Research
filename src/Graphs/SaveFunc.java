package Graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

			file.println("\nTotal Nodes:\t\t\t" + stats[0]);
			file.println("Total Edges:\t\t\t" + stats[1]);
			file.println("Most Connections:\t\t" + stats[3]);
			file.println("Least Connections:\t\t" + stats[5]);
			file.println("Average Connections:\t" + stats[6]);
			file.println("Graph assortativity:\t" + stats[7]);
			file.println("AFI:\t\t\t\t\t" + stats[8]);
			file.println("GFI:\t\t\t\t\t" + stats[9]);
			file.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	public void saveFis(Graph graph)
	{
		PrintWriter file;

		try
		{
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
			// TODO Auto-generated catch block
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

	public void saveInfo(double[] info, LocalDateTime start, LocalDateTime end, int times)
	{
		try (PrintWriter file = new PrintWriter(directory + "/info");)
		{
			file.println("Tests started at: " + start.format(DateTimeFormatter.ofPattern("M/dd h:mm:ss a"))
					+ " and finished at " + end.format(DateTimeFormatter.ofPattern("M/dd h:mm:ss a"))
					+ " for a total time of: " + getDuration(start, end) + "\r\n\r\n");

			file.println("Friend tests:");
			file.println("Max node: \t\t" + info[0]);
			file.println("Avg node: \t\t" + info[1]);
			file.println("Max subgraph: \t" + info[2]);
			file.println("Avg subgraph: \t" + info[3]);
			file.println("\r\nRandom tests:");
			file.println("Max node: \t\t" + info[4]);
			file.println("Avg node: \t\t" + info[5]);
			file.println("Max subgraph: \t" + info[6]);
			file.println("Avg subgraph: \t" + info[7]);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	private static StringBuilder getDuration(LocalDateTime start, LocalDateTime end)
	{
		StringBuilder sb = new StringBuilder();
		long hours = ChronoUnit.HOURS.between(start, end);
		long mins = ChronoUnit.MINUTES.between(start, end);
		long secs = ChronoUnit.SECONDS.between(start, end);

		if (hours > 0)
			sb.append(hours + " hour");
		if (hours > 1)
			sb.append("s");
		if (hours > 0 && mins > 0)
			sb.append(" & ");
		if (mins > 0)
			sb.append(mins + " minute");
		if (mins > 1)
			sb.append("s");
		if ((mins > 0 || hours > 0) && secs > 0)
			sb.append(" & ");
		if (secs > 0)
			sb.append(secs + " second");
		if (secs > 1)
			sb.append("s");
		return sb;
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
			return System.getProperty("user.dir") + "/";
		return directory;
	}

	public static boolean exists(String directory)
	{
		return Files.exists(Path.of(shortcut(directory)));
	}

	public void createSubfolder(String subName)
	{
		directory = new File(directory.toString() + "/" + subName);
		directory.mkdir();
	}

	public void goToParent()
	{
		directory = new File(directory.getParent());
	}
}
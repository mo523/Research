package Graphs;

import java.util.*;
import java.util.concurrent.*;

public class Program
{
	static ArrayList<Graph> graphs = new ArrayList<Graph>();
	static ExecutorService threadPool;
	static Scanner kb;
	static Graph graph;
	static double[] stats;

	public static void main(String[] args)
	{
		kb = new Scanner(System.in);
		initialMenu();
		kb.close();
	}

	private static void initialMenu()
	{
		System.out.println("Welcome to the graphing thingy!");
		int choice = 0;
		do
		{
			System.out.println("\nWhat would you like to do?");
			System.out.println(
					"0. Quit\n1. Create a new graph\n2. Import a graph \n3. Create multiple graphs\n5. Vaccination tests");
			choice = choiceValidator(0, 5);
			switch (choice)
			{
				case 1:
					newGraphMenu();
					break;
				case 2:
					importMenu();
					break;
				case 3:
					multipleGraphMenu();
					break;
				case 4:
					choice = 0;
					tenTest();
				case 5:
					vaccMenu();
				default:
					break;
			}
			if (choice != 0)
				graphMenu();
		} while (choice != 0);
	}

	private static void vaccMenu()
	{
		graph = new Graph();
		// System.out.println("Node amount?");
		// int nAmt = kb.nextInt();
		int nAmt = 1000;
		System.out.println("Connect probabilty (ER)");
		double cp = kb.nextDouble();
		System.out.println("Initial connection amount (BA)");
		int ica = kb.nextInt();
		System.out.println("Repeat amount?");
		int ra = kb.nextInt();
		System.out.println("Prob amount");
		double pa = kb.nextDouble();
		boolean er = true;

		for (int n = 0; n < 5; n++)
		{
			System.out.println("\nNode Amount: " + nAmt + "\n------------");
			for (int i = 0; i < 2; i++)
			{
				graph = new Graph();
				boolean ran = true;
				if (er)
					graph.randomFill(nAmt, cp);
				else
					graph.Barbasi(nAmt, ica);
				HashSet<Tuple> edges = graph.getAllEdges();

				if (er)
					System.out.println("Erdos Reyni");
				else
					System.out.println("Barbasi Albert");
				for (int j = 0; j < 2; j++)
				{
					int total = 0;
					if (ran)
						System.out.print("\tRandom Vaccinations:\t\t");
					else
						System.out.print("\tMax Friend Vaccinations:\t");
					for (int k = 0; k < ra; k++)
					{
						graph = new Graph(edges);
						graph.vaccinate(pa, ran);
						total += Collections.max(graph.getSubgraphs());
					}
					System.out.println(total / ra);
					ran = false;
				}
				er = false;
			}
			nAmt *= 10;
		}

	}

	private static void multipleGraphMenu()
	{

		int choice = 0;
		multipleGraphs graphs = multipleGraph();
		do
		{
			System.out.println("\nWhat would you like to do?");
			System.out
					.println("0. Quit\n1. Create new graphs\n2. View Avg Stats \n3. Save graphs \n4. Vaccinate Graphs");
			choice = choiceValidator(0, 4);
			switch (choice)
			{
				case 1:
					graphs = multipleGraph();
					break;
				case 2:
					Display.displayStats(graphs.getStats());
				case 3:
					break;
				case 4:
					System.out.println("How many threads?");
					int threadAmt = kb.nextInt();
					System.out.println(graphs.getGraphs().size());
					System.out.println("1. Random\n2. Friends");
					boolean ran = choiceValidator(1, 2) == 1;
					System.out.println("What % of peeps get vacced?");
					double p = kb.nextDouble();
					graphs.subGraph(p, ran, threadAmt);
					System.out.println("\nAvg Total Subgraphs: " + graphs.totalSubgraphAvg());
					System.out.println("Avg Biggest Subgraph: " + graphs.largestSubgraphAvg());

				default:
					break;
			}
		} while (choice != 0);
	}

	private static void newGraphMenu()
	{
		int choice;
		graph = new Graph();
		stats = null;
		System.out.println("\nHow would you like to fill the graph?\n1. Barbashi-Albert\n2. Random");
		choice = choiceValidator(1, 2);
		if (choice == 2)
			randomFillMenu();
		else
			barbashiFillMenu();
	}

	private static void importMenu()
	{
		System.out.println("\nWhat type of file?\n1. Moshe's\n2. SNAP");
		int choice = choiceValidator(1, 2);
		System.out.println("File name?");
		String fName = kb.nextLine();
		switch (choice)
		{
			case 1:
				graph = SaveFunc.importFile(fName);
				break;
			case 2:
				graph = SaveFunc.importSNAP(fName);
				break;
		}
		stats = graph.getStats();
		Display.displayStats(stats);
		SaveFunc.saveGraph(graph.getAfis(), graph.getGfis(), fName, stats);
	}

	private static void graphMenu()
	{
		int choice;
		do
		{
			System.out.println("\nWhat would you like to do with the graph?");
			System.out.println(
					"0. Main Menu\n1. View graph & stats\n2. View stats only\n3. Save Graph\n4. Vaccinate graph");
			choice = choiceValidator(0, 10);
			switch (choice)
			{
				case 1:
					Display.displayGraph(graph.getNodes());
				case 2:
					if (stats == null)
						stats = graph.getStats();
					Display.displayStats(graph.getStats());
					break;
				case 3:
					saveMenu();
					break;
				case 4:
					subgraphMenu();
					break;
				case 5:
					SaveFunc.saveTemp(graph.getAllEdges(), ",");
				default:
					break;
			}
		} while (choice != 0);
	}

	private static void subgraphMenu()
	{
		System.out.println("What % of peeps get vacced?");
		double p = kb.nextDouble();
		ArrayList<Integer> subgraphs;

		System.out.println("Random");
		graph.vaccinate(p, true);
		subgraphs = graph.getSubgraphs();
		System.out.println("Total Subgraphs: " + subgraphs.size());
		System.out.println("Biggest Subgraph: " + Collections.max(subgraphs));

		graph = new Graph(graph.getAllEdges());
		System.out.println("MAx friend");
		graph.vaccinate(p, false);
		subgraphs = graph.getSubgraphs();
		System.out.println("Total Subgraphs: " + subgraphs.size());
		System.out.println("Biggest Subgraph: " + Collections.max(subgraphs));

	}

	private static void randomFillMenu()
	{
		System.out.println("\nHow many nodes?");
		int nodeAmt = choiceValidator(1, Integer.MAX_VALUE);
		System.out.println("\nConnection Probability?");
		graph.randomFill(nodeAmt, kb.nextDouble());

	}

	private static void barbashiFillMenu()
	{
		System.out.println("\nHow many nodes?");
		int nodeAmt = choiceValidator(1, Integer.MAX_VALUE);
		System.out.println("\nHow many edges per new node?");
		int edgeAmt = choiceValidator(1, Integer.MAX_VALUE);
		graph.Barbasi(nodeAmt, edgeAmt);
	}

	private static void saveMenu()
	{
		System.out.println("Filename and location?");
		String fName = kb.nextLine();
		if (stats == null)
			stats = graph.getStats();
		SaveFunc.saveGraph(graph.getAfis(), graph.getGfis(), fName, stats);
	}

	private static int choiceValidator(int low, int high)
	{
		int choice = low;
		do
		{
			if (choice < low || choice > high)
				System.out.println("\nInvalid choice!\n" + low + " - " + high);
			choice = kb.nextInt();
		} while (choice < low || choice > high);
		kb.nextLine();
		return choice;
	}

	public static multipleGraphs multipleGraph()
	{

		boolean barbasi;
		System.out.println("Enter number of nodes");
		int nodes = choiceValidator(1, Integer.MAX_VALUE);
		System.out.println("Enter number of edges per new node");
		int edgeAmt = choiceValidator(1, Integer.MAX_VALUE);
		System.out.println("\nHow would you like to fill these graphs?\n1. Barbashi-Albert\n2. Random");
		int choice = choiceValidator(1, 2);
		if (choice == 1)
			barbasi = true;
		else
			barbasi = false;

		System.out.println("Enter number of graphs");
		int graphAmt = choiceValidator(1, Integer.MAX_VALUE);

		System.out.println("Enter number of threads");
		int threads = choiceValidator(1, Integer.MAX_VALUE);
		multipleGraphs multiple = new multipleGraphs(barbasi, graphAmt, nodes, threads, edgeAmt);
		multiple.execute();
		return multiple;
	}

	public static Graph createGraph(int nodeAmt, int edgeAmt)
	{
		Graph graph = new Graph();

		graph.Barbasi(nodeAmt, edgeAmt);

		return graph;
	}

	private static void tenTest()
	{
		System.out.println("How many Graphs?");
		int gAmt = choiceValidator(1, Integer.MAX_VALUE);
		System.out.println("How many nodes?");
		int nAmt = choiceValidator(1, Integer.MAX_VALUE);
		System.out.println("How many edges per new node?");
		int eAmt = choiceValidator(1, Integer.MAX_VALUE);
		System.out.println("1. Barbasi\n2. Random");
		int gGen = choiceValidator(1, 2);
		for (int i = 0; i < gAmt; i++)
		{
			Graph g = new Graph();
			if (gGen == 1)
				g.Barbasi(nAmt, eAmt);
			else
				g.randomFill(nAmt, 80);
			stats = g.getStats();
			SaveFunc.saveGraph(g.getAfis(), g.getGfis(), ",test" + (i + 1), stats);
			System.out.println("Done " + (i + 1));
		}
		System.out.println("COMPLETE");
	}
}

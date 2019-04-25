package Graphs;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Program
{
	private static Scanner kb;
	private static Graph graph;
	private static SaveFunc save;

	public static void main(String[] args)
	{
		if (args.length == 8)
			singleThreadedTests(args);
		else
		{
			initialMenu();
			kb.close();
		}
	}

	// Menus

	private static void initialMenu()
	{
		System.out.println("Welcome to the graphing thingy!");
		setupFileOutput();
		int choice = 0;
		do
		{
			choice = choiceValidator(
					"\nWhat would you like to do?\n0. Quit\n1. Create a single random graph"
							+ "\n2. Import a graph \n3. Multithreaded graph tests\n4. Single threaded graph tests",
					0, 5);
			switch (choice)
			{
				case 1:
					newGraphMenu();
					graphMenu();
					break;
				case 2:
					importMenu();
					graphMenu();
					break;
				case 3:
					multithreadedMenu();
					break;
				case 4:
					singleThreadedMenu();
				default:
					break;
			}
		} while (choice != 0);
	}

	private static void importMenu()
	{
		System.out.println("What is the full path to the file you would like to import");
		String fName;
		boolean exists = false;
		do
		{
			fName = kb.nextLine();
			if (!SaveFunc.exists(fName))
				System.out.println("Warning! File does nor exist");
			else
				exists = true;
		} while (!exists);
		graph = SaveFunc.importGraph(fName);
	}

	private static void setupFileOutput()
	{
		System.out.println(
				"\nWhich folder would you like to use for the current session?\nYou can input 'cd' for the current working directory");
		String directory;
		boolean exists = false;
		do
		{
			directory = kb.nextLine();
			if (!SaveFunc.exists(directory))
				System.out.println("Warning! Not a good directory");
			else
				exists = true;
		} while (!exists);
		save = new SaveFunc(directory);
	}

	// Single graph menus
	private static void newGraphMenu()
	{
		graph = new Graph();
		int nodeAmt = choiceValidator("\nHow many nodes?", 1, Integer.MAX_VALUE);
		if (choiceValidator("\nHow would you like to fill the graph?\n1. Barbashi-Albert\n2. Random", 1, 2) == 2)
			graph.randomFill(true, nodeAmt, choiceValidator("\nConnection probability?", 0d, 1d));
		else
			graph.randomFill(false, nodeAmt, choiceValidator("\nConnection amount?", 0, Integer.MAX_VALUE));
	}

	private static void graphMenu()
	{
		int choice;
		do
		{
			choice = choiceValidator("\nWhat would you like to do with the graph?\n0. Main Menu"
					+ "\n1. View stats\n2. Vaccinate graph\n3. Save stats\n4. Save graph & stats", 0, 4);
			switch (choice)
			{
				case 1:
					Display.displayStats(graph.getStats());
					break;
				case 2:
					vaccinationMenu();
					break;
				case 3:
					save.saveStats(graph);
					break;
				case 4:
					save.saveGraph(graph);
					save.saveStats(graph);
					break;
				default:
					break;
			}
		} while (choice != 0);
	}

	private static void vaccinationMenu()
	{
		System.out.println("What % of peeps get vacced?");
		double p = kb.nextDouble();
		ArrayList<Integer> subgraphs;

		System.out.println("Random");
		graph.vaccinate(p, true);
		subgraphs = graph.getSubgraphs();
		System.out.println("Total Subgraphs: " + subgraphs.size());
		System.out.println("Biggest Subgraph: " + Collections.max(subgraphs));

		graph.rebuildGraph();
		System.out.println("Max friend");
		graph.vaccinate(p, false);
		subgraphs = graph.getSubgraphs();
		System.out.println("Total Subgraphs: " + subgraphs.size());
		System.out.println("Biggest Subgraph: " + Collections.max(subgraphs));

	}

	// Multiple single thread menus
	private static void singleThreadedMenu()
	{

		int repeatOnNew = choiceValidator("How many new graphs should each test be ran on?", 1, Integer.MAX_VALUE);
		int repeatOnReload = choiceValidator("How many times should the vaccination be ran per graph", 1,
				Integer.MAX_VALUE);
		int initalNodeAmount = choiceValidator("Initial node amount?", 1, Integer.MAX_VALUE);
		int maxNodeAmount = choiceValidator("Maximum node amount?", 1, Integer.MAX_VALUE);
		int increaseAmount = choiceValidator("Increase amount?", 1, Integer.MAX_VALUE);
		int initialConnectionAmount = choiceValidator("Initial connection amount", 1, Integer.MAX_VALUE);
		int maxConnectionAmount = choiceValidator("Maximum connection", 1, Integer.MAX_VALUE);
		double vacPercent = choiceValidator("Percent of population that gets vaccinated?", 0d, 1d);

		singleThreadedTests(repeatOnNew, repeatOnReload, initalNodeAmount, maxNodeAmount, increaseAmount,
				initialConnectionAmount, maxConnectionAmount, vacPercent);

	}

	private static void singleThreadedTests(int repeatOnNew, int repeatOnReload, int initalNodeAmount,
			int maxNodeAmount, int increaseAmount, int initialConnectionAmount, int maxConnectionAmount,
			double vacPercent)
	{
		LocalDateTime startOut = LocalDateTime.now();
		double[] allInfo = new double[8];
		for (int nodeAmt = initalNodeAmount; nodeAmt <= maxNodeAmount; nodeAmt += increaseAmount)
		{
			for (int connectionAmount = initialConnectionAmount; connectionAmount <= maxConnectionAmount; connectionAmount++)
			{
				LocalDateTime startMid = LocalDateTime.now();
				save.createSubfolder("/n=" + nodeAmt + ", m=" + connectionAmount);
				double[] totalInfo = new double[8];
				for (int k = 0; k < repeatOnNew; k++)
				{
					LocalDateTime startIn = LocalDateTime.now();
					graph = new Graph();
					graph.randomFill(false, nodeAmt, connectionAmount);
					save.createSubfolder("graph " + (k + 1));
					save.saveGraph(graph);
					save.saveStats(graph);
					double[] info = new double[8];
					for (int l = 0; l < repeatOnReload; l++)
					{
						graph.vaccinate(vacPercent, false);
						ArrayList<Integer> subGraphs = graph.getSubgraphs();
						info[2] += Collections.max(subGraphs);
						info[3] += subGraphs.size();
						info[0] += graph.getMaxEdges();
						info[1] += (graph.getCurrentEdgeCount() / graph.getTotalNodeCount());
						graph.rebuildGraph();
						graph.vaccinate(vacPercent, true);
						subGraphs = graph.getSubgraphs();
						info[6] += Collections.max(subGraphs);
						info[7] += subGraphs.size();
						info[4] += graph.getMaxEdges();
						info[5] += (graph.getCurrentEdgeCount() / graph.getTotalNodeCount());

						graph.rebuildGraph();
					}
					LocalDateTime endIn = LocalDateTime.now();

					for (int i = 0; i < info.length; i++)
					{
						info[i] /= repeatOnReload;
						totalInfo[i] += info[i];
					}
					save.saveInfo(info, startIn, endIn, repeatOnReload);

					save.goToParent();
				}
				LocalDateTime endOut = LocalDateTime.now();
				for (int i = 0; i < totalInfo.length; i++)
				{
					totalInfo[i] /= repeatOnNew;
					allInfo[i] = totalInfo[i];
				}
				save.saveInfo(totalInfo, startMid, endOut, repeatOnNew);
				save.goToParent();
			}
		}
		save.goToParent();
		LocalDateTime endOut = LocalDateTime.now();
		for (int i = 0; i < allInfo.length; i++)
			allInfo[i] /= ((maxConnectionAmount - initialConnectionAmount) * (maxNodeAmount - initalNodeAmount));
		save.saveInfo(allInfo, startOut, endOut, 0);
	}

	private static void singleThreadedTests(String[] args)
	{
		singleThreadedTests(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
				Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]),
				Integer.parseInt(args[6]), Double.parseDouble(args[7]));
	}

	// Multiple multithreaded menus
	public static multipleGraphs multipleGraph()
	{
		double ESProb = 0;
		boolean barbasi;
		int nodes = choiceValidator("Enter number of nodes", 1, Integer.MAX_VALUE);
		int edgeAmt = choiceValidator("Enter number of edges per new node", 1, Integer.MAX_VALUE);
		int choice = choiceValidator("\nHow would you like to fill these graphs?\n1. Barbashi-Albert\n2. Random", 1, 2);
		if (choice == 1)
			barbasi = true;
		else
		{
			barbasi = false;
			System.out.println();
			ESProb = choiceValidator("\nConnection Probability?", 0d, 1d);
		}

		int graphAmt = choiceValidator("Enter number of graphs", 1, Integer.MAX_VALUE);
		int threads = choiceValidator("Enter number of threads", 1, Integer.MAX_VALUE);
		multipleGraphs multiple = new multipleGraphs(barbasi, graphAmt, nodes, threads, edgeAmt, ESProb);
		multiple.execute();
		return multiple;
	}

	private static void multithreadedMenu()
	{

		int choice = 0;
		multipleGraphs graphs = multipleGraph();
		do
		{
			System.out.println();
			System.out.println();
			choice = choiceValidator("\nWhat would you like to do?\n0. Quit\n1. Create new graphs"
					+ "\n2. View Avg Stats \n3. Save graphs \n4. Vaccinate Graphs", 0, 5);
			switch (choice)
			{
				case 1:
					graphs = multipleGraph();
					break;
				case 2:
					Display.displayStats(graphs.getStats());
					break;
				case 3:
					break;
				case 4:
					System.out.println("How many threads?");
					int threadAmt = kb.nextInt();
					System.out.println(graphs.getGraphs().size());
					boolean ran = choiceValidator("1. Random\n2. Friends", 1, 2) == 1;
					System.out.println("What % of peeps get vacced?");
					double p = kb.nextDouble();
					graphs.subGraph(p, ran, threadAmt);
					System.out.println("\nAvg Total Subgraphs: " + graphs.totalSubgraphAvg());
					System.out.println("Avg Biggest Subgraph: " + graphs.largestSubgraphAvg());
					break;
				case 5:
					ArrayList<String> results = new ArrayList<String>();
					System.out.println("How many tests?");
					int numTests = kb.nextInt();
					System.out.println("How many threads?");
					threadAmt = kb.nextInt();
					System.out.println(graphs.getGraphs().size());
					ran = choiceValidator("1. Random\n2. Friends", 1, 2) == 1;
					System.out.println("What % of peeps get vacced?");
					p = kb.nextDouble();
					for (int i = 0; i < numTests; i++)
					{
						multipleGraphs testCases = new multipleGraphs(graphs);
						testCases.subGraph(p, ran, threadAmt);
						System.out.println("test: " + i);
						for (Graph g : testCases.getGraphs())
							results.add(g.getSubgraphs().size() + " " + Collections.max(g.getSubgraphs()) + " ");

						results.add("* ");

					}

					try
					{
						FileWriter fileWriter = new FileWriter(
								"C:\\Users\\sam\\OneDrive\\Documents\\Yoel\\Tests\\test");
						for (String s : results)
						{
							fileWriter.write(s);
						}
						fileWriter.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}

				default:
					break;
			}
		} while (choice != 0);
	}

	// Input validation & prompts
	private static int choiceValidator(String msg, int low, int high)
	{
		return (int) choiceValidator(msg, (double) low, (double) high);
	}

	private static double choiceValidator(String msg, double low, double high)
	{
		System.out.println(msg);
		double choice;
		do
		{
			choice = kb.nextDouble();
			if (choice < low || choice > high)
				System.out.println("\nInvalid choice!\n" + low + " - " + high);
		} while (choice < low || choice > high);
		kb.nextLine();
		return choice;
	}
}

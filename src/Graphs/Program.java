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
		if (args.length == 9)
		{
			save = new SaveFunc("cd");
			singleThreadedTests(args);
		}
		else
		{
			kb = new Scanner(System.in);
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
		graph.vaccinate(p, 0);
		subgraphs = graph.getSubgraphs();
		System.out.println("Total Subgraphs: " + subgraphs.size());
		System.out.println("Biggest Subgraph: " + Collections.max(subgraphs));

		graph.rebuildGraph();
		System.out.println("Max friend");
		graph.vaccinate(p, 1);
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
		int maxFriendZone = choiceValidator("Max friend zone?", 1, Integer.MAX_VALUE) + 1;
		double vacPercent = choiceValidator("Percent of population that gets vaccinated?", 0d, 1d);

		singleThreadedTests(maxFriendZone, repeatOnNew, repeatOnReload, initalNodeAmount, maxNodeAmount, increaseAmount,
				initialConnectionAmount, maxConnectionAmount, vacPercent);
	}

	private static void singleThreadedTests(int maxFriendZone, int maxRepeatOnNew, int maxRepeatOnReload,
			int initalNodeAmount, int maxNodeAmount, int increaseAmount, int initialConnectionAmount,
			int maxConnectionAmount, double vacPercent)
	{
		LocalDateTime startOut = LocalDateTime.now();
		double[][] allInfo = new double[maxFriendZone][4];
		for (int nodeAmt = initalNodeAmount; nodeAmt <= maxNodeAmount; nodeAmt += increaseAmount)
		{
			for (int connectionAmount = initialConnectionAmount; connectionAmount <= maxConnectionAmount; connectionAmount++)
			{
				LocalDateTime startMid = LocalDateTime.now();
				save.createSubfolder("/n=" + nodeAmt + ", m=" + connectionAmount);
				double[][][] totalInfo = new double[maxFriendZone][maxRepeatOnNew][4];
				for (int repeatOnNew = 0; repeatOnNew < maxRepeatOnNew; repeatOnNew++)
				{
					LocalDateTime startIn = LocalDateTime.now();
					graph = new Graph();
					graph.randomFill(false, nodeAmt, connectionAmount);
					save.createSubfolder("graph " + (repeatOnNew + 1));
					save.saveGraph(graph);
					save.saveStats(graph);
					double[][][] info = new double[maxFriendZone][maxRepeatOnReload][4];
					for (int friendZone = 0; friendZone < maxFriendZone; friendZone++)
					{

						for (int repeatOnReload = 0; repeatOnReload < maxRepeatOnReload; repeatOnReload++)
						{
							graph.vaccinate(vacPercent, friendZone);
							ArrayList<Integer> subGraphs = graph.getSubgraphs();
							info[friendZone][repeatOnReload][0] = graph.getMaxEdges();
							info[friendZone][repeatOnReload][1] = (graph.getCurrentEdgeCount()
									/ graph.getTotalNodeCount());
							info[friendZone][repeatOnReload][2] = Collections.max(subGraphs);
							info[friendZone][repeatOnReload][3] = subGraphs.size();
							graph.rebuildGraph();
						}
					}
					LocalDateTime endIn = LocalDateTime.now();

					double[][] totals = new double[maxFriendZone][4];
					for (int i = 0; i < info.length; i++)
						for (int j = 0; j < info[i].length; j++)
							for (int k = 0; k < info[i][j].length; k++)
								totals[i][k] += info[i][j][k];

					for (int i = 0; i < totals.length; i++)
						for (int j = 0; j < totals[i].length; j++)
						{
							totals[i][j] /= maxRepeatOnReload;
							totalInfo[i][repeatOnNew][j] = totals[i][j];
						}

					save.saveInfo(info, totals, startIn, endIn);

					save.goToParent();
				}
				LocalDateTime endOut = LocalDateTime.now();
				double[][] totals = new double[maxFriendZone][4];

				for (int i = 0; i < totalInfo.length; i++)
					for (int j = 0; j < totalInfo[i].length; j++)
						for (int k = 0; k < totalInfo[i][j].length; k++)
							totals[i][k] += totalInfo[i][j][k];

				for (int i = 0; i < totals.length; i++)
					for (int j = 0; j < totals[i].length; j++)
					{
						totals[i][j] /= maxRepeatOnNew;
						allInfo[i][j] += totals[i][j];
					}
				save.saveInfo(totalInfo, totals, startMid, endOut);
				save.goToParent();
			}
		}
		LocalDateTime endOut = LocalDateTime.now();
		int x = (((maxNodeAmount - initalNodeAmount) / increaseAmount + 1)
				* (maxConnectionAmount - initialConnectionAmount + 1));
		System.out.println(x);
		for (int i = 0; i < allInfo.length; i++)
			for (int j = 0; j < allInfo[i].length; j++)
				allInfo[i][j] /= x;
		save.saveInfo(allInfo, startOut, endOut);

		System.out.println("Complete!!");
	}

	private static void singleThreadedTests(String[] args)
	{
		singleThreadedTests(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
				Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]),
				Integer.parseInt(args[6]), Integer.parseInt(args[7]), Double.parseDouble(args[8]));
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
					int friend = choiceValidator("1. Random\n2. Friends", 1, 2);
					System.out.println("What % of peeps get vacced?");
					double p = kb.nextDouble();
					graphs.subGraph(p, friend, threadAmt);
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
					friend = choiceValidator("1. Random\n2. Friends", 1, 2);
					System.out.println("What % of peeps get vacced?");
					p = kb.nextDouble();
					for (int i = 0; i < numTests; i++)
					{
						multipleGraphs testCases = new multipleGraphs(graphs);
						testCases.subGraph(p, friend, threadAmt);
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

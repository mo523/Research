package Graphs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Program
{
	private static Scanner kb;
	private static Graph graph;

	public static void main(String[] args)
	{
		kb = new Scanner(System.in);
		initialMenu();
		kb.close();
	}

	// Menus

	private static void initialMenu()
	{
		System.out.println("Welcome to the graphing thingy!");
		int choice = 0;
		do
		{
			choice = choiceValidator(
					"\nWhat would you like to do?\n0. Quit\n1. Create a single graph"
							+ "\n2. Import a graph \n3. Multithreaded graph tests\n4. Single threaded graph tests",
					0, 5);
			switch (choice)
			{
				case 1:
					newGraphMenu();
					graphMenu();
					break;
				case 2:
					graph = SaveFunc.importGraph();
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

	// Single graph menus
	private static void newGraphMenu()
	{
		graph = new Graph();
		int nodeAmt = choiceValidator("\nHow many nodes?", 1, Integer.MAX_VALUE);
		if (choiceValidator("\nHow would you like to fill the graph?\n1. Barbashi-Albert\n2. Random", 1, 2) == 1)
			graph.randomFill(true, nodeAmt, choiceValidator("\nConnection Probability?", 0d, 1d));
		else
			graph.randomFill(false, nodeAmt, choiceValidator("\nConnection Amount?", 0, 1));
	}

	private static void graphMenu()
	{
		int choice;
		do
		{
			choice = choiceValidator("\nWhat would you like to do with the graph?\n0. Main Menu"
					+ "\n1. View stats\n2. Save Graph\n3. Vaccinate graph", 0, 3);
			switch (choice)
			{
				case 1:
					Display.displayStats(graph.getStats());
					break;
				case 2:
					SaveFunc.saveMenu(graph);
					break;
				case 3:
					vaccinationMenu();
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

		for (int nodeAmt = initalNodeAmount; nodeAmt <= maxNodeAmount; nodeAmt += increaseAmount)
		{
			for (int connectionAmount = initialConnectionAmount; connectionAmount <= maxConnectionAmount; connectionAmount++)
			{
				int totalBiggestRandomSubgraph = 0;
				int totalBiggestFriendSubgraph = 0;
				for (int k = 0; k < repeatOnNew; k++)
				{
					graph = new Graph();
					graph.randomFill(false, nodeAmt, connectionAmount);
					for (int l = 0; l < repeatOnReload; l++)
					{
						graph.vaccinate(vacPercent, true);
						totalBiggestRandomSubgraph += Collections.max(graph.getSubgraphs());
						graph.rebuildGraph();
						graph.vaccinate(vacPercent, false);
						totalBiggestFriendSubgraph += Collections.max(graph.getSubgraphs());
						graph.rebuildGraph();
					}
				}
				System.out.println("Node amount: " + nodeAmt + ", Connection Amount : " + connectionAmount);
				System.out.println(
						"Biggest random subgraph: " + (totalBiggestRandomSubgraph / repeatOnNew / repeatOnReload));
				System.out.println(
						"Biggest friend subgraph: " + (totalBiggestFriendSubgraph / repeatOnNew / repeatOnReload));
			}
		}

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

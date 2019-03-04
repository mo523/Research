package Graphs;

import java.util.*;
import java.util.concurrent.*;

public class Program
{
	static ArrayList<Graph> graphs = new ArrayList<Graph>();
	static ExecutorService threadPool;
	static Scanner kb;
	static Graph graph;
	static String[] stats;

	public static void main( String[] args )
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
			System.out.println("0. Quit\n1. Create a new graph\n2. Import a graph \n3. Create multiple graphs");
			choice = choiceValidator(0, 4);
			switch ( choice )
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
			default:
				break;
			}
			if ( choice != 0 )
				graphMenu();
		} while ( choice != 0 );
	}

	private static void multipleGraphMenu()
	{
		multipleGraph();
		System.out.println("All Done");

	}

	private static void newGraphMenu()
	{
		int choice;
		graph = new Graph();
		stats = null;
		System.out.println("\nHow would you like to fill the graph?\n1. Barbashi-Albert\n2. Random");
		choice = choiceValidator(1, 2);
		if ( choice == 2 )
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
		switch ( choice )
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
			System.out.println("0. Main Menu\n1. View graph & stats\n2. View stats only\n3. Save Graph");
			choice = choiceValidator(0, 10);
			switch ( choice )
			{
			case 1:
				Display.displayGraph(graph.getNodes());
			case 2:
				if ( stats == null )
					stats = graph.getStats();
				Display.displayStats(graph.getStats());
				break;
			case 3:
				saveMenu();
				break;
			case 4:
				subgraphMenu();
				break;
			default:
				break;
			}
		} while ( choice != 0 );
	}

	private static void subgraphMenu()
	{
		System.out.println("1. Random\n2. Friends");
		boolean ran = choiceValidator(1, 2) == 1;
		System.out.println("What % of peeps get vacced?");
		double p = kb.nextDouble();
		graph.randomVac(p, ran);
		ArrayList<Integer> subgraphs = graph.getSubgraphs();
		System.out.println("\nTotal Subgraphs: " + subgraphs.size());
		System.out.println("Biggest Subgraph: " + Collections.max(subgraphs));
	}

	private static void randomFillMenu()
	{
		System.out.println("\nHow many nodes?");
		int nodeAmt = choiceValidator(1, Integer.MAX_VALUE);
		System.out.println("\nConnection Probability?");
		System.out.println("As 1/n  (1/10 = .1, 1/80 = .0125, etc.)");
		int prob = choiceValidator(1, Integer.MAX_VALUE);
		graph.randomFill(nodeAmt, prob);

	}

	private static void barbashiFillMenu()
	{
		System.out.println("\nHow many nodes?");
		int nodeAmt = choiceValidator(1, Integer.MAX_VALUE);
		graph.Barbasi(nodeAmt);
	}

	private static void saveMenu()
	{
		System.out.println("Filename and location?");
		String fName = kb.nextLine();
		if ( stats == null )
			stats = graph.getStats();
		SaveFunc.saveGraph(graph.getAfis(), graph.getGfis(), fName, stats);
	}

	private static int choiceValidator( int low, int high )
	{
		int choice = low;
		do
		{
			if ( choice < low || choice > high )
				System.out.println("\nInvalid choice!\n" + low + " - " + high);
			choice = kb.nextInt();
		} while ( choice < low || choice > high );
		kb.nextLine();
		return choice;
	}

	public static void multipleGraph()
	{
		boolean barbasi;
		System.out.println("Enter number of nodes");
		int nodes = kb.nextInt();
		System.out.println("\nHow would you like to fill these graphs?\n1. Barbashi-Albert\n2. Random");
		int choice = choiceValidator(1, 2);
		if ( choice == 1 )
			barbasi = false;
		else
			barbasi = true;

		System.out.println("Enter number of graphs");
		int graphAmt = kb.nextInt();

		System.out.println("Enter number of threads");
		int threads = kb.nextInt();
		multipleGraphs multiple = new multipleGraphs(barbasi, graphAmt, nodes, threads);
		multiple.execute();
	}

	public static Graph createGraph( int nodeAmt )
	{
		Graph graph = new Graph();

		graph.Barbasi(nodeAmt);

		return graph;
	}

	private static void tenTest()
	{
		System.out.println("How many Graphs?");
		int gAmt = choiceValidator(1, Integer.MAX_VALUE);
		System.out.println("How many nodes?");
		int nAmt = choiceValidator(1, Integer.MAX_VALUE);
		System.out.println("1. Barbasi\n2. Random");
		int gGen = choiceValidator(1, 2);
		for ( int i = 0; i < gAmt; i++ )
		{
			Graph g = new Graph();
			if ( gGen == 1 )
				g.Barbasi(nAmt);
			else
				g.randomFill(nAmt, 80);
			stats = g.getStats();
			SaveFunc.saveGraph(g.getAfis(), g.getGfis(), ",test" + ( i + 1 ), stats);
			System.out.println("Done " + ( i + 1 ));
		}
		System.out.println("COMPLETE");
	}
}

package Graphs;

import java.util.*;

public class Program
{
	static Scanner kb;
	static Graph graph;

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
			System.out.println("0. Quit\n1. Create a new graph\n2. Save graph");
			choice = choiceValidator(0, 2);
			switch ( choice )
			{
			case 1:
				newGraphMenu();
				break;
			case 2:

			default:
				break;
			}
			if ( choice != 0 )
				graphMenu();
		} while ( choice != 0 );
	}

	private static void newGraphMenu()
	{
		int choice;
		System.out.println("\nWould you like to create a;\n1. Undirected Graph\n2. Directed Graph");
		choice = choiceValidator(1, 2);
		graph = new Graph(choice == 1 ? true : false);

		System.out.println("\nWould you like to randomly fill your graph?\n1. Yes\n2. No");
		choice = choiceValidator(1, 2);
		if ( choice == 1 )
			randomFillMenu();
	}

	private static void graphMenu()
	{
		int choice;
		do
		{
			System.out.println("\nWhat would you like to do with the graph?");
			System.out.println(
					"0. Main Menu\n1. View graph & stats\n2. View stats only\n3. Add node\n4. Remove node\n5. Add edge\n6. Remove Edge");
			choice = choiceValidator(0, 10);
			switch ( choice )
			{
			case 1:
				Display.displayGraph(graph.getNodes());
			case 2:
				Display.displayStats(graph.getNodes(), graph.getTotalNodeCount());
				break;
			case 3:
				addNodeMenu();
				break;
			case 4:
				removeNodeMenu();
				break;
			case 5:
				addEdgeMenu();
				break;
			case 6:
				removeEdgeMenu();
				break;
			default:
				System.out.println("Doesn't do anything yet.");
				break;
			}
		} while ( choice != 0 );
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

	private static void addNodeMenu()
	{
		System.out.println("\nWhat would you like to call the node?");
		Node n = graph.addNode(kb.nextLine());

		System.out.println("\nWould you like to connect \"" + n.getID() + "\" to any other nodes?\n1. Yes\n2. No");
		if ( choiceValidator(1, 2) == 1 )
			addEdgeSubMenu(n);
	}

	private static void addEdgeMenu()
	{
		System.out.println("\nWhich node would you like to add edges to?");
		addEdgeSubMenu(graph.getNodes().getNode(kb.nextLine()));
	}

	private static void addEdgeSubMenu( Node n )
	{
		String choice;
		do
		{
			System.out.println("\nWhich node would you like to connect \"" + n.getID() + "\" to?\n-1 when finised");
			choice = kb.nextLine();
			if ( !choice.equals("-1") )
				graph.addEdge(n, graph.getNodes().getNode(choice));
		} while ( !choice.equals("-1") );
	}

	private static void removeNodeMenu()
	{
		String choice;
		do
		{
			System.out.println("\nWhich node would you like to remove?\n-1 when finished");
			choice = kb.nextLine();
			if ( !choice.equals("-1") )
				graph.removeNode(choice);
		} while ( !choice.equals("-1") );
	}

	private static void removeEdgeMenu()
	{
		System.out.println("\nWhich node would you like to remove edges from?");
		Node n = graph.getNodes().getNode(kb.nextLine());
		String choice;
		do
		{
			System.out.println("\nWhich node would you like to remove from \"" + n.getID() + "\" ?\n-1 when finished");
			choice = kb.nextLine();
			if (!choice.equals("-1"))
				graph.removeEdge(n.getID(), choice);
		} while ( !choice.equals("-1") );

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
}

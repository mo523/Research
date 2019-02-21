package Graphs;

import java.io.FileNotFoundException;
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
			System.out.println("0. Quit\n1. Create a new graph\n2. Import a graph");
			choice = choiceValidator(0, 3);
			switch ( choice )
			{
			case 1:
				newGraphMenu();
				break;
			case 2:
				importMenu();
				break;
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
		graph = new Graph();

		System.out.println("\nHow would you like to fill the graph?\n1. Random\n2. Barbashi-Albert");
		choice = choiceValidator(1, 2);
		if ( choice == 1 )
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
		if (fName.charAt(0) == '1');
		{
			String m = "C:/Users/moshe/Documents/College/MCO 493 Special Research Project/Graphs";
			fName = fName.replace('1', '/');
			m += fName;
			fName = m;
		}
		switch ( choice )
		{
		case 1:
			graph = SaveFunc.importFile(fName);
			break;
		case 2:
			graph = SaveFunc.importSNAP(fName);
			break;
		}

		Display.displayStats(graph.getNodes(), graph.getTotalNodeCount(), graph.getAsort());
		
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
				Display.displayStats(graph.getNodes(), graph.getTotalNodeCount(), graph.getAsort());
				break;
			case 3:
				saveMenu();
				break;
			default:
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

	private static void barbashiFillMenu()
	{
		System.out.println("\nHow many nodes?");
		int nodeAmt = choiceValidator(1, Integer.MAX_VALUE);
		System.out.println("\nWould you like to use Barbasi Albert;\n1. Yes\n2. No");
		graph.Barbasi(nodeAmt);
	}

	private static void saveMenu()
	{

		System.out.println("FIlename and location?");
		String f = kb.nextLine();
		try
		{
			SaveFunc.createFile(f, graph.getNodes());
		} catch ( FileNotFoundException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

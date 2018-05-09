import java.util.ArrayList;
import java.util.Scanner;

/*
 * Author: Cole Polyak
 * 3 May 2018
 * 
 * Creates a "shell" of sorts to evaluate various logical expressions.
 * Currently limited to single operations.
 */

public class Enviroment 
{
	
	// Our values since we can't use dynamic variable names in Java.
	static ArrayList<String> varNames = new ArrayList<>();
	static ArrayList<Boolean> booleans = new ArrayList<>();
	
	// Calling the shell.
	public static void main(String[] args)
	{
		evaluate();
	}
	
	
	// Runs an endless loop awaiting commands from the user.
	public static void evaluate()
	{
		String command = "";
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("For a list of commands, enter help");
		System.out.print("Please enter a command: ");
		
		while(true)
		{
			command = keyboard.nextLine();
			
			// Special cases.
			if(command.toLowerCase().equals("help")) { outputHelp(); continue;}
			if(command.toLowerCase().equals("end")) { break;}
			
			// Used for the add and get commands.
			String subCommand = command.substring(0,3).toLowerCase();
			
			// Checks subcommand
			switch(subCommand)
			{
				case "add":
					addOperation(command);
					break;
				case "get":
					getValue(command);
				case "hel":
					break;
				default:
					otherOperation(command);
					break;
			}
		}
		
		keyboard.close();
	}
	
	// Command isn't add or get.
	public static void otherOperation(String command)
	{
		// Command and each side of the command.
		String processedCommand = processCommand(command);
		boolean[] processedBooleans = processBooleans(command);
		
		// Checks the command in question.
		switch(processedCommand)
		{
			case "and":
				System.out.println(andOperation(processedBooleans));
				break;
			case "or":
				System.out.println(orOperation(processedBooleans));
				break;
			case "not":
				System.out.println(!(processedBooleans[0]));
				break;
			case "implies":
				System.out.println(impliesOperation(processedBooleans));
				break;
			case "xor":
				System.out.println(xorOperation(processedBooleans));
				break;
			case "biconditional":
				System.out.println(biconditionalOperation(processedBooleans));
				break;
		}
		
	}
	
	// Displays commands possible.
	public static void outputHelp()
	{
		System.out.println("\n\n-----List of commands-----");
		System.out.println("add(n,t): adds an element to the comparison where n is name and t is truth value.");
		System.out.println("end: terminates the program.");
		System.out.println("get(n): returns the truth of the value with name n.");
		
		System.out.println("\n-----List of comparisons-----");
		System.out.println("x and x: evaluates whether both elements are true. The x's are elements.");
		System.out.println("x biconditional x: evaluates wether the two elements are the same. The x's are elements.");
		System.out.println("x or x: evaluates whether one or both elements are true. The x's are elements.");
		System.out.println("x implies x: evaluates the validity of the 'contract' between the two elements. The x's are elements");
		System.out.println("x xor x: evaluates whetheter one and only one elemnet is true. The x's are elements.");
		
		System.out.println("\n\nCommand: ");
		
	}
	
	// Finds a specific target name in the list. Returns -1 if not found.
	public static int find(String target)
	{
		for(int i = 0; i < varNames.size(); ++i)
		{
			if(varNames.get(i).equals(target))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	// Converts the string input into an array of booleans.
	public static boolean[] processBooleans(String command)
	{
		// Splits on spaces.
		String[] arr = command.split(" ");
		
		boolean[] booleansLocal = new boolean[2];
		int usedIndicies = 0;
		
		for(int i = 0; i < arr.length; ++i)
		{
			String processed = arr[i].toLowerCase();
			
			int index = find(processed);
			
			if(index == -1) { continue; }
			else
			{
				// Populates each side.
				if(booleans.get(index)) { booleansLocal[usedIndicies] = true; ++usedIndicies;}
				else { booleansLocal[usedIndicies] = false; ++usedIndicies;}
			}
			
			
			
		}
		return booleansLocal;
		
	}
	
	// Picks out the command the user wants.
	public static String processCommand(String command)
	{
		String[] arr = command.split(" ");
		
		if(arr[0].equals("not")) { return arr[0];}
		return arr[1];
	}
	
	// Gets the boolean value of a specified name.
	public static void getValue(String command)
	{
		String target = command.substring(4, command.length()-1);
		
		int findTarget = find(target);
		
		if(findTarget != -1)
		{
			System.out.println(booleans.get(findTarget));
		}
		else
		{
			System.out.println("The value you are looking for doesn't exist!");
		}
	}
	
	// Used to add a name and truth value pair to the field.
	public static void addOperation(String command)
	{
		String parameters = command.substring(4, command.length()-1);
		
		String[] split = parameters.split(",");
		
		varNames.add(split[0]);

		if(split[1].trim().equals("false")) { booleans.add(new Boolean(false));}
		else { booleans.add(new Boolean(true));}
	}

	// Compares and.
	public static boolean andOperation(boolean[] booleans)
	{
		return booleans[0] && booleans[1];
	}
	
	// Compares or.
	public static boolean orOperation(boolean[] booleans)
	{
		return booleans[0] || booleans[1];
	}
	
	// Compares xor.
	public static boolean xorOperation(boolean[] booleans)
	{
		if(booleans[0] && booleans[1]) { return false;}
		return booleans[0] || booleans[1];
	}
	
	// Compares implies.
	public static boolean impliesOperation(boolean[] booleans)
	{
		if(booleans[0] && !(booleans[1])) { return false;}
		return true;
	}
	
	// Compares biconditional.
	public static boolean biconditionalOperation(boolean[] booleans)
	{
		if(booleans[0] == booleans[1]) { return true;}
		return false;
	}
			
}

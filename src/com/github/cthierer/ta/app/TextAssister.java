package com.github.cthierer.ta.app;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.github.cthierer.ta.engine.AutocompleteProvider;
import com.github.cthierer.ta.engine.IAutocompleteProvider;
import com.github.cthierer.ta.engine.ICandidate;

public class TextAssister {
	
	/**
	 * Signal to train the autocomplete engine. 
	 */
	private static final int OPTION_TRAIN = 1;
	
	/**
	 * Signal to search for a match. 
	 */
	private static final int OPTION_SEARCH = 2;
	
	/**
	 * Signal to quit the application. 
	 */
	private static final int OPTION_QUIT = 3;
	
	public static void main(String[] args) {
		final IAutocompleteProvider provider = new AutocompleteProvider();
		final Scanner in = new Scanner(System.in);
		int selection = 0;
		
		while (selection != OPTION_QUIT) {
			showMenu();
			selection = getSelection(in);
			
			if (selection == OPTION_TRAIN) {
				provider.train(getInput(in));
			} else if (selection == OPTION_SEARCH) {
				printMatches(provider.getWords(getInput(in)));
			}
		}
		
		in.close();
		
		System.out.println("Done!");
	}
	
	/**
	 * Get user input string. 
	 * @param input Input stream. 
	 * @return The user's input, up to the end of the line. 
	 */
	public static String getInput(Scanner input) {		
		System.out.print("Input: ");
		String value = input.nextLine();
		
		return value;
	}
	
	/**
	 * Get the user's menu selection. 
	 * @param input	Input stream. 
	 * @return The selected option; guaranteed to be one of the valid options. 
	 */
	public static int getSelection(Scanner input) {		
		System.out.print("Selection: ");
		
		int selection = input.nextInt();
		input.nextLine();
		
		if (!isValidOption(selection)) {
			System.out.println("Invalid input.");
			return getSelection(input);
		}
		
		return selection;
	}
	
	/**
	 * @param option	Selected option to validate. 
	 * @return True if the option is valid; false otherwise. 
	 */
	public static boolean isValidOption(int option) {
		return option == OPTION_TRAIN ||
				option == OPTION_SEARCH ||
				option == OPTION_QUIT;
	}
	
	/**
	 * Print the matches to the screen. 
	 * @param matches Collection of candidates to print. 
	 */
	public static void printMatches(List<ICandidate> matches) {
		final Iterator<ICandidate> it = matches.iterator();
		boolean first = true;
		
		while(it.hasNext()) {
			final ICandidate candidate = it.next();
			
			if (first) {
				first = false;
			} else {
				System.out.print(", ");
			}
			
			System.out.print(candidate.toString());
		}
		
		System.out.println("\n");
	}
	
	/**
	 * Print a menu option to the screen. 
	 * @param key	The menu key for this option. 
	 * @param label	Text description of this option. 
	 */
	public static void printOption(int key, String label) {
		System.out.println(key + ": " + label);
	}

	/**
	 * Show the main menu with options that the user has available. 
	 */
	public static void showMenu() {
		System.out.println("Select an option:");
		printOption(OPTION_TRAIN, "Train the engine");
		printOption(OPTION_SEARCH, "Search for matches");
		printOption(OPTION_QUIT, "Quit");
	}
}

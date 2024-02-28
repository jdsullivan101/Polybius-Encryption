package ie.atu.sw;

import java.io.*;
import java.util.*;

// Options menu for the user. Only option is for the user to create a directory at the specified location. 
public class OptionsMenu {

	// Same logic as the main menu class with the variable to keep it running.
	private Scanner sc;
	private boolean running = true;

	public OptionsMenu() {
		sc = new Scanner(System.in);
	}

	public void launch() throws Exception {
		// Catch any non numeric inputs by the user.
		try {
			while (running) {
				optionsDisplay();

				int choice = Integer.parseInt(sc.next());
				switch (choice) {

				case 1 -> createDir(); // Call createDir method.
				case 2 -> running = false;
				default -> System.out.println("Invalid Selection, Try Again");

				}
			}
		} catch (IllegalArgumentException e) {
			// Catching incorrect inputs by the user.
			System.out.println("Cannot Accept Non-Numeric Values. " + e.getMessage());
			
		} System.out.println("Returning to Main Menu.");
	}

	private void createDir() {
		sc = new Scanner(System.in);

		// Specify the pathway to the new directory.
		System.out.println("Write the pathway to the new Directory: ");
		File f = new File(sc.nextLine());

		if (f.mkdirs() == true) {
			System.out.println("Folder created at " + f);
		} else {
			System.out.println("Cannot create this directory at " + f);
		}
	}

	private void optionsDisplay() {
		System.out.println(ConsoleColour.PURPLE);
		System.out.println("********************* OPTIONS MENU *************************");
		System.out.println("*                                                          *");
		System.out.println("1. Create Directory											");
		System.out.println("2. Return Home 												");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
		System.out.print("Select Option [1-2]>");
		System.out.println();
		System.out.print(ConsoleColour.BLUE_BOLD_BRIGHT);

	}

}

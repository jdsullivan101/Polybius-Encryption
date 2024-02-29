package ie.atu.sw;

import java.nio.file.*;
import java.util.*;

/*
 * Handling the user specified directory to source the files for encryption from.
 */
public class Directory {
	

	private Scanner sc;

	public String directorySelection() throws Exception {

		sc = new Scanner(System.in);
		
		boolean b = false;
		String inputDirectory = null;

		do {
			System.out.println("Write Your Pathway as path/to/directory");
			
			System.out.println("Specify the Pathway to Input Directory: ");
			

			inputDirectory = sc.nextLine();
			
			Path path = Paths.get(inputDirectory);
			b = checkPathway(path);

		} while (!b); 

		return inputDirectory; 

	}

	public String outputSelection() throws Exception {

		// Scanner to handle user input of the directory.
		sc = new Scanner(System.in);

		// Initialising the boolean and strings for use later;
		boolean b;
		String outDirectory;

		// Do while loop to handle the user input for pathway to directory.
		do {
			System.out.println("Write Your Pathway as path/to/directory");
			System.out.println();
			System.out.println("Specify the Pathway to Output Directory: ");
			// Utilising nextLine instead of next in order to handle any spaces in the file
			// path.

			outDirectory = sc.nextLine();

			/*
			 * Utilising java paths to determine if a directory exists. The input string is
			 * converted into a path object 's', which is then passed to the checkPathway
			 * method to verify the pathway.
			 */
			Path pathout = Paths.get(outDirectory);
			b = checkPathway(pathout); // Boolean check when passing the input to the checkPathway method

		} while (!b); // Prevents the program from returning to the main menu.

		return outDirectory; // Returns the output directory.
	}

	private boolean checkPathway(Path userspecified) {
		// Method to check if it is a directory.
		if (Files.isDirectory(userspecified)) {
			System.out.println("Specified Directory is: " + userspecified);
			return true;

		} else {
			// If directory doesn't exist, method returns a false and user can try again.
			System.out.println("Directory Does not Exist. Try Again.");
			return false;
		}

	}

}

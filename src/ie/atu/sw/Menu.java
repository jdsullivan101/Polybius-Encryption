package ie.atu.sw;

import java.io.*;
import java.util.*;

public class Menu {
	/*
	 * Declaring the instance variables for the class in order to handle the
	 * variables that the program needs. These are all private to prevent other
	 * classes from seeing them. These all have their initial values as null except
	 * for the keepRunning which is declared as true to start the menu for the
	 * program.
	 */
	private String inPut, outPut;
	private char[] encryptionKey;
	private String[] cipheredContent;
	private String[] parsedContent;
	private String[] parseEncrypted;
	private boolean keepRunning = true;

	public void start() throws Exception {

		/*
		 * Using try resources to start a scanner instance so that when the block is
		 * closed, the scanner will automatically close and prevent memory leak.
		 */
		try (Scanner scanner = new Scanner(System.in)) {

			while (keepRunning) {
				/*
				 * Try block in order to catch non-numeric inputs by the user to prevent program
				 * from crashing.
				 */
				try {
					showMenu();

					int choice = Integer.parseInt(scanner.next());
					switch (choice) {

					case 1 -> inputDirectory(); // Handles input directory selection.
					case 2 -> outputDirectory(); // Handles output directory selection.
					case 3 -> userKey(); // Sets the encryption key.
					case 4 -> encrypt(); // Performs the encryption of the files.
					case 5 -> decrypt(); // Decrypts the files.
					case 6 -> writeOut(); // Writes the encrypted or decrypted files to the output directory.
					case 7 -> options(); // Launches the options menu.
					case 8 -> userInputs(); // Shows the user what they have entered as variables to the program.
					case 9 -> keepRunning = false; // End the program.
					default -> System.out.println("Invalid Selection. Please Select Between 1 & 9");

					}

				} catch (IllegalArgumentException e) {
					// Catching incorrect inputs by the user.
					System.out.println("Cannot Accept Non-Numeric Values. " + e.getMessage());
				}

			}
		} finally {
			// Closing the program after selecting the quit option.
			System.out.println("Closing Program, Goodbye.");
			System.exit(0);
		}

	}

	private void inputDirectory() throws Exception {
		// To allow the user to input a pathway to file location.
		Directory d = new Directory();
		// Declaring the inPut variable.
		inPut = d.directorySelection();

	}

	private void outputDirectory() throws Exception {
		// To allow the user to input a pathway to file location.
		Directory d = new Directory();
		// Declaring the output variable.
		outPut = d.outputSelection();

	}

	private void writeOut() throws Exception {
		// File writer class.
		try {
			// Requires a directory to be specified or will generate a message for user.
			if (outPut != null) {
				OutWriter ow = new OutWriter();

				// Passes the destination directory and the ciphered content.
				ow.writeOut(outPut, cipheredContent);
			} else {
				System.out.println("Output directory not specified. Please try again");
			}
			// Catching any errors with the output or null pointer exceptions.
		} catch (IOException | NullPointerException e) {
			System.out.println("Error Writing to Directory: " + outPut + e.getMessage());
			e.printStackTrace();

		}
	}

	private void userKey() throws Exception {
		// Set the encryption key of the program.
		UserKey sk = new UserKey();
		encryptionKey = sk.userKey();

	}

	private void encrypt() throws Exception {

		double duration = 0.0; // To show the time that has elapsed for the process.

		/*
		 * Starts the encryption process. The code first calls the parser method in
		 * Parse class and fills the instance variable parsedContent. This is then
		 * passed to cipher method of the Square class, along with the key.
		 */

		try {
			long startTime = System.nanoTime(); // Starting the timer.

			Parser p = new Parser();
			parsedContent = p.parse(inPut);

			Square sq = new Square(); // Encryption logic.
			cipheredContent = sq.cipher(parsedContent, encryptionKey);

			long endTime = System.nanoTime(); // End the timer:

			duration += ((double) (endTime - startTime) / 1000000000.0); // Converting to seconds.

			System.out.println(ConsoleColour.CYAN_BOLD_BRIGHT);
			System.out.println("It took " + duration + " seconds to encrypt");

		} catch (NullPointerException | IOException e) {
			// In case that the user has not put in an encryption key or an issue with the
			// pathway to the files.
			System.out.println("Error. Review your Input Directory or Encryption Key. " + e.getMessage());
			e.printStackTrace();

		}
	}

	private void decrypt() throws Exception {
		/*
		 * As with encrypt method this will parse the source of the files, and fill the
		 * parsedContent variable. This will then be passed to the Square class for
		 * decryption.
		 */

		double duration = 0.0; // To show the time that has elapsed for the process.

		try {
			long startTime = System.nanoTime(); // Starting the timer.
			Parser p = new Parser();
			parseEncrypted = p.decryptParse(inPut);

			Square sq = new Square(); // Encryption logic.
			cipheredContent = sq.decipher(parseEncrypted, encryptionKey);
			long endTime = System.nanoTime(); // End the timer:

			duration += ((double) (endTime - startTime) / 1000000000.0); // Converting to seconds.

			System.out.println(ConsoleColour.CYAN_BOLD_BRIGHT);
			System.out.println("It took " + duration + " seconds to decrypt");

		} catch (NullPointerException | IOException e) {
			System.out.println("Missing Information. Review your Input Directory or Encryption Key. " + e.getMessage());
			e.printStackTrace();

		}
	}

	private void options() throws Exception {
		// Enter the options menu of the program.
		OptionsMenu om = new OptionsMenu();
		om.launch();

	}

	private void userInputs() {
		// Shows the user the current variables/inputs into the program.
		System.out.println(ConsoleColour.GREEN_BOLD);
		System.out.println("Your Source Directory is: " + inPut);
		System.out.println("Your Destination Directory is: " + outPut);
		System.out.println("Your Encryption Key is: " + Arrays.toString(encryptionKey));

	}

	private void showMenu() {
		// Menu method for the program
		System.out.println(ConsoleColour.PURPLE);
		System.out.println("************************************************************");
		System.out.println("*       ATU - Dept. Computer Science & Applied Physics     *");
		System.out.println("*                                                          *");
		System.out.println("*                   ADFGVX File Encryption                 *");
		System.out.println("*                                                          *");
		System.out.println("*                          G00425758                       *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify Input File Directory");
		System.out.println("(2) Specify Output File Directory");
		System.out.println("(3) Set Encryption Key");
		System.out.println("(4) Prepare and Encrypt Files");
		System.out.println("(5) Prepare and Decrypt Files");
		System.out.println("(6) Write Files to Directory");
		System.out.println("(7) Options Menu");
		System.out.println("(8) Show User Inputs");
		System.out.println("(9) Quit");
		System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
		System.out.print("Select Option [1-9]>");
		System.out.println();
		System.out.print(ConsoleColour.BLUE_BOLD_BRIGHT);
	}

}

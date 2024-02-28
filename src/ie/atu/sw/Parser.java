package ie.atu.sw;

import java.io.*;

/* 
 * Parsing class that takes the user's specified directory of the text files. 
 * This class will then return a String array that will contain the contents of each of the parsed text files.
 * Has 2 parsers, 1 for taking in the original files and the other for the encrypted content. This is because the encrypted 
 * content will be required to maintain blank spaces for the matrix creation and subsequent decryption logic. 
 */

public class Parser {

	// String to help determine the working directory in case of error.
	private String workingDir = System.getProperty("user.dir");

	// Parsing method for the program, will return a String List of separate strings
	// for the text files.

	public String[] parse(String inPutDirectory) throws Exception {

		File directoryFiles = new File(inPutDirectory); // Declaring the source of files, from user input in the Menu
														// Class.

		File textFiles[] = directoryFiles.listFiles(); // Obtaining the files inside the source.

		int indexdirectory = 0; // Index for the creation of the String[] to hold each of the files.

		for (File file : textFiles) {
			// Conditional to ensure that only files and files that end in .txt are parsed.
			if (file.isFile() && file.getName().endsWith(".txt")) {

				indexdirectory++; // Increment the index so that parsedContents will have a number of files to
									// hold.
			}
		}

		String[] parsedContents = new String[indexdirectory]; // New String array to hold contents of file source.

		int wordindex = 0; // For the increment of the parsedContents [] to hold each file.

		for (File file : textFiles) {

			/*
			 * Getting the files and then passing them through the try/catch block of code.
			 * The block will catch errors in reading the file source and prevent the
			 * program from crashing.
			 */

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {

				if (file.isFile() && file.getName().endsWith(".txt")) { // Ensuring that the files are parsed.

					// Stringbuilder to hold the content of the file.
					StringBuilder parsedContent = new StringBuilder();

					String text = null; // To hold the contents of the buffered reader.

					while ((text = br.readLine()) != null) {

						String[] fileContents = text.split("\\s+");

						// To handle each of the words for modification.
						for (int textlength = 0; textlength < fileContents.length; textlength++) {

							// Trimming the white space and special characters. Adjusted to allow to keep
							// numbers as they are handled in the program.
							fileContents[textlength] = fileContents[textlength].trim().replaceAll("[^a-zA-Z0-9]", "")
									.toUpperCase();

							// Appending a space character after each word.
							parsedContent.append(fileContents[textlength]).append(" ");

						}
					}
					br.close(); // Close the reader

					parsedContents[wordindex++] = parsedContent.toString(); // Add to the String Array List.

					System.out.println(ConsoleColour.BLUE_BOLD_BRIGHT);
					System.out.println("File Successfully Parsed: " + file.getName()); // For user information.
				}
			} catch (Exception e) {
				System.out.println("Error Reading File: " + e.getMessage());
				e.printStackTrace();

				// In case of error this will help determine the current working directory
				System.out.println("The current directory is: " + workingDir);
			}
		}
		return parsedContents;
	}

	public String[] decryptParse(String inPutDirectory) throws Exception {

		File encryptedFiles = new File(inPutDirectory); // Declaring the source of files, from user input in the Menu
														// Class.
		File encFiles[] = encryptedFiles.listFiles();

		int indexdecrypt = 0; // Index for the creation of the String[] to hold each of the files.

		for (File file : encFiles) {

			// Conditional to ensure that only files and files that end in .txt are parsed.
			if (file.isFile() && file.getName().endsWith(".txt")) {

				indexdecrypt++; // Increment the index so that parsed content will have a number of files to
								// hold.
			}
		}
		String[] parsedFiles = new String[indexdecrypt]; // New String array for the parsed files.

		int strindex = 0; // Index for the

		for (File file : encFiles) {

			// To read the content of each file. Catches IO exceptions for the user.
			try (BufferedReader readencrypted = new BufferedReader(new FileReader(file))) {

				if (file.isFile() && file.getName().endsWith(".txt")) {

					// To hold content of the files.
					StringBuilder parsedEncrypted = new StringBuilder();

					String line = null;

					while ((line = readencrypted.readLine()) != null) {
						if (line.trim().isEmpty()) {
							/*
							 * Appending white spaces in the encrypted content as these will be needed for
							 * the creation of the array in the decryption logic of the program.
							 */
							parsedEncrypted.append(line);
						} else {
							parsedEncrypted.append(line);
						}
					}
					readencrypted.close();// Close the reader.

					parsedFiles[strindex++] = parsedEncrypted.toString(); // Add to the array of files.

					System.out.println(ConsoleColour.BLUE_BOLD_BRIGHT);
					System.out.println("File Successfully Parsed: " + file.getName()); // For user information.
				}

			} catch (Exception e) {
				// Catch any exceptions happening in the code.
				System.out.println("Error Reading File: " + e.getMessage());
				e.printStackTrace();
			}
		}

		return parsedFiles;

	}

}

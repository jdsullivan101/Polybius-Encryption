package ie.atu.sw;

import java.io.*;
import java.util.*;

/*
 *  Class to handle the write to file for the program. This takes the arguments of the user 
 *  specified directory and the list of ciphered content. 
 */
public class OutWriter {
	
	private Scanner sc; // This is for the user to enter a name for the file.

	public void writeOut(String outDirectory, String[] cipheredContent) throws Exception {
		
		try {
			// Allows the user to specify if the content is encrypted/decrypted.
			System.out.println("Enter a description, such as encrypt or decrypt.");
			sc = new Scanner(System.in);
			String fileName = sc.next();
			
			int index =0;
			// Iterating through the cipheredContent list.
			for (int i = 0; i < cipheredContent.length; i++) {
				
				// Getting a string value for each of the individual strings for the content.
				String modContent = cipheredContent[index];
				
				/*
				 * String to handle a combination of the outdirectory, filename and then
				 * extension of the file. User should avoid entering a "/" when specifying the
				 * destination directory. E.g the directory should be written as 
				 * "path/to/directory". The program will add another "/" as needed. 
				 */
				String outFile = outDirectory + File.separator + fileName + i + ".txt";
				
				// Writing the file using filewriter. 
				FileWriter fw = new FileWriter(outFile);

				fw.write(modContent);
				index++;

				fw.close(); // Close the writer.
			}
		} catch (IOException e) {
			// Catch an output to the specified directory.
			e.printStackTrace();
			System.out.println("Error Writing file to " + outDirectory + e.getMessage());
		} 
		// Informing the user that everything has been written. 
		System.out.println("Files written to: " + outDirectory);

	}

}

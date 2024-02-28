package ie.atu.sw;

/*
 *  This class handles the decryption and encryption logic of the program. The class has several methods that are used to perform
 *  the encryption and decryption. The 2 public methods take in the arguments from the menu class, which handles the encryption key and
 *  directory handling. These 2 methods in general follow a sequential order, calling on private methods to perform their requirements. 
 *  For the encryption part there is no clipping of the input string as there is a method padText that will append blank characters(' ')
 *  to the string to be put into the created 2D arrays.
 */

public class Square {

	public String[] cipher(String[] plainText, char[] key) {

		int cipherindex = plainText.length; // Retrieving the number of files to be encrypted.
		String[] cipheredStrings = new String[cipherindex]; // To return these at the end of method.

		int index = 0;
		for (String plainT : plainText) { // For loop to iterate over data passed to method.

			StringBuilder getChars = new StringBuilder();
			for (int i = 0; i < plainT.length(); i++) {

				// Append each of the 2 characters from the polybius square.
				getChars.append(getRowChar(plainT.charAt(i)));
				getChars.append(getColChar(plainT.charAt(i)));
			}

			String twoCharString = getChars.toString(); // Return the String.

			// Creating first matrix, and passing the arguments.
			char[][] firstmatrix = createMatrix(twoCharString, key);

			// Sorting the matrix based on columnar transposition.
			char[][] lastmatrix = sortMatrix(firstmatrix, key);

			/*
			 * Building a string of the encrypted text so that it can be then passed to the
			 * file writer class. The characters are appended by going down each of the
			 * columns and appending each of the characters at the row position.
			 */
			StringBuilder cipherText = new StringBuilder(); // The encrypted string of the input file.

			// Building the string by appending the characters in the column going down row
			// by row.
			for (int col = 0; col < lastmatrix[0].length; col++) {
				for (int row = 0; row < lastmatrix.length; row++) {
					char c = lastmatrix[row][col];
					cipherText.append(c);
				}
			}

			cipheredStrings[index++] = cipherText.toString(); // Repeat for each of the Strings.
		}

		return cipheredStrings;

	}

	private char getRowChar(char plainchar) {
		/*
		 * Retrieving the character representation at the row column. Row is started at
		 * 1 as the row = 0, contains the polybius character representation and is not
		 * required.
		 */

		for (int row = 1; row < POLYBIUS.length; row++) {
			for (int col = 1; col < POLYBIUS[0].length; col++) {
				if (plainchar == ' ') { // If the character is a space return a blank char.
					return ' ';
				}
				if (POLYBIUS[row][col] == plainchar) {
					return POLYBIUS[row][0]; // Returns row character for the encryption.
				}
			}
		}

		return plainchar; // Will return the character from the input if not found.
	}

	private char getColChar(char plainchar) {

		// Same logic as the getRowchar method above.
		for (int row = 1; row < POLYBIUS.length; row++) {
			for (int col = 1; col < POLYBIUS[row].length; col++) {
				if (plainchar == ' ') {
					return ' ';
				}
				if (POLYBIUS[row][col] == plainchar) {
					return POLYBIUS[0][col]; // Returns column character for the encryption.
				}

			}
		}
		return plainchar; // Will return the character from the input if not found.
	}

	private char[][] createMatrix(String twoCharString, char[] key) {

		// Create the 2D array of the 2 character encryption of the input string.

		int cols = key.length; // From the encryption key passed to the program.

		int clip = ((twoCharString.length()) % key.length); // Determining if the string needs to be reduced in size.

		if (clip > 0) { // In case an even array cannot be drawn, calling this method will pad extra
						// space characters at the end.
			twoCharString = padText(twoCharString, key);
		}

		int row = (twoCharString.length() / cols); // Get the rows of array.

		char[][] matrix = new char[row][cols]; // New 2D array.

		int index = 0;
		for (int i = 0; i < row; i++) { // Populate array column by column.
			for (int j = 0; j < cols; j++) {
				matrix[i][j] = twoCharString.charAt(index);
				index++;
			}

		}
		return matrix;
	}

	private String padText(String s, char[] key) {
		/*
		 * Method to pad the text. Based on the number of characters to clip off the end
		 * of the string, this method will add a space character to the string allowing
		 * the create matrix method to fill the array fully.
		 */
		int clip = s.length() % key.length;
		int pad = key.length - clip; // The number of spaces to be filled
		StringBuilder padding = new StringBuilder();

		for (int i = 0; i < pad; i++) { // Fill in each space with blank character.
			padding.append(" ");
		}
		String padded = s + padding.toString(); // Add the padded characters to the input string.

		return padded;
	}

	private static char[][] sortMatrix(char[][] matrix, char[] key) {

		// Method for transposing the matrix based on the sorting the encryption key.

		int[] keyIndex = getKeyIndices(key); // Get the index of the key characters.

		keyIndex = bubbleSort(key, keyIndex); // Bubble sort the key and get the order of the key.

		char[][] ciphermatrix = new char[matrix.length][matrix[0].length]; // Creating the transposed matrix based on
																			// original matrix.

		for (int i = 0; i < keyIndex.length; i++) { // The columns of the matrix.
			int colindex = keyIndex[i]; // To move the element based on the key index.
			for (int j = 0; j < matrix.length; j++) { // Rows of the matrix.
				// The elements are rearranged based on the change of position of the key
				// index(column).
				ciphermatrix[j][i] = matrix[j][colindex];

			}
		}

		return ciphermatrix;
	}

	public String[] decipher(String[] parseEncrypted, char[] key2) throws Exception {

		int decipherindex = parseEncrypted.length; // The array of the encrypted strings.

		String[] decipheredStrings = new String[decipherindex]; // Return the ciphered strings, based on number of
																// inputs.

		int[] originalIndices = getKeyIndices(key2); // In order to sort the key and get the indices.

		int[] sortKey = bubbleSort(key2, originalIndices); // In order to create the matrix based on the sorted key.

		int index = 0;
		for (String cipherText : parseEncrypted) { // For each loop of the encrypted content.

			try {
				// Creating the array based on the elements of the sorted key.
				char[][] cipheredmatrix = transposedMatrix(cipherText, sortKey);

				// Transposing the array based on the arguments passed to it
				char[][] unsortmatrix = decipherMatrix(cipheredmatrix, key2);

				// Returning the encrypted string back to it's original form.
				String uncipherText = decipherFinalText(unsortmatrix, key2);

				decipheredStrings[index++] = uncipherText; // Iterate over each of the strings.

			} catch (NullPointerException e) {

				// Catching any null values in case there's an issue with null values being
				// passed to method.
				System.out.println("Null value at: " + decipheredStrings[index]);
				e.printStackTrace();
			}
		}

		return decipheredStrings;
	}

	private char[][] transposedMatrix(String cipherText, int[] key) throws Exception {

		// Method for the creation of the transposed matrix that was the result of
		// encrypting the text.
		int cols = key.length;

		int clip = ((cipherText.length()) % key.length); // To remove any characters that cannot fit inside the 2D
															// array.

		cipherText = cipherText.substring(0, (cipherText.length() - clip));

		int row = (cipherText.length() / cols);

		// Recreating the matrix that was the result of transposing the original matrix.
		char[][] deciphertranspose = new char[row][cols];

		int index = 0;
		for (int i = 0; i < cols; i++) { // Filling the matrix column by column by inserting the characters into each
											// row.
			for (int j = 0; j < row; j++) { // This is the reverse of the original create matrix method.
				deciphertranspose[j][i] = cipherText.charAt(index);
				index++;
			}

		}

		return deciphertranspose;
	}

	private static char[][] decipherMatrix(char[][] transposed, char[] sortedKey) {
		// Method to reverse the columnar transposition of the array.

		int[] keyIndex = getKeyIndices(sortedKey); // Get the indices based on the sorted key.

		keyIndex = bubbleSort(sortedKey, keyIndex); // Sorting the characters and indices.

		char[][] deciphermatrix = new char[transposed.length][transposed[0].length]; // Create the transposed array.

		for (int i = 0; i < keyIndex.length; i++) { // For every column of the array.
			int colindex = keyIndex[i];
			for (int j = 0; j < transposed.length; j++) { // The row of the array.
				deciphermatrix[j][colindex] = transposed[j][i]; // Rearranging the characters based on the rearranging
																// the encryption key.
			}
		}

		return deciphermatrix;

	}

	private String decipherFinalText(char[][] matrix, char[] key) {

		// https://github.com/andpenaspal/Polybius-Square-Cipher/blob/master/src/ie/gmit/dip/Polybius.java#L548
		// Source above where this code was adapted from for this program.

		StringBuilder decipheredText = new StringBuilder(); // To hold the content of the decrypted string.
		for (int row = 0; row < matrix.length; row++) {

			// Increment the columns by 2 as the characters are in groups of 2.
			for (int col = 0; col < matrix[row].length; col += 2) {

				if ((key.length % 2 != 0) && row % 2 != 0 && col == 0) {
					col++; // To handle the situation where the number of columns is odd and then the code
							// can
					// skip the first column on the next row.

				}

				// To handle the space character so that the end string is easier for the user
				// to read.
				if (matrix[row][col] == ' ') {
					decipheredText.append(' ');
					continue;
				}

				char indexone; // Initialising variables for the 2 character representation.
				char indextwo;

				for (int polyrow = 0; polyrow < POLYBIUS.length; polyrow++) {
					if (col < matrix[row].length - 1) { // Checking to ensure that there is 2 characters to decipher.

						// Declaring the 2 character representation of the characters.
						indexone = matrix[row][col];
						indextwo = matrix[row][col + 1]; // For the character in the next column.

						if (indexone == POLYBIUS[polyrow][0]) { // Getting the decrypted character based on the location
																// in the Polybius square.
							for (int polycol = 0; polycol < POLYBIUS[0].length; polycol++) {
								if (indextwo == POLYBIUS[0][polycol]) {
									decipheredText.append(POLYBIUS[polyrow][polycol]);
								}
							}
						}

					} else {
						indexone = matrix[row][col];
						// Handling the situation where the 2nd character representation has to be taken
						// from the next row due to
						// an odd number of rows.
						if (row < matrix.length - 1) {
							indextwo = matrix[row + 1][0]; // The 2nd character will be the character on the next row.

							if (indexone == POLYBIUS[polyrow][0]) {
								for (int polycol = 0; polycol < POLYBIUS[0].length; polycol++) {
									if (indextwo == POLYBIUS[0][polycol]) { // Getting the decrypted character from the
																			// square.
										decipheredText.append(POLYBIUS[polyrow][polycol]);
									}
								}
							}
						}
					}
				}
			}

		}
		return decipheredText.toString();

	}

	private static int[] bubbleSort(char[] key, int[] keyIndex) {
		/*
		 * Bubble sorting of the key, and sorting the indices of the key. For example
		 * "JAVA" as a keyword would have an original index of 0,1,2,3. This bubble sort
		 * will return the key as "AAJV" and the indices as 1,3,0,2.
		 */
		char[] sortKey = key.clone(); // Make a copy of the key.

		// Bubble sort algorithm taken from the CTA module.
		boolean swapped;
		do {
			swapped = false;
			for (int i = 0; i < sortKey.length - 1; i++) {
				for (int j = 0; j < keyIndex.length - i - 1; j++) {
					if (sortKey[j] > sortKey[j + 1]) {
						// Sorting the key based on the value of the char at index i.
						char temp = sortKey[j];
						sortKey[j] = sortKey[j + 1];
						sortKey[j + 1] = temp;

						// Returning the indices of the characters in the encryption key.
						int tempindex = keyIndex[j];
						keyIndex[j] = keyIndex[j + 1];
						keyIndex[j + 1] = tempindex;
						swapped = true;
					}
				}
			}
		} while (swapped);
		return keyIndex;
	}

	private static int[] getKeyIndices(char[] encryptionKey) {

		// Method to return the indices of the key.
		// Is used in the methods to transpose the arrays for encryption and decryption.
		int[] originalIndices = new int[encryptionKey.length];
		for (int i = 0; i < encryptionKey.length; i++) {
			originalIndices[i] = i;
		}
		return originalIndices;
	}

	// 2D array of the polybius square for the encryption/decryption.
	private static final char[][] POLYBIUS = { 
			{ ' ', 'A', 'D', 'F', 'G', 'V', 'X' },
			{ 'A', 'P', 'H', '0', 'Q', 'G', '6' }, 
			{ 'D', '4', 'M', 'E', 'A', '1', 'Y' },
			{ 'F', 'L', '2', 'N', 'O', 'F', 'D' }, 
			{ 'G', 'X', 'K', 'R', '3', 'C', 'V' },
			{ 'V', 'S', '5', 'Z', 'W', '7', 'B' },
			{ 'X', 'J', '9', 'U', 'T', 'I', '8' }, };

}

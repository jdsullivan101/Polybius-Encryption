package ie.atu.sw;

import java.util.*;

/*
 * Class to handle and validate the encryption key input by the user. The user key can include alpha-numeric characters in the
 * key. The validation method will strip special characters and remove spaces from the input. 
 */
public class UserKey {

	private static final int MIN_KEY_SIZE = 4; // Minimum to ensure good diffusion of the encryption.
	private static final int MAX_KEY_SIZE = 50;

	private Scanner s; // Handle user input of the key.

	private char[] userKeyInput = null;

	public char[] userKey() throws Exception {

		try {
			s = new Scanner(System.in);
			System.out.println("Please Enter Your Encryption Key. Min 4, Max 50 Characters.");
			String inputkey = s.nextLine(); // In case the user puts in 2 words.

			userKeyInput = setKey(inputkey); // Call setKey method to validate the key.

			// For user information. As the key is returned as a char[], the Arrays.toString
			// is needed.
			System.out.println("Your Key Is: " + Arrays.toString(userKeyInput));

			return userKeyInput;

		} catch (Exception e) {
			// Catch errors if it cannot be validated.
			System.out.println("Cannot Set Key: " + e.getMessage());

		}

		return null; // Returns a null value for the key if there's an issue setting the key.
	}

	private char[] setKey(String inputkey) throws Exception {
		// Replacing the special characters and converting to char[].

		this.userKeyInput = inputkey.replaceAll("[^a-zA-Z0-9]", "").toUpperCase().toCharArray();
		this.validateKey(userKeyInput);

		return userKeyInput;

	}

	private void validateKey(char[] key) throws Exception {

		// Ensuring that the key is not null and is within the limits set by the
		// program.
		if (key == null || key.length < MIN_KEY_SIZE || key.length > MAX_KEY_SIZE) {
			throw new Exception("Invalid Key Length.");
		}

	}

}

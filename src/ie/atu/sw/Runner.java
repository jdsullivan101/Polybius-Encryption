package ie.atu.sw;

public class Runner {

	public static void main(String[] args) {

		// Starting the program from this class.
		try {
			Menu m = new Menu();
			m.start();
		} catch (Exception e) {
			System.out.println("Error Launching Program. " + e.getMessage());
			e.printStackTrace();
		}
	}

}

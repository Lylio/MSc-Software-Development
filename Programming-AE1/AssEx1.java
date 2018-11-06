import javax.swing.*;

/**
 * @author Lyle 
 * Main method for Lilybank Wine Merchant program
 */
public class AssEx1 {

	public static void main(String[] args) {

		String customerName = "";
		double customerBalance = 0;
		boolean validInput = false;

		// Asks for customer's name - program exits if field is empty
		try {
		customerName = JOptionPane.showInputDialog("Customer name: ");
		if(customerName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error - no customer name", "Blank field error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			}
		} catch (Exception error) {
			System.exit(0); //Program exits if cancel or close window are pressed
		}
		
		
		// Asks for customer's starting balance - validates input is numerical
		try {
			while (!validInput) {
				try {
					customerBalance = Double.parseDouble(JOptionPane.showInputDialog("Customer balance: "));
					validInput = true;
				} catch (NumberFormatException error) {
					JOptionPane.showMessageDialog(null, "Invalid number entered - please try again", "Invalid number error", JOptionPane.ERROR_MESSAGE);
					validInput = false;
				}
			}

		} catch (Exception error) {
			System.exit(0); //Program exits if cancel or close window are pressed
		}

		
		// Creates new customer account object passing name and balance data
		CustomerAccount customerAccount = new CustomerAccount(customerName, customerBalance);

		
		// Runs GUI passing customer account object
		LWMGUI runGUI = new LWMGUI(customerAccount);

	}

}

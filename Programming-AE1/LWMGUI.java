import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Lyle 
 * Creates the GUI interface and methods to handle button events
 */

public class LWMGUI extends JFrame implements ActionListener {

	private CustomerAccount customerAccount;
	private JLabel wineNameLabel, qtyLabel, priceLabel, transactionLabel, balanceLabel, itemLabel;
	private JTextField wineNameField, qtyField, priceField, transactionField, balanceField;
	private JButton saleButton, returnButton;
	private String wineName;
	private double winePrice;
	private int wineQty;

	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 200;

	// Constructs the GUI and passes in customer account details
	public LWMGUI(CustomerAccount customerAccount) {

		this.customerAccount = customerAccount;
		
	// Creates frame elements
		createNameField();
		createQtyField();
		createPriceField();
		createSaleButton();
		createReturnButton();
		createItemField();
		createTransactionField();
		createBalanceField();
		createPanel();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
	// Initialises frame properties
		this.setTitle("Lilybank Wine Merchants: " + customerAccount.getCustomerName());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
	// Displays customer's starting balance when GUI launches	
		updateBalance();

	}

	 // Retrieves customer's balance: a negative number means the
	 // account is in credit and is converted to a positive by negating the negative value ('-') 
	 // and attaching the suffix 'CR'
	private void updateBalance() {
		double custBal = customerAccount.getCustomerBalance();
		if (custBal < 0) {
			transactionField.setText(String.format("£%.2f", customerAccount.getTransactionAmount()));
			balanceField.setText(String.format("£%.2f CR", -(customerAccount.getCustomerBalance())));
		} else {
			transactionField.setText(String.format("£%.2f", customerAccount.getTransactionAmount()));
			balanceField.setText(String.format("£%.2f", customerAccount.getCustomerBalance()));
		}
	}

	// Removes data from the name, quantity and price fields
	private void clear() {
		wineNameField.setText("");
		qtyField.setText("");
		priceField.setText("");
	}

	
	// Processes a sale by creating a wine object and passing to calculateSale() method 
	// in customer account
	private void processSale() {
		
		Wine wine = new Wine(wineName, winePrice, wineQty);
		customerAccount.calculateSale(wine);

		// Plural test for single or multiple bottles in order to return 'bottle' or 'bottles'
		if (wineQty > 1) {
			itemLabel.setText("Wine Purchased: " + wine.getName() + " (" + wine.getQuantity() + " bottles)");
		} else {
			itemLabel.setText("Wine Purchased: " + wine.getName() + " (" + wine.getQuantity() + " bottle)");
		}
		updateBalance();
		clear();
	}
	
	// Processes a return by creating a wine object and passing to calculateReturn() method 
	// in customer account
	private void processReturn() {
		
		Wine wine = new Wine(wineName, winePrice, wineQty);
		customerAccount.calculateReturn(wine);

		// Plural test for single or multiple bottles in order to return 'bottle' or 'bottles' text
		if (wineQty > 1) {
			itemLabel.setText("Wine Returned: " + wine.getName() + " (" + wine.getQuantity() + " bottles)");
		} else {
			itemLabel.setText("Wine Returned: " + wine.getName() + " (" + wine.getQuantity() + " bottle)");
		}
		updateBalance();
		clear();
	}
	
	// Creates wine name label and field
	private void createNameField() {
		wineNameLabel = new JLabel("Name: ");
		final int FIELD_WIDTH = 15;
		wineNameField = new JTextField(FIELD_WIDTH);
	}

	// Creates quantity label and field
	private void createQtyField() {
		qtyLabel = new JLabel("Quantity: ");
		final int FIELD_WIDTH = 5;
		qtyField = new JTextField(FIELD_WIDTH);
	}

	// Creates price label and field
	private void createPriceField() {
		priceLabel = new JLabel("Price: £");
		final int FIELD_WIDTH = 6;
		priceField = new JTextField(FIELD_WIDTH);
	}

	// Creates sale button and attaches ActionListener
	private void createSaleButton() {
		saleButton = new JButton("Process Sale");
		saleButton.addActionListener(this);
	}

	// Creates return button and attaches ActionListener
	private void createReturnButton() {
		returnButton = new JButton("Process Return");
		returnButton.addActionListener(this);
	}

	// Creates item label and field
	private void createItemField() {
		itemLabel = new JLabel("");
	}

	// Creates transaction label and field
	private void createTransactionField() {
		transactionLabel = new JLabel("Transaction amount: ");
		final int FIELD_WIDTH = 8;
		transactionField = new JTextField(FIELD_WIDTH);
		transactionField.setEditable(false);
	}

	// Creates balance label and field
	private void createBalanceField() {
		balanceLabel = new JLabel("Current balance: ");
		final int FIELD_WIDTH = 8;
		balanceField = new JTextField(FIELD_WIDTH);
		balanceField.setEditable(false);
	}

	// Creates panels and attach labels, fields and buttons in the layout of
	// 4 rows and 1 column
	private void createPanel() {

		setLayout(new GridLayout(4, 1));

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		
		//Top panel: name, quantity and price of wine
		panel1.add(wineNameLabel);
		panel1.add(wineNameField);
		panel1.add(qtyLabel);
		panel1.add(qtyField);
		panel1.add(priceLabel);
		panel1.add(priceField);
		
		//Middle panel: sale and return buttons
		panel2.add(saleButton);
		panel2.add(returnButton);
		
		//Middle panel: confirmation of name and quantity of items sold or returned
		panel3.add(itemLabel);
		
		//Bottom panel: transaction and balance amounts
		panel4.add(transactionLabel);
		panel4.add(transactionField);
		panel4.add(balanceLabel);
		panel4.add(balanceField);
		
		//Attaches panels to frame
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);
		this.add(panel4);
	}

	// Validates field data (with error messages) then confirms which button was pressed and 
	//calls either processSale() or processReturn()
	@Override
	public void actionPerformed(ActionEvent event) {
		
		wineName = wineNameField.getText();
		
		if (wineNameField.getText().isEmpty() || qtyField.getText().isEmpty() || priceField.getText().isEmpty()) //Tests if fields are blank 
		{
			JOptionPane.showMessageDialog(null, "Fields cannot be empty - please try again", "Blank field error", JOptionPane.ERROR_MESSAGE);
			clear();
		} else {
			try {
				wineQty = Integer.parseInt(qtyField.getText());
				if (wineQty < 0) //Tests if negative item quantity is entered
				{
					JOptionPane.showMessageDialog(null, "Quantity must be a postive number - please try again", "Number type error", JOptionPane.ERROR_MESSAGE);
					clear();
				} else {
					try {
						winePrice = Double.parseDouble(priceField.getText());
						if (winePrice < 0) //Tests if negative price has been entered
						{
							JOptionPane.showMessageDialog(null, "Price must be a postive number - please try again", "Number type error", JOptionPane.ERROR_MESSAGE);
							clear();
						}

						else {
							if (event.getSource() == saleButton) //If sale button is pressed, the sale method is called and fields cleared
							{
								processSale();
								clear();
							} else if (event.getSource() == returnButton)  //If return button is pressed, the return method is called and fields cleared
							{
								processReturn();
								clear();
							}
						}

					}

					catch (NumberFormatException error) {
						JOptionPane.showMessageDialog(null, "Invalid number entered - please try again", "Number type error", JOptionPane.ERROR_MESSAGE);
						clear();
					}
				}
			}

			catch (NumberFormatException error) {
				JOptionPane.showMessageDialog(null, "Invalid number entered - please try again", "Number type error", JOptionPane.ERROR_MESSAGE);
				clear();
			}
		}
	}

}

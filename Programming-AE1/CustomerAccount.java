
/**
 * @author Lyle 
 * Creates a customer account object
 */
public class CustomerAccount {

	private String customerName;
	private double customerBalance, transactionAmount;
	private Wine wine;

	private final double SERVICE_CHARGE = 0.8; // Returned bottles are refunded
											   // with 20% off original price

	// Constructs a customer account with their balance details
	public CustomerAccount(String customerName, double customerBalance) {
		this.customerName = customerName;
		this.customerBalance = customerBalance;
	}

	// Returns customer name
	public String getCustomerName() {
		return customerName;
	}

	// Returns customer balance
	public double getCustomerBalance() {
		return customerBalance;
	}

	// Returns transaction amount
	public double getTransactionAmount() {
		return transactionAmount;
	}

	// Calculates total cost of wine sale and updates customer balance
	public void calculateSale(Wine wine) {
		this.wine = wine;
		transactionAmount = wine.getPrice() * wine.getQuantity();
		customerBalance += transactionAmount;
	}

	// Calculates total refund of returned wine including 20% service charge 
	// and updates customer balance
	public void calculateReturn(Wine wine) {
		this.wine = wine;
		transactionAmount = (wine.getPrice() * wine.getQuantity()) * SERVICE_CHARGE;
		customerBalance -= transactionAmount;
	}
}

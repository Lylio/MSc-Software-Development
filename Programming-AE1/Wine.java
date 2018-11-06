
/**
 * @author Lyle 
 * Creates a wine object
 */

public class Wine {

	private String wineName;
	private double winePrice;
	private int wineQuantity;

	//Constructs a wine item with name, price and quantity of the sale or return 
	public Wine(String name, double price, int quantity) {

		wineName = name;
		winePrice = price;
		wineQuantity = quantity;
	}

	// Returns name of wine item
	public String getName() {
		return wineName;
	}

	// Returns price of wine item
	public double getPrice() {
		return winePrice;
	}

	// Returns quantity of wine items
	public int getQuantity() {
		return wineQuantity;
	}
}

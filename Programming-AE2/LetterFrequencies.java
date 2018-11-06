
/**
 * Programming AE2
 * Processes report on letter frequencies
 */
public class LetterFrequencies
{
	/** Size of the alphabet */
	private final int SIZE = 26;

	/** Count for each letter */
	private int [] alphaCounts;

	/** The alphabet */
	private char [] alphabet; 

	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
			0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
			6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};

	/** Character that occurs most frequently */
	private char maxCh;

	/** Total number of characters encrypted/decrypted */
	private int totChars;

	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies()
	{
		//Creates alphabet array
		alphabet = new char[SIZE];
		for (int i = 0; i < SIZE; i++) {
			alphabet[i] = (char)('A' + i);
		}
		
		//Initialises variables
		alphaCounts = new int[SIZE];
		totChars = 0;
		maxCh = ' ';
	}

	/**
	 * Increases frequency details for given character
	 * @param ch the character just read
	 */
	public void addChar(char ch)
	{
		for (int i = 0; i < alphabet.length; i++)
		{
			//Increments count of each character and the total number of characters
			if(ch == alphabet[i]) {
				alphaCounts[i] = alphaCounts[i] + 1; 
				totChars++;
			}
		}
	}

	/**
	 * Gets the maximum frequency
	 * @return the maximum frequency
	 */
	//Returns the maximum frequency percentage
	public double getMaxPC()
	{
		double maxPC = 0; 
		for (int i = 0; i < 26; i++) {
			double charFreq = (double)alphaCounts[i] / totChars * 100;
			if(charFreq >= maxPC) {
				maxPC = charFreq;
			}
		}
		return maxPC;
	}

	//Returns the maximum frequency character
	public char getMaxChar()
	{
		double maxPC = 0; 
		for (int i = 0; i < 26; i++) {
			double charFreq = (double)alphaCounts[i] / totChars * 100;
			if(charFreq >= maxPC) {
				maxPC = charFreq;
				maxCh = (char)(i + 'A');
			}
		}
		return maxCh;
	}

	/**
	 * Returns a String consisting of the full frequency report
	 * @return the report
	 */
	public String getReport(char alphaChar, int i)
	{
		String report = ""; 
		alphaChar = alphabet[i]; //Character of alphabet
		int frequency = alphaCounts[i]; //Number of character occurrences
		double freqPercent = (double)alphaCounts[i] / totChars * 100; //Percentage of character occurrences 
		double avgFreq = avgCounts[i]; //Average frequency percentages
		double diff = freqPercent - avgFreq; //Difference between file frequency percentages and average frequency percentages
		
		report = String.format("%4s %5d %7.1f %7.1f %8.1f \n", alphaChar, frequency, freqPercent, avgFreq, diff);
		
		return report;
	}
}

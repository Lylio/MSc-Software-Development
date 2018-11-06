import java.util.Arrays;

/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode and decode a character
 */
public class VCipher
{
	private char [] alphabet;
	private final int SIZE = 26;
	private char [][] vigArray;
	private int keyLength;
	private int totChars;

	
	/** 
	 * The constructor generates the cipher
	 * @param keyword the cipher keyword
	 */
	public VCipher(String keyword) { 
		
		alphabet = new char [SIZE];
		keyLength = keyword.length();
		vigArray = new char[keyLength][SIZE];
		char[] tempArray = new char[SIZE];
		
		//Creates alphabet
		for(int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
		
		//Creates 2D Vigenere array
		for(int i = 0; i < keyLength; i++){
			
			int keyIndex = keyword.charAt(i) - 'A';
			
			//Copies from keyword character position in alphabet to end of alphabet
			System.arraycopy(alphabet, keyIndex, tempArray, 0, SIZE - keyIndex);
			
			//Copies from beginning of alphabet up to keyword character position in alphabet  
			System.arraycopy(alphabet, 0, tempArray, SIZE - keyIndex, keyIndex);

			//Copies the rows to the Vigenere array
			vigArray[i] = Arrays.copyOf(tempArray, SIZE);
		}

		//Print Vigenere array for testing and tutors
		for(int i = 0; i < keyLength; i++) {
			
			for(int j = 0; j < SIZE; j++) {
				System.out.print(vigArray[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */	
	public char encode(char ch) {

		char enChar = ' ';

		//k = row of Vigenere array
		//Encoding process loops back to first row when totChars % keyLength == 0;
		int k = totChars % keyLength;

		//Loop iterates forwards through length of alphabet
		for (int j = 0; j < SIZE; j++) 
		{				
			if (ch == alphabet[j]) //Compares character against alphabet array
			{ 
				totChars++ ; //Increments total characters
				enChar = vigArray[k][j]; //Assigns corresponding character in Vigenere array
			}	
			else if (!(ch >= 'A' && ch <= 'Z')) //Returns non-letters such as spaces and punctuation as unprocessed
			{
				enChar = ch;
			}
		}
		return enChar;
	}
	
	/**
	 * Decode a character
	 * @param ch the character to be decoded
	 * @return the decoded character
	 */  
	public char decode(char ch) {	
		
		char deChar = ' ';
		
		//k = row of Vigenere array
		//Encoding process loops back to first row when totChars % keyLength == 0;
		int k = totChars % keyLength;

		//Loop iterates forwards through length of alphabet
		for (int j = 0; j < SIZE; j++)
			
			if (ch == vigArray[k][j]) //Compares character against Vigenere array
			{	
				totChars++ ; //Increments total characters
				deChar = alphabet[j] ; //Assigns corresponding character in alphabet array
			}
			else if (!(ch >= 'A' && ch <= 'Z')) //Returns non-letters such as spaces and punctuation as unprocessed
			{
				deChar = ch ;
			}
		return deChar;
	}
}

/**
 * Programming AE2
 * Contains monoalphabetic cipher and methods to encode and decode a character.
 */
public class MonoCipher
{
	/** The size of the alphabet. */
	private final int SIZE = 26;

	/** The alphabet. */
	private char [] alphabet;

	/** The cipher array. */
	private char [] cipher;

	//
	private int duplicateFlag;

	/**
	 * Instantiates a new mono cipher.
	 * @param keyword the cipher keyword
	 */
	public MonoCipher(String keyword)
	{
		//Creates alphabet
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++) {
			alphabet[i] = (char)('A' + i);
		}

		//Creates first part of cipher from keyword
		int keyLength = keyword.length();
		cipher = new char[SIZE];
		for(int i = 0; i < keyLength; i++) {
			cipher[i] = keyword.charAt(i);
		}

		//Creates remainder of cipher from the remaining characters of the alphabet
		//Outer loop iterates backwards through the alphabet
		for(int i = SIZE - 1; i >= 0; i--) {

			//Inner loop iterates through each keyword letter to check for duplicate characters
			for(int j = 0; j < keyLength; j++)

				//Increments duplicateFlag if duplicate letter is found
				if(alphabet[i] == cipher[j]) 
					duplicateFlag++; 

			//Appends letter to array if duplicate letter is not found
			if(duplicateFlag == 0) { 
				cipher[keyLength] = alphabet[i];
				keyLength++;
			}

			//Resets duplicate for start of next loop
			else duplicateFlag = 0;
		}

		//Print cipher array for testing and tutors
		for(char c : cipher) {
			System.out.print(c + " ");
		}
	} 

	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char ch)
	{
		for(int i = 0; i < SIZE; i++) {

			//Returns encoded character
			if(ch == alphabet[i]) {
				return cipher[i];
			} 

			//Returns non-letters such as spaces and punctuation as unprocessed
			else if (!(ch >= 'A' && ch <= 'Z')) {
				return ch;
			}
		}
		return ' ';
	}

	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
	public char decode(char ch)
	{
		for(int i = 0; i < SIZE; i++) {

			//Returns decoded character
			if(ch == cipher[i]) {
				return alphabet[i];
			} 

			//Returns non-letters such as spaces and punctuation as unprocessed
			else if (!(ch >= 'A' && ch <= 'Z')) {
				return ch;
			}
		}
		return ' ';
	}
}

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/** 
 * Programming AE2
 * Class to display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener
{
	//Instance variables which are the components
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel;

	//Application instance variables
	//Including the 'core' part of the textfile filename
	//codeType indicates whether encoding or decoding is to be done
	private MonoCipher mcipher;
	private VCipher vcipher;
	private LetterFrequencies lettFreq;
	private String fileName;
	private char codeType;
	private String keyword = "";


	/**
	 * The constructor adds all the components to the frame
	 */
	public CipherGUI()
	{
		this.setSize(400,150);
		this.setLocation(100,100);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
	}

	/**
	 * Helper method to add components to the frame
	 */
	public void layoutComponents()
	{
		//Top panel is yellow and contains a text field of 10 characters
		top = new JPanel();
		top.setBackground(Color.yellow);
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		keyField = new JTextField(10);
		top.add(keyField);
		this.add(top,BorderLayout.NORTH);

		//Middle panel is yellow and contains a text field of 10 characters
		middle = new JPanel();
		middle.setBackground(Color.yellow);
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10);
		middle.add(messageField);
		this.add(middle,BorderLayout.CENTER);

		//Bottom panel is green and contains 2 buttons
		bottom = new JPanel();
		bottom.setBackground(Color.green);
		//Create mono button and add it to the top panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		//Create vigenere button and add it to the top panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		//Add the top panel
		this.add(bottom,BorderLayout.SOUTH);
	}

	/**
	 * Listen for and react to button press events
	 * (use helper methods below)
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == monoButton) {

			//Processes file and report if getKeyword AND processFileName return TRUE
			if(getKeyword() && processFileName()) {
				processFile(false); //'false' processes as MonoCipher
				processReport();
				
				//Terminates after processing
				System.exit(0);
			}
		}

		if(e.getSource() == vigenereButton) {
			
			//Processes file and report if getKeyword AND processFileName return TRUE
			if(getKeyword() && processFileName()) {
				processFile(true); //'true' processes as Vigenere
				processReport();
				
				//Terminates after processing
				System.exit(0);
			}
		}
	}

	/** 
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	private boolean getKeyword()
	{
		//Checks if keyword field is empty
		keyword = keyField.getText();
		if(keyField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Keyword cannot be empty - please try again", "Blank keyword error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		//Checks if keyword is in CAPITALS
		for(int i = 0; i < keyword.length(); i++) {
			if(!Character.isUpperCase(keyword.charAt(i))) {
				JOptionPane.showMessageDialog(null, "Keyword must be in CAPITALS - please try again", "Keyword case error", JOptionPane.ERROR_MESSAGE);
				keyField.setText("");
				return false;
			}
		}

		//Checks that keyword contains only unique characters
		char[] keywordArray = keyword.toCharArray();
		for(int i = 0; i < keywordArray.length; i++){
			for(int j = i + 1; j < keywordArray.length; j++){
				if(keywordArray[i] == keywordArray[j]){
					if(i != j){
						JOptionPane.showMessageDialog(null, "Keyword must be ONLY unique characters  - please try again", "Keyword duplicate characters error", JOptionPane.ERROR_MESSAGE);
						keyField.setText("");
						return false;
					}
				}
			}
		}

		return true; 
	}

	/** 
	 * Obtains filename from GUI
	 * The details of the filename and the type of coding are extracted
	 * If the filename is invalid, a message is produced 
	 * The details obtained from the filename must be remembered
	 * @return whether a valid filename was entered
	 */
	private boolean processFileName()
	{
		//Checks if filename field is empty
		fileName = messageField.getText();
		if(messageField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Filename cannot be empty - please try again", "Blank filename error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		//Gets file encoding type from last letter of fileName
		codeType = fileName.charAt(fileName.length() - 1);

		//Checks if encoding type is correct format - character must be 'C' or 'P'
		if(codeType != 'C' && codeType != 'P') {
			JOptionPane.showMessageDialog(null, "Filename must end with either 'P' or 'C'  - please try again", "Filename error", JOptionPane.ERROR_MESSAGE);
			messageField.setText("");
			return false;
		}

		//Gets filename from messageField and appends '.txt' to the the end
		fileName = messageField.getText() + ".txt";

		return true;
	}

	/** 
	 * Reads the input text file character by character
	 * Each character is encoded or decoded as appropriate
	 * and written to the output text file
	 * @param vigenere whether the encoding is Vigenere (true) or Mono (false)
	 * @return whether the I/O operations were successful
	 */
	private boolean processFile(boolean vigenere)
	{
		//Creates FileReader, PrintWriter, MonoCipher and LetterFrequency objects
		FileReader reader = null;
		PrintWriter writer = null;
		lettFreq = new LetterFrequencies();

		//--------------------START OF MONOCIPHER CODE--------------------
		if(vigenere == false) {
			
			//Creates new MonoCipher object and passes user's keyword
			mcipher = new MonoCipher(keyField.getText());
			
			//Passes fileName to FileReader object
			try {
				reader = new FileReader(fileName);

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "File not found - please check filename", "Filename error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				return false;
			}

			//Tests whether file is plain text (codeType == 'P')
			if(codeType == 'P') {

				int charAsInt;

				//Creates filename for ciphered message
				String outputFilename = messageField.getText();
				outputFilename = outputFilename.substring(0, outputFilename.length() - 1);
				outputFilename += "C.txt";
				try {
					writer = new PrintWriter(outputFilename);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				try {
					while ((charAsInt = reader.read()) != -1) {
						char ch = (char)charAsInt; //Parses each letter from integer to character
						char encodedChar = mcipher.encode(ch); //Passes each character to encode method in MonoCipher object
						writer.write(encodedChar); //Passes each encoded character to FileWriter object
						lettFreq.addChar(encodedChar); //Passes each encoded character to addChar method in LetterFrequencies object
					}

				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "File-read error", "File-read error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
					return false;
				} 	writer.close();

				//Tests whether file is ciphered text (codeType == 'C')
			} else if(codeType == 'C') {

				int charAsInt;

				//Creates filename for decoded message
				String outputFilename = messageField.getText();
				outputFilename = outputFilename.substring(0, outputFilename.length() - 1);
				outputFilename += "D.txt";

				try {
					writer = new PrintWriter(outputFilename);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				try {
					while ((charAsInt = reader.read()) != -1) {
						char ch = (char)charAsInt; //Parses each letter from integer to character
						char decodedChar = mcipher.decode(ch); //Passes each character to decode method in MonoCipher object
						writer.write(decodedChar); //Passes each decoded character to FileWriter object
						lettFreq.addChar(decodedChar); //Passes each decoded letter to addChar method in LetterFrequencies object
					}

				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "File-read error", "File-read error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
					return false;
				} 
				writer.close();
			}
		}
		
		//--------------------START OF VIGENERE CODE--------------------
		if(vigenere == true) {
			
			//Creates new Vigenere cipher object and passes user's keyword
			vcipher = new VCipher(keyField.getText());
			keyword = keyField.getText();
			
			//Passes fileName to FileReader object
			try {
				reader = new FileReader(fileName);

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "File not found - please check filename", "Filename error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				return false;
			}

			//Tests whether file is plain text (codeType == 'P')
			if(codeType == 'P') {

				int charAsInt;

				//Creates filename for ciphered message
				String outputFilename = messageField.getText();
				outputFilename = outputFilename.substring(0, outputFilename.length() - 1);
				outputFilename += "C.txt";
				try {
					writer = new PrintWriter(outputFilename);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				try {
					while ((charAsInt = reader.read()) != -1) {
						char ch = (char)charAsInt; //Parses each letter from integer to character
						char encodedChar = vcipher.encode(ch); //Passes each character and keyword length to encode method in VCipher object
						writer.write(encodedChar); //Passes each encoded character to FileWriter object
						lettFreq.addChar(encodedChar); //Passes each encoded character to addChar method in LetterFrequencies object
					}

				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "File-read error", "File-read error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
					return false;
				} 	writer.close();

				//Tests whether file is ciphered text (codeType == 'C')
			} else if(codeType == 'C') {

				int charAsInt;

				//Creates filename for decoded message
				String outputFilename = messageField.getText();
				outputFilename = outputFilename.substring(0, outputFilename.length() - 1);
				outputFilename += "D.txt";

				try {
					writer = new PrintWriter(outputFilename);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				try {
					while ((charAsInt = reader.read()) != -1) {
						char ch = (char)charAsInt; //Parses each letter from integer to character
						char decodedChar = vcipher.decode(ch); //Passes each character to decode method in VCipher object
						writer.write(decodedChar); //Passes each decoded character to FileWriter object
						lettFreq.addChar(decodedChar); //Passes each decoded letter to addChar method in LetterFrequencies object
					}

				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "File-read error", "File-read error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
					return false;
				} 

				writer.close();
			}

			//Closes reader object
			try {
				reader.close();

			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	//Processes frequency report
	private void processReport() {

		PrintWriter writer; 

		try 
		{	
			//Creates filename for frequency report
			String outputFilename = messageField.getText();
			outputFilename = outputFilename.substring(0, outputFilename.length() - 1);
			outputFilename += "F.txt";

			writer = new PrintWriter(outputFilename) ;

			//Creates frequency report headings
			writer.printf("%-7s %-5s %-6s %-9s %s\n", "Letter","Freq","Freq%", "AvgFreq%","Diff");
			
			//Passes letters and index location of alphabet to getReport() method
			for (int i = 0; i < 26; i++) 
			{	
				writer.println(lettFreq.getReport((char)i,i)); 
			}

			//Outputs the most frequent character with its frequency percentage
			char maxChar = lettFreq.getMaxChar();
			double maxFreq = lettFreq.getMaxPC();
			writer.println(String.format("The most frequent character is %s at %.1f%s ", maxChar, maxFreq,"%")); 
			writer.close();
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

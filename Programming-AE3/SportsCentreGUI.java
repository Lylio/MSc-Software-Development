import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
public class SportsCentreGUI extends JFrame implements ActionListener {

	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;

	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	private final String attendancesFile = "AttendancesIn.txt";

	/**Fitness Program object */
	FitnessProgram fitProgram;

	/**Constructor for AssEx3GUI class*/
	public SportsCentreGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setSize(800, 300);
		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		fitProgram = new FitnessProgram();
		initLadiesDay();
		initAttendances();
		updateDisplay();
	}

	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() {
		try {
			FileReader reader = new FileReader(classesInFile);
			Scanner in = new Scanner(reader);
			while(in.hasNextLine()) {
				fitProgram.createClassArray(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialises the attendances using data
	 * from the file AttendancesIn.txt
	 */
	public void initAttendances() {
		try {
			FileReader reader = new FileReader(attendancesFile);
			Scanner in = new Scanner(reader);
			while(in.hasNextLine()) {
				fitProgram.createAttendArray(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**Instantiates timetable display and adds it to GUI*/
	public void updateDisplay() {
		String hours = "", classNames = "", tutorNames = "";
		int classDuration = 1;
		int[] times = fitProgram.getClassTimes();
		FitnessClass[] fitClasses = fitProgram.getClassArray();

		for(int i = 0; i < fitClasses.length; i++) {

			//Time slots line
			hours += String.format(" %s" + "-" + "%s" + "%8s", times[i], (times[i] + classDuration), " ");

			if(fitClasses[i] != null) {
				//Fitness class names line
				classNames += String.format(" %s" + "%7s", fitClasses[i].getClassName(), " ");
				//Tutor names line
				tutorNames += String.format(" %s " + "%8s", fitClasses[i].getTutorName(), " ");
			} else {
				//Output for null values in array of fitness class objects
				classNames += String.format("%s" + "%3s", "Available", " ");
				tutorNames += String.format("%13s", " ");
			}
		}

		//Sets & appends fitness class time, class name and tutor name to text display area
		display.setText(hours + "\n");
		display.append(classNames + "\n");
		display.append(tutorNames);
	}

	/**Adds buttons to top of GUI*/
	public void layoutTop() {
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	/**Adds labels, text fields and buttons to bottom of GUI*/
	public void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}

	/**Processes adding a fitness class*/
	public void processAdding() {
		String classID = idIn.getText();
		String className = classIn.getText();
		String tutorName = tutorIn.getText();
		int noc = fitProgram.numOfClasses();
		int max = fitProgram.getMaxClasses();

		//Displays error if fields are empty
		if(classID.isEmpty() || className.isEmpty() || tutorName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Fields cannot be empty - please try again", "Empty fields error", JOptionPane.ERROR_MESSAGE);
		} 
		//Displays error if fitness class array is full
		else if(noc == max) {
			JOptionPane.showMessageDialog(null, "Sorry - class schedule is full", "Schedule full error", JOptionPane.ERROR_MESSAGE);
		} 
		//Displays error if class ID already exists
		else if(fitProgram.getFCID(classID) != null) {
			JOptionPane.showMessageDialog(null, "Sorry - class with ID '" + classID + "' already exists", "Class exists error", JOptionPane.ERROR_MESSAGE);
		}
		//Class addition method is called from FitnessProgram object if fields contain legal data
		else {
			fitProgram.addClass(classID, className, tutorName);
			updateDisplay();
			idIn.setText(null); classIn.setText(null); tutorIn.setText(null);
			JOptionPane.showMessageDialog(null, "Fitness class successfully added", "Fitness class added", JOptionPane.PLAIN_MESSAGE);
		}
	}

	/**Processes deleting a fitness class*/
	public void processDeletion() {
		//Gets fitness class ID from text field
		String classID = idIn.getText();

		//Displays error message if ID field is blank
		if(classID.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Empty ID - please try again", "Invalid ID error", JOptionPane.ERROR_MESSAGE);
			idIn.requestFocus();
		}
		//Displays error message if ID is not found in fitness class array
		else if(fitProgram.getFCID(classID) == null) {
			JOptionPane.showMessageDialog(null, "ID not found - please try again", "Invalid ID error", JOptionPane.ERROR_MESSAGE);
			idIn.setText(null);
			idIn.requestFocus();
		}
		//Class deletion method is called from FitnessProgram object if ID is matched
		else {
			fitProgram.deleteClass(classID);
			updateDisplay();
			idIn.setText(null);
			JOptionPane.showMessageDialog(null, "Fitness class '" + classID + "' successfully removed", "Fitness class removed", JOptionPane.PLAIN_MESSAGE);
		}
	}

	/**Instantiates a new window and displays the attendance report*/
	public void displayReport() {
		report = new ReportFrame(fitProgram);
		report.setVisible(true);
	}

	/**Writes lines to file representing class name, tutor and start time and then exits from the program*/
	public void processSaveAndClose() {
		try {
			PrintWriter writer = new PrintWriter(classesOutFile);
			for(int i = 0; i < fitProgram.getClassArray().length; i++) {
				FitnessClass fitClass = fitProgram.getClassArray()[i];
				if (fitClass != null) {
					String classDetails = String.format("%s %s %s %d",fitClass.getClassID(),
							fitClass.getClassName(), fitClass.getTutorName(),
							fitClass.getStartTime());
					writer.println(classDetails);
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Fitness classes successfully written to file", "Write successful", JOptionPane.PLAIN_MESSAGE);
		System.exit(0);
	}

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource().equals(attendanceButton)) {

			displayReport();	
		}
		else if(ae.getSource().equals(closeButton)) {

			processSaveAndClose();
		}

		else if(ae.getSource().equals(deleteButton)) {

			processDeletion();
		}
		else if(ae.getSource().equals(addButton)) {

			processAdding();
		}
	}
}
